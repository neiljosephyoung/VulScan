package com.ny.vulscan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ny.vulscan.model.Finding;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Service;

/**
 * ScannerServiceImpl.
 */
@Service
public class ScannerServiceImpl implements ScannerService {
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final OllamaChatModel chatModel;

  private static final String PROMPT_TEMPLATE = """
            You are a security static analysis assistant.
            Analyze the following Java code for potential security issues (OWASP Top 10, CWE).
            
            **INSTRUCTIONS:**
            - Return ONLY valid JSON. Nothing else. No explanations, no commentary.
            - The JSON must be an array of objects grouped by path.
            - Each object must contain exactly these keys:
              - path (Use full file path.)
              - file (file name)
              - line (approx if possible)
              - issue
              - severity (LOW, MEDIUM, HIGH)
              - cve (CVE reference if available)
              - cvss (decimal format if available)
              - recommendation (well-written, detailed guidance. As much detail as possible)
            
            
            Here's the code to analyse:
            ```
            %s
            ```
            """;

  /**
   * Wiring.
   *
   * @param chatModel OllamaChatModel
   */
  public ScannerServiceImpl(OllamaChatModel chatModel) {
    this.chatModel = chatModel;
  }

  @Override
  public List<HashMap<String, List<Finding>>> scanProject(Path projectDir, String model) throws IOException {
    List<HashMap<String, List<Finding>>> allFindings = new ArrayList<>();
    Files.walk(projectDir)
                .filter(path -> path.toString().endsWith(".java"))
                .forEach(path -> {
                  var fileName = path.getName(path.getNameCount() - 1).toString();
                  String code;
                  try {
                    code = Files.readString(path);
                  } catch (IOException e) {
                    throw new RuntimeException(e);
                  }
                  String transformedPrompt = PROMPT_TEMPLATE.formatted(code);

                  ChatResponse response = chatModel.call(
                          new Prompt(transformedPrompt,
                                  OllamaOptions.builder()
                                          .model(OllamaModel.LLAMA3)
                                          .temperature(0.1)
                                          .build()
                            ));

                  response.getResults().forEach(result -> {
                    try {
                      var map = new HashMap<String, List<Finding>>();
                      var findings = objectMapper.readValue(result.getOutput().getText(),
                                                    new TypeReference<List<Finding>>(){});
                      map.put(fileName, findings);
                      allFindings.add(map);
                    } catch (JsonProcessingException e) {
                      throw new RuntimeException(e);
                    }
                  });
                });

    return allFindings;
  }
}

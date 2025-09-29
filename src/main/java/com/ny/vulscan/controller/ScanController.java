package com.ny.vulscan.controller;

import com.ny.vulscan.ScannerService;
import com.ny.vulscan.model.Finding;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * ScanController.
 */
@Controller
public class ScanController {

  private final ScannerService scannerService;

  /**
  * Wiring.
  *
  * @param scannerService ScannerService
  */
  public ScanController(ScannerService scannerService) {
    this.scannerService = scannerService;
  }

  /**
  * index.
  *
  * @return String String
  */
  @GetMapping("/")
  public String index() {
    return "index";
  }

  /**
  * analyze endpoint.
  *
  * @param path String
  * @param model String
  * @param uiModel Model
  * @return String htmx templated view
  * @throws Exception Exception
  */
  @PostMapping("/analyze")
  public String analyze(@RequestParam String path,
                        @RequestParam String model,
                        Model uiModel) throws Exception {

    // Run scanner service
    List<HashMap<String, List<Finding>>> results = scannerService.scanProject(Paths.get(path), model);
    uiModel.addAttribute("results", results);

    return "results :: resultFragment"; // return HTMX fragment
  }
}

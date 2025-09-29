package com.ny.vulscan;

import com.ny.vulscan.model.Finding;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

/**
 * ScannerService.
 */
public interface ScannerService {

  /**
  * scanProject.
  *
  * @param projectDir Path
  * @param model String
  * @return List of findings
  * @throws IOException Exception
  */
  List<HashMap<String, List<Finding>>> scanProject(Path projectDir, String model) throws IOException;
}

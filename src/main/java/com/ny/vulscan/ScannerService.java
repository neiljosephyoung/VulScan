package com.ny.vulscan;

import com.ny.vulscan.model.Finding;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public interface ScannerService {

    List<HashMap<String, List<Finding>>> scanProject(Path projectDir, String model) throws IOException;

}

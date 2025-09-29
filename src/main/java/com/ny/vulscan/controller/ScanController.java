package com.ny.vulscan.controller;

import com.ny.vulscan.ScannerService;
import com.ny.vulscan.model.Finding;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

//@RestController
//@RequestMapping("/scan")
@Controller
public class ScanController {

    private final ScannerService scannerService;

    public ScanController(ScannerService scannerService) {
        this.scannerService = scannerService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

//    @PostMapping("/analyze")
//    public List<HashMap<String, List<Finding>>> scan(@RequestParam String path, @RequestParam(defaultValue = "llama3") String model) throws Exception {
//        return scannerService.scanProject(Path.of(path), model);
//    }

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

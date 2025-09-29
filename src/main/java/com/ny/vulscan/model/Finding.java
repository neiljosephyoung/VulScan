package com.ny.vulscan.model;

import java.util.Map;

public record Finding(
        String path,
        String file,
        Integer line,
        String issue,
        String cve,
        Double cvss,
        String severity,
        String recommendation
) {
}

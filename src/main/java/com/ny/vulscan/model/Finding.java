package com.ny.vulscan.model;


/**
 * Finding.
 *
 * @param path String
 * @param file String
 * @param line Integer
 * @param issue String
 * @param cve String
 * @param cvss Double
 * @param severity String
 * @param recommendation String
 */
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

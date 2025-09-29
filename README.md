# VulScan

## Overview

VulScan is a **local security static analysis tool** for Java projects built with **Spring Boot**. It leverages a **local LLM (Ollama)** to automatically detect potential security issues, including OWASP Top 10 vulnerabilities and CWE references.  

The front-end uses **HTMX** for a responsive, interactive experience, allowing you to analyze projects directly from the browser without page reloads.

---

## Features

- Scan entire Java project directories for security issues.  
- Supports **multiple models** (like LLaMA3) via a local AI client.  
- Returns results in a **collapsible tree view** grouped by file.  
- Detailed findings include:
  - File path
  - Line number
  - Issue description
  - Severity (LOW, MEDIUM, HIGH)
  - CVE reference (if available)
  - CVSS score (if available)
  - Recommendation for remediation
- Modern, responsive UI with **spinner during analysis**.  
- Expand/Collapse all files for easier navigation.  

---

## Usage
<img width="1916" height="911" alt="image" src="https://github.com/user-attachments/assets/bdf60450-d2eb-4dcb-ac3f-9a301a61b0ee" />

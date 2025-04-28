# Spring Boot Demo Project

[![CodeFactor](https://www.codefactor.io/repository/github/vancetang/demo/badge)](https://www.codefactor.io/repository/github/vancetang/demo) ![Spring Boot](https://img.shields.io/badge/dynamic/xml?url=https://raw.githubusercontent.com/vancetang/demo/master/pom.xml&query=//*[local-name()='parent']/*[local-name()='version']&label=Spring%20Boot&color=brightgreen) ![Java Version](https://img.shields.io/badge/dynamic/xml?url=https://raw.githubusercontent.com/vancetang/demo/master/pom.xml&query=//*[local-name()='properties']/*[local-name()='java.version']&label=Java&color=ED8B00&logo=openjdk&logoColor=white) ![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-Enabled-blue) [![Star History](https://img.shields.io/badge/Star%20History-Chart-orange)](https://star-history.com/#vancetang/demo&Date)

This project serves as a demonstration and testing ground for Spring Boot functionalities integrated with GitHub Actions. It incorporates and implements common techniques and applications found online, providing a practical learning resource and reference example.

## 🌐 Available Languages

[![English](https://img.shields.io/badge/English-Click-yellow)](README_en.md)
[![繁體中文](https://img.shields.io/badge/繁體中文-Click-orange)](README.md)
[![简体中文](https://img.shields.io/badge/简体中文-Click-green)](README_zh-CN.md)

## 🚀 Project Features Overview

### GitHub Actions Workflows

The following GitHub Actions features are implemented in this project:

- **`add-labels`**: Automatically adds or modifies labels.
- **`cleanup-cache`**: Cleans up the Action cache (currently has a date conversion issue to be fixed).
- **`close-stale-issues-prs`**: Closes stale Issues and PRs.
- **`create-release`**: Automatically generates releases based on tags.
- **`gmail`**: Sends Gmail notifications.
- **`notify-collaborators`**: Sends GitHub notifications to repository collaborators.
- **`sync-branches-and-notify`**: Synchronizes the `main` branch to `develop` and sends a notification upon failure.
- **`telegram`**: Sends Telegram messages.
- **`codeql`**: Performs static code analysis for vulnerability detection.
- **`test`**: Testing functionality (retains direct Java execution and `mvn clean package`).
- **`translate`**: README translation (currently unable to test due to missing OpenAI Token).
- **`label-pr`**: Automatically adds labels to PRs (using `actions/labeler@v5`, note that the format has been updated).
- **`dependency-check`**: Executes OWASP Dependency-Check tool to scan project dependencies for known vulnerabilities and generates a detailed report.
- **`lint-pr`**: Checks if the Pull Request title conforms to the Conventional Commits v1.0.0 specification.
- **`shiftleft-reports`**: Uses the ShiftLeft Scan tool to perform static security analysis on the application and its dependencies, identifying potential vulnerabilities and generating reports.
- **`translate-readme`**: Translates README.md into other language versions (occasional incomplete translations and minor issues may occur).
- **`release-please`**: Analyzes commits based on Conventional Commits, generates CHANGELOG.md and creates a PR, automatically creating a Release and tag after merging (not adopted because the project uses "Merge Pull Request" which creates an extra record).
- **`translate`**: Translates the specified file into other language versions (completed through the python googletrans API).

### Spring Boot Related Tests

- Developed and tested using **Spring Boot v3**.
- Testing branch protection: `master` branch requires a PR to be merged.
- Local protection: Added `pre-push` Hook to prevent direct pushes to `master` and check for updates.

### Other Features

- Issues reporting supports English.
- PR Template with multi-language support (not adopted, unable to implement).

# Spring Boot Test Project

[![CodeFactor](https://www.codefactor.io/repository/github/vancetang/demo/badge)](https://www.codefactor.io/repository/github/vancetang/demo)![Spring Boot](https://img.shields.io/badge/dynamic/xml?url=https://raw.githubusercontent.com/vancetang/demo/master/pom.xml&query=//*[local-name()='parent']/*[local-name()='version']&label=Spring%20Boot&color=brightgreen)![Java Version](https://img.shields.io/badge/dynamic/xml?url=https://raw.githubusercontent.com/vancetang/demo/master/pom.xml&query=//*[local-name()='properties']/*[local-name()='java.version']&label=Java&color=ED8B00&logo=openjdk&logoColor=white)![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-Enabled-blue)[![Star History](https://img.shields.io/badge/Star%20History-Chart-orange)](https://star-history.com/#vancetang/demo&Date)

This is a special project to test Spring Boot and GitHub Actions functions, covering common related functions and implementations on the Internet.

* * *

## 🌐 Available Languages

-   **[Traditional Chinese (preset)](README.md)**
-   **[English](README.en.md)**
-   **[Simplified Chinese](README.zh-CN.md)**

* * *

## 🚀 Overview of project functions

### GitHub Actions Workflow

以下是本專案中實作的 GitHub Actions 功能：

-   **`add-labels`**: Automatically add or modify tags.
-   **`cleanup-cache`**: Clear Action cache (there are currently issues with date conversion to be fixed).
-   **`close-stale-issues-prs`**: Close expired Issues and PRs.
-   **`create-release`**: Automatically generate Release according to Tag.
-   **`gmail`**: Send Gmail notifications.
-   **`notify-collaborators`**: Send GitHub notifications to repository collaborators.
-   **`sync-branches-and-notify`**:Will`main`Branch sync to`develop`, send a notification when it fails.
-   **`telegram`**: Send Telegram message.
-   **`codeql`**: Perform static program weakness scan.
-   **`test`**: Test function (reserves direct execution of Java and`mvn clean package`）。
-   **`translate`**:README translation (cannot be tested due to the lack of OpenAI Token).
-   **`label-pr`**: Automatically add tags to PR (using`actions/labeler@v5`, note that the format has been updated).
-   **`dependency-check`**：Run the OWASP Dependency-Check tool to scan project dependencies for known vulnerabilities and generate a detailed report.
-   **`lint-pr`**：Check if the Pull Request title complies with the Conventional Commits v1.0.0 specification.
-   **`shiftleft-reports`**: Use the ShiftLeft Scan tool to perform static security analysis of applications and their dependencies, identify potential vulnerabilities and generate reports.
-   **`translate-readme`**: Translate README.md into other language versions (occasionally, the translation may be incomplete and contain minor issues).
-   ~~**`release-please`**: Analyzes commits based on Conventional Commits, generates CHANGELOG.md and creates a PR, then automatically produces a Release and tag after merging.~~

* * *

### Spring Boot related tests

-   use**Spring Boot v3**Version development and testing.
-   Test branch protection:`master`Branches need to be merged through PR.
-   Local protection: added`pre-push`Hook, prevent direct push to`master`And check for updates.

### Other features

-   Issues returns support English version.
-   ~~PR Template Multi-language version (unable to implement)~~。

* * *

# Spring Boot Test Project

[![CodeFactor](https://www.codefactor.io/repository/github/vancetang/demo/badge)](https://www.codefactor.io/repository/github/vancetang/demo) ![Spring Boot](https://img.shields.io/badge/dynamic/xml?url=https://raw.githubusercontent.com/vancetang/demo/master/pom.xml&query=//*[local-name()='parent']/*[local-name()='version']&label=Spring%20Boot&color=brightgreen) ![Java Version](https://img.shields.io/badge/dynamic/xml?url=https://raw.githubusercontent.com/vancetang/demo/master/pom.xml&query=//*[local-name()='properties']/*[local-name()='java.version']&label=Java&color=ED8B00&logo=openjdk&logoColor=white) ![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-Enabled-blue) [![Star History](https://img.shields.io/badge/Star%20History-Chart-orange)](https://star-history.com/#vancetang/demo&Date)

This project aims to test and demonstrate the functions of Spring Boot combined with GitHub Actions, integrate and implement common related technologies and applications on the Internet, as a practical learning and reference example.


## 🌐 Available Languages

[![English](https://img.shields.io/badge/English-Click-yellow)](README_en.md)
[![Traditional Chinese](https://img.shields.io/badge/Traditional Chinese-click to view-orange)](README.md)
[![Simplified Chinese](https://img.shields.io/badge/Simplified Chinese-click to view-green)](README_zh-cn.md)


## 🚀 Overview of project functions

### GitHub Actions Workflow
The following are the GitHub Actions functions implemented in this project:
- **`add-labels`**: Automatically add or modify labels.
- **`cleanup-cache`**: Clear Action cache (there are currently issues with date conversion to be fixed).
- **`close-stale-issues-prs`**: Close expired Issues and PRs.
- **`create-release`**: Automatically generate Release based on the Tag.
- **`gmail`**: Send Gmail notifications.
- **`notify-collaborators`**: Send GitHub notifications to repository collaborators.
- **`sync-branches-and-notify`**: Synchronize the `main` branch to `develop`, send a notification if it fails.
- **`telegram`**: Send Telegram messages.
- **`codeql`**: Perform static program weakness scan.
- **`test`**: Test function (reserves direct execution of Java and `mvn clean package`).
- **`translate`**: README translation (cannot be tested due to the lack of OpenAI Token).
- **`label-pr`**: Automatically add labels to PR (use `actions/labeler@v5`, note that the format has been updated).
- **`dependent-check`**: Execute the OWASP Dependency-Check tool, scan the project dependencies for known vulnerabilities, and generate detailed reports.
- **`lint-pr`**: Checks whether the title of the Pull Request complies with the Conventional Commits v1.0.0 specification.
- **`shiftleft-reports`**: Use the ShiftLeft Scan tool to perform static security analysis of applications and their dependencies, identify potential vulnerabilities and generate reports.
- **`translate-readme`**: Translate README.md to another language version (the translation may be incomplete occasionally, with some minor problems).
- **`release-please`**: Based on the Conventional Commits analysis and submission, generate CHANGELOG.md and create PRs. After the merge, release and tags will be automatically generated (not used, because the PR in the project will use Merge Pull Request to make an extra record).
- **`translate`**: Translate the specified archive to another language version (completed through the python googletrans API).


### Spring Boot related tests
- Develop and test using the **Spring Boot v3** version.
- Test branch protection: The `master` branch must be PR before it can be merged.
- Local protection: Added `pre-push` Hook to prevent direct push to `master` and check for updates.


### Other features
- Issues returns support English version.
- PR Template Multi-language version (not adopted, cannot be implemented).
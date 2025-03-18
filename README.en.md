# Spring Boot Test Project

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-v3-brightgreen)![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-Enabled-blue)[![Star History](https://img.shields.io/badge/Star%20History-Chart-orange)](https://star-history.com/#vancetang/demo&Date)

This is a project to test Spring Boot and GitHub Actions functions, covering common related functions and implementations on the Internet.

* * *

## 🌐 Available Languages

-   **[Traditional Chinese (preset)](README.md)**
-   **[English](README.en.md)**
-   **[Simplified Chinese](README.zh-CN.md)**

* * *

## 🚀 Overview of project functions

### GitHub Actions Workflow

The following are the GitHub Actions functions implemented in this project:

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

### Spring Boot related tests

-   use**Spring Boot v3**Version development and testing.
-   Test branch protection:`master`Branches need to be merged through PR.
-   Local protection: added`pre-push`Hook, prevent direct push to`master`And check for updates.

### Other features

-   Issues returns support English version.
-   ~~PR Template Multi-language version (unable to implement)~~。

* * *

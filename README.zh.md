# Spring Boot 测试专案

[![CodeFactor](https://www.codefactor.io/repository/github/vancetang/demo/badge)](https://www.codefactor.io/repository/github/vancetang/demo) ![Spring Boot](https://img.shields.io/badge/dynamic/xml?url=https://raw.githubusercontent.com/vancetang/demo/master/pom.xml&query=//*[local-name()='parent']/*[local-name()='version']&label=Spring%20Boot&color=brightgreen) ![Java Version](https://img.shields.io/badge/dynamic/xml?url=https://raw.githubusercontent.com/vancetang/demo/master/pom.xml&query=//*[local-name()='properties']/*[local-name()='java.version']&label=Java&color=ED8B00&logo=openjdk&logoColor=white) ![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-Enabled-blue) [![Star History](https://img.shields.io/badge/Star%20History-Chart-orange)](https://star-history.com/#vancetang/demo&Date) ![GitHub License](https://img.shields.io/github/license/vancetang/demo)

此项目旨在测试与展示 Spring Boot 结合 GitHub Actions 的功能，整合并实现了网络上常见的相关技术与应用，作为一个实用的学习与参考范例。


## 🌐 可用语言

[![English](https://img.shields.io/badge/English-Click-yellow)](README.en.md)
[![繁體中文](https://img.shields.io/badge/繁體中文-點擊查看-orange)](README.md)
[![简体中文](https://img.shields.io/badge/简体中文-点击查看-green)](README.zh.md)


## 🚀 专案功能概览

### GitHub Actions 工作流程

以下是本专案中实作的 GitHub Actions 功能：

-   **`add-labels`**：自动新增或修改标签。
-   **`cleanup-cache`**：清除 Action 快取（目前有日期转换问题待修复）。
-   **`close-stale-issues-prs`**：关闭过期的 Issues 和 PRs。
-   **`create-release`**：根据 Tag 自动生成 Release。
-   **`gmail`**：发送 Gmail 通知。
-   **`notify-collaborators`**：向仓库协作者发送 GitHub 通知。
-   **`sync-branches-and-notify`**：将`main`分支同步到`develop`，失败时发送通知。
-   **`telegram`**：发送 Telegram 讯息。
-   **`codeql`**：执行静态程式弱点扫描。
-   **`test`**：测试功能（保留直接执行 Java 和`mvn clean package`）。
-   **`translate`**：README 翻译（因缺少 OpenAI Token 暂无法测试）。
-   **`label-pr`**：为 PR 自动添加标签（使用`actions/labeler@v5`，注意格式已更新）。
-   **`dependency-check`**：执行 OWASP Dependency-Check 工具，扫描专案依赖项是否存在已知漏洞，并生成详细报告。
-   **`lint-pr`**：检查 Pull Request 的标题是否符合 Conventional Commits v1.0.0 规范。
-   **`shiftleft-reports`**：利用 ShiftLeft Scan 工具，对应用程式及其依赖项进行静态安全分析，找出潜在漏洞并生成报告。
-   **`translate-readme`**：将 README.md 翻译成其他语言版本（有时翻译可能不完整，存在一些小问题）。
-   ~~**`release-please`**: 依據 Conventional Commits 分析提交，生成 CHANGELOG.md 并创建 PR，合并后自动产生 Release 和标签。~~


### Spring Boot 相关测试

-   使用**弹簧靴V3**版本进行开发与测试。
-   测试分支保护：`master`分支需通过 PR 才能合并。
-   本地端保护：新增`pre-push`Hook，防止直接推送至`master`并检查更新。

### 其他功能

-   Issues 回报支援英文版本。
-   ~~PR Template 多国语言版本（无法实现）~~。


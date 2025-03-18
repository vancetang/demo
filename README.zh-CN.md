# Spring Boot 测试专案

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-v3-brightgreen)![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-Enabled-blue)[![Star History](https://img.shields.io/badge/Star%20History-Chart-orange)](https://star-history.com/#vancetang/demo&Date)

这是一个用来测试 Spring Boot 与 GitHub Actions 功能的专案，涵盖网路上常见的相关功能与实作。

* * *

## 🌐 可用语言

-   **[繁体中文 (预设)](README.md)**
-   **[英语](README.en.md)**
-   **[简体中文](README.zh-CN.md)**

* * *

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

### Spring Boot 相关测试

-   使用**弹簧靴V3**版本进行开发与测试。
-   测试分支保护：`master`分支需通过 PR 才能合并。
-   本地端保护：新增`pre-push`Hook，防止直接推送至`master`并检查更新。

### 其他功能

-   Issues 回报支援英文版本。
-   ~~PR Template 多国语言版本（无法实现）~~。

* * *

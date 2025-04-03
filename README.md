# Spring Boot 測試專案

[![CodeFactor](https://www.codefactor.io/repository/github/vancetang/demo/badge)](https://www.codefactor.io/repository/github/vancetang/demo) ![Spring Boot](https://img.shields.io/badge/dynamic/xml?url=https://raw.githubusercontent.com/vancetang/demo/master/pom.xml&query=//*[local-name()='parent']/*[local-name()='version']&label=Spring%20Boot&color=brightgreen) ![Java Version](https://img.shields.io/badge/dynamic/xml?url=https://raw.githubusercontent.com/vancetang/demo/master/pom.xml&query=//*[local-name()='properties']/*[local-name()='java.version']&label=Java&color=ED8B00&logo=openjdk&logoColor=white) ![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-Enabled-blue) [![Star History](https://img.shields.io/badge/Star%20History-Chart-orange)](https://star-history.com/#vancetang/demo&Date)

這是一個用來測試 Spring Boot 與 GitHub Actions 功能的專案，涵蓋網路上常見的相關功能與實作。

---

## 🌐 可用語言
- **[繁體中文 (預設)](README.md)**  
- **[English](README.en.md)**  
- **[简体中文](README.zh-CN.md)**  

---

## 🚀 專案功能概覽

### GitHub Actions 工作流程
以下是本專案中實作的 GitHub Actions 功能：
- **`add-labels`**：自動新增或修改標籤。
- **`cleanup-cache`**：清除 Action 快取（目前有日期轉換問題待修復）。
- **`close-stale-issues-prs`**：關閉過期的 Issues 和 PRs。
- **`create-release`**：根據 Tag 自動生成 Release。
- **`gmail`**：發送 Gmail 通知。
- **`notify-collaborators`**：向倉庫協作者發送 GitHub 通知。
- **`sync-branches-and-notify`**：將 `main` 分支同步到 `develop`，失敗時發送通知。
- **`telegram`**：發送 Telegram 訊息。
- **`codeql`**：執行靜態程式弱點掃描。
- **`test`**：測試功能（保留直接執行 Java 和 `mvn clean package`）。
- **`translate`**：README 翻譯（因缺少 OpenAI Token 暫無法測試）。
- **`label-pr`**：為 PR 自動添加標籤（使用 `actions/labeler@v5`，注意格式已更新）。
- **`dependency-check`**：執行 OWASP Dependency-Check 工具，掃描專案依賴項是否存在已知漏洞，並生成詳細報告。
- **`lint-pr`**：檢查 Pull Request 的標題是否符合 Conventional Commits v1.0.0 規範。
- **`shiftleft-reports`**：利用 ShiftLeft Scan 工具，對應用程式及其依賴項進行靜態安全分析，找出潛在漏洞並生成報告。
- **`translate-readme`**：將 README.md 翻譯成其他語言版本（偶爾翻譯可能不完整，存在一些小問題）。


---


### Spring Boot 相關測試
- 使用 **Spring Boot v3** 版本進行開發與測試。
- 測試分支保護：`master` 分支需通過 PR 才能合併。
- 本地端保護：新增 `pre-push` Hook，防止直接推送至 `master` 並檢查更新。

### 其他功能
- Issues 回報支援英文版本。
- ~~PR Template 多國語言版本（無法實現）~~。

---

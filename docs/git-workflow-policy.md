# Git 分支管理與同步策略指南

本專案採用 `feat -> develop -> master` 的開發流程，並透過 GitHub Actions 實現 `master` 到 `develop` 的自動反向同步。

## 📑 分支定義

- **`master`**: 生產環境分支，僅接受來自 `develop` 的 Pull Request。
- **`develop`**: 主要開發分支，所有新功能與修復均在此彙整。
- **`feat/*` 或 `fix/*`**: 功能開發或問題修復分支，從 `develop` 分出。

---

## 🚀 開發與合併流程

### 1. 功能開發 (feat -> develop)
當功能開發完成並準備併入 `develop` 時：
- **建議合併方式**：`Squash and Merge`。
- **優點**：保持 `develop` 歷史簡潔，每個提交代表一個完整的功能區塊。

### 2. 發布準備 (develop -> master)
當 `develop` 測試完成，準備合併到 `master` 時：
- **建議合併方式**：**`Create a Merge Commit`** (最推薦) 或 `Rebase and Merge`。
- **關鍵點**：**避免使用 `Squash and Merge`**。
  - *原因*：使用 Merge Commit 能維持分支間的「親緣關係」，讓 Git 知道兩者已完全同步，有利於後續自動化腳本執行。

---

## 🔄 自動反向同步機制 (Master -> Develop)

本專案配置了 `.github/workflows/sync-branchs-and-notify.yml` 自動化流程，確保 `master` 的任何變更（如 Hotfix 或 PR 合併）能即時同步回 `develop`。

### 同步邏輯說明
1. **優先嘗試 Fast-forward**：若 `develop` 與 `master` 具備直系親緣關係，將直接進行無感的快速同步。
2. **自動衝突處理 (-X theirs)**：若因 `develop -> master` 曾使用過 Squash Merge 而導致線圖斷裂，腳本會自動以 `master` 內容解決「內容重複」的衝突並建立一個同步提交。
3. **失敗通知**：若發生無法自動解決的邏輯衝突，系統會建立 GitHub Issue 通知開發者手動介入。

---

## 💡 最佳實踐小撇步

1. **提交訊息**：遵循 [Conventional Commits](https://www.conventionalcommits.org/) 規範（例如 `feat:`, `fix:`, `chore:`, `ci:`）。
2. **保持 Develop 更新**：在將 `feat` 併入 `develop` 前，建議先將 `develop` 合併/Rebase 回自己的分支。
3. **同步 PR 處理**：當自動同步產生 Merge Commit 時，訊息會包含 `[skip ci]` 以避免觸發不必要的重複建置。

---

*最後更新日期：2025年12月23日*

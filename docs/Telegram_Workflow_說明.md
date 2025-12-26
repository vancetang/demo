# Telegram Bot GitHub Actions Workflow 說明

## 概述

這個 GitHub Actions workflow 用於測試 Telegram Bot 的訊息發送功能，包含 Chat ID 驗證和增強的錯誤處理。

## 主要功能

### 1. Chat ID 狀態檢查

在發送訊息前，會先檢查 `TELEGRAM_CHAT_ID` 是否已正確設定：

- **已設定**：顯示 `Chat ID 已設定 ✓`
- **未設定**：顯示 `⚠️ 警告: TELEGRAM_CHAT_ID 未設定或為空`
- **安全性**：不會在日誌中顯示實際的 Chat ID 值

### 2. 增強的錯誤處理

- 檢查 Chat ID 是否已設定
- 驗證訊息發送結果
- 使用 `jq` 格式化 API 回應（如果可用）
- 發送失敗時自動終止並返回錯誤碼

## Workflow 執行步驟

### Step 1: Checkout repository

```yaml
- name: Checkout repository
  uses: actions/checkout@v4
```

檢出程式碼儲存庫（標準步驟）。

### Step 2: Run Telegram bot test

```yaml
- name: Run Telegram bot test
  env:
    TELEGRAM_TOKEN: ${{ secrets.TELEGRAM_TOKEN }}
    TELEGRAM_CHAT_ID: ${{ secrets.TELEGRAM_CHAT_ID }}
  run: |
    MESSAGE="${{ github.event.inputs.message }}"
    echo "準備發送訊息到 Telegram..."

    # 檢查 Chat ID 是否已設定
    if [ -n "$TELEGRAM_CHAT_ID" ]; then
      echo "Chat ID 已設定 ✓"
    else
      echo "⚠️ 警告: TELEGRAM_CHAT_ID 未設定或為空"
    fi

    # 發送訊息到 Telegram
    RESPONSE=$(curl -s -X POST "https://api.telegram.org/bot${TELEGRAM_TOKEN}/sendMessage" \
      -d "chat_id=${TELEGRAM_CHAT_ID}" \
      -d "text=${MESSAGE}")

    # 顯示 API 回應
    echo "Telegram API 回應:"
    echo "$RESPONSE" | jq '.' || echo "$RESPONSE"

    # 檢查是否發送成功
    if echo "$RESPONSE" | grep -q '"ok":true'; then
      echo "✓ 訊息發送成功！"
    else
      echo "✗ 訊息發送失敗"
      exit 1
    fi
```

**輸出範例（成功）**：
```
準備發送訊息到 Telegram...
Chat ID 已設定 ✓
Telegram API 回應:
{
  "ok": true,
  "result": {
    "message_id": 123,
    "chat": {
      "id": 123456789,
      "type": "private"
    },
    "text": "這是從 GitHub Actions 發送的測試訊息！"
  }
}
✓ 訊息發送成功！
```

**輸出範例（Chat ID 未設定）**：
```
準備發送訊息到 Telegram...
⚠️ 警告: TELEGRAM_CHAT_ID 未設定或為空
Telegram API 回應:
{
  "ok": false,
  "error_code": 400,
  "description": "Bad Request: chat_id is empty"
}
✗ 訊息發送失敗
```

## 使用方式

### 1. 設定 GitHub Secrets

在您的 GitHub 儲存庫中設定以下 secrets：

1. 前往 `Settings` → `Secrets and variables` → `Actions`
2. 新增以下 secrets：
   - `TELEGRAM_TOKEN`：您的 Telegram Bot Token
   - `TELEGRAM_CHAT_ID`：接收訊息的 Chat ID

### 2. 手動執行 Workflow

1. 前往 `Actions` 頁籤
2. 選擇 `Test Telegram Bot` workflow
3. 點擊 `Run workflow`
4. 輸入要發送的訊息（或使用預設訊息）
5. 點擊 `Run workflow` 按鈕

### 3. 查看執行結果

在 workflow 執行日誌中，您會看到：

1. **Chat ID 狀態檢查**（已設定或未設定）
2. **訊息發送準備訊息**
3. **Telegram API 回應**（JSON 格式）
4. **成功或失敗訊息**

## 安全性考量

### Chat ID 保護機制

此 workflow 採用以下安全措施保護敏感資訊：

1. **不顯示實際值**：Chat ID 不會在日誌中顯示
2. **狀態檢查**：只顯示是否已設定，不顯示具體內容
3. **GitHub Secrets**：使用 GitHub 的加密 Secrets 功能儲存敏感資訊

### 為什麼要保護 Chat ID？

1. **防止資訊洩露**：GitHub Actions 日誌可能是公開的（對於公開儲存庫）
2. **最小權限原則**：只顯示必要的狀態資訊用於除錯
3. **合規性**：符合資料保護最佳實踐
4. **防止濫用**：避免他人使用您的 Chat ID 發送訊息

## 錯誤處理

### 常見錯誤訊息

| 錯誤訊息                                | 原因                | 解決方法                                     |
| --------------------------------------- | ------------------- | -------------------------------------------- |
| `⚠️ 警告: TELEGRAM_CHAT_ID 未設定或為空` | Secret 未設定或為空 | 在 GitHub Settings 中設定 `TELEGRAM_CHAT_ID` |
| `✗ 訊息發送失敗`                        | API 呼叫失敗        | 檢查 Token 和 Chat ID 是否正確               |
| `Bad Request: chat_id is empty`         | Chat ID 為空        | 確認 Secret 已正確設定且無空格               |
| `Unauthorized`                          | Token 錯誤          | 檢查 `TELEGRAM_TOKEN` 是否正確               |
| `jq: command not found`                 | jq 未安裝           | 不影響功能，只是回應格式不美觀               |

### 除錯步驟

1. **檢查 Secrets 設定**
   - 前往 `Settings` → `Secrets and variables` → `Actions`
   - 確認 `TELEGRAM_TOKEN` 和 `TELEGRAM_CHAT_ID` 都已設定
   - 確認沒有多餘的空格或換行符號

2. **驗證 Token**
   - 使用 Telegram 的 BotFather 確認 Token 是否有效
   - Token 格式應為：`123456789:ABCdefGHIjklMNOpqrsTUVwxyz`

3. **驗證 Chat ID**
   - 可以透過發送訊息給 Bot 後，使用 `getUpdates` API 取得
   - Chat ID 可能是正數（私人對話）或負數（群組）
   - 範例：`123456789` 或 `-987654321`

4. **查看完整錯誤訊息**
   - 在 Actions 執行日誌中查看 Telegram API 的完整回應
   - 根據 `error_code` 和 `description` 判斷問題

## 進階功能

### 取得 Chat ID 的方法

如果您不知道自己的 Chat ID，可以使用以下方法：

1. **使用 getUpdates API**
   ```bash
   curl https://api.telegram.org/bot<YOUR_BOT_TOKEN>/getUpdates
   ```

2. **使用 @userinfobot**
   - 在 Telegram 中搜尋 `@userinfobot`
   - 發送任意訊息給它
   - 它會回覆您的 Chat ID

3. **使用 @get_id_bot**
   - 在 Telegram 中搜尋 `@get_id_bot`
   - 點擊 Start
   - 它會顯示您的 User ID（即 Chat ID）

### 自訂訊息格式

您可以在 workflow 中加入更多格式化選項：

```yaml
run: |
  MESSAGE="${{ github.event.inputs.message }}"

  # 加入時間戳記
  TIMESTAMP=$(date '+%Y-%m-%d %H:%M:%S')
  FORMATTED_MESSAGE="[${TIMESTAMP}] ${MESSAGE}"

  # 發送格式化訊息
  curl -s -X POST "https://api.telegram.org/bot${TELEGRAM_TOKEN}/sendMessage" \
    -d "chat_id=${TELEGRAM_CHAT_ID}" \
    -d "text=${FORMATTED_MESSAGE}"
```

## 測試建議

1. **首次設定**：先使用預設訊息測試
2. **驗證設定**：確認 Chat ID 狀態檢查顯示「已設定 ✓」
3. **檢查回應**：確認 API 回應中的 `"ok": true`
4. **實際接收**：在 Telegram 中確認是否收到訊息
5. **測試錯誤處理**：故意使用錯誤的 Token 或 Chat ID 測試錯誤處理

## 相關資源

- [Telegram Bot API 文件](https://core.telegram.org/bots/api)
- [GitHub Actions 文件](https://docs.github.com/en/actions)
- [GitHub Secrets 管理](https://docs.github.com/en/actions/security-guides/encrypted-secrets)


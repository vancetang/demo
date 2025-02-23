import requests
import os

# 你的 Bot Token
TOKEN = os.getenv("TELEGRAM_TOKEN")
# 聊天 ID，可以是個人或群組的 ID
CHAT_ID = os.getenv("TELEGRAM_CHAT_ID")

# Telegram API 的基礎 URL
BASE_URL = f"https://api.telegram.org/bot{TOKEN}"


# 發送訊息的函數
def send_message(chat_id, text):
    url = f"{BASE_URL}/sendMessage"
    payload = {"chat_id": chat_id, "text": text}
    response = requests.post(url, json=payload)
    return response.json()


# 測試接收訊息
url = f"{BASE_URL}/getUpdates"
response = requests.get(url)
# print(response.json())

# 測試發送訊息
result = send_message(CHAT_ID, "嗨，這是用程式發送的訊息！")
print(result)

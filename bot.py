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


if __name__ == "__main__":
    if not TOKEN or not CHAT_ID:
        print("錯誤：TELEGRAM_TOKEN 或 TELEGRAM_CHAT_ID 未設定")
    else:
        result = send_message(CHAT_ID, "嗨，這是從 bot.py 發送的測試訊息！")
        print(result)

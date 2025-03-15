# Spring boot 測試專案

主要測試 spring boot, github action...etc 網路上常看到的相關功能  
- GitHub Action Workflow
  - add-labels: 新增修改labels
  - cleanup-cache: 清除action cache(目前會有資料日期轉換問題)
  - close-stale-issues-prs: 關閉舊的issues/prs
  - create-release: 依tag產生release
  - gmail: 寄發gmail
  - notify-collaborators: 發送github訊息(repo的餐與者)
  - sync-branchs-and-notify: main同步變更到develop，並且失敗時會通知
  - telegram: 發送telegram
  - codeql: 靜態程式弱點掃描
  - test: 測試功能用(目前保留直接執行java功能, maven clean package...)
  - translate: 翻譯(因為無openai token所以無法測試)
  - label-pr 替PR增加label(label要存在不然會發生錯誤)(使用actions/labeler@v5他的格式有更新請注意)
- Spring-Boot v3版本
- 測試保護分支(Master)，一定要PR才可以
- 測試新增本地端 pre-push 保護 master & 檢查是否有更新


## Star History

[![Star History Chart](https://api.star-history.com/svg?repos=vancetang/demo&type=Date)](https://star-history.com/#vancetang/demo&Date)


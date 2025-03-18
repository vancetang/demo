# Spring boot 測試專案

主要测试 spring boot, github action...etc 网路上常看到的相关功能

-   GitHub动作工作流程
    -   add-labels: 新增修改labels
    -   cleanup-cache: 清除action cache(目前会有资料日期转换问题)
    -   close-stale-issues-prs: 关闭旧的issues/prs
    -   create-release: 依tag产生release
    -   gmail: 寄发gmail
    -   notify-collaborators: 发送github讯息(repo的餐与者)
    -   sync-branchs-and-notify: main同步变更到develop，并且失败时会通知
    -   telegram: 发送telegram
    -   codeql: 静态程式弱点扫描
    -   test: 测试功能用(目前保留直接执行java功能, maven clean package...)
    -   translate: 翻譯(因為無openai token所以無法測試)
    -   label-pr 替PR增加label(label要存在不然會發生錯誤)(使用actions/labeler@v5他的格式有更新請注意)
-   Spring-Boot v3版本
-   測試保護分支(Master)，一定要PR才可以
-   测试新增本地端 pre-push 保护 master & 检查是否有更新
-   issues回报增加英文版
-   ~~PR Template测试多国语言版本~~

## 星历史

[![Star History Chart](https://api.star-history.com/svg?repos=vancetang/demo&type=Date)](https://star-history.com/#vancetang/demo&Date)

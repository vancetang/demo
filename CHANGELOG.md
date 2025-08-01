# Changelog

## [2.0.0](https://github.com/vancetang/demo/compare/v1.9.0...v2.0.0) (2025-07-25)


### ⚠ BREAKING CHANGES

* **deps:** 更新 Spring Boot 版本至 3.5.4
* **deps:** 更新 Spring Boot 版本至 3.5.3
* **parent:** 更新 Spring Boot 版本至 3.5.2
* **deps:** 更新 Spring Boot 版本至 3.5.0

### Build System

* **deps:** 更新 Spring Boot 版本至 3.5.0 ([f0c0b45](https://github.com/vancetang/demo/commit/f0c0b4577fbb4d76b07589ae10550bd85d022f77))
* **deps:** 更新 Spring Boot 版本至 3.5.3 ([b53198d](https://github.com/vancetang/demo/commit/b53198d33f8cca4b4e387c2cc11be307767642d4))
* **deps:** 更新 Spring Boot 版本至 3.5.4 ([224c2cf](https://github.com/vancetang/demo/commit/224c2cfd1a1ac88e0d3b7666f1d265af3db0fb0b))
* **parent:** 更新 Spring Boot 版本至 3.5.2 ([2a576c6](https://github.com/vancetang/demo/commit/2a576c6d8e928d8e7f694accf4484724ed5a1f10))

## [1.9.0](https://github.com/vancetang/demo/compare/v1.8.0...v1.9.0) (2025-05-07)


### Features

* **CompletableFutureThreadPool:** 新增基於 CompletableFuture 的線程池任務執行工具備份 ([732db7b](https://github.com/vancetang/demo/commit/732db7b5d781954a2f4b3cbe8682b77ebff3f2d1))
* **CompletableFutureThreadPool:** 新增帶前綴名稱的任務執行方法 ([e0fb8a7](https://github.com/vancetang/demo/commit/e0fb8a7637e7794552c385734dc171da957c91d2))

## [1.8.0](https://github.com/vancetang/demo/compare/v1.7.0...v1.8.0) (2025-04-30)


### Features

* 配合SonarLint檢測，更新 DateUtil 類別以支援 Java 8 日期時間 API，並改善日期解析邏輯 ([de85578](https://github.com/vancetang/demo/commit/de85578258481d99653c0bcd4088b74a0b0b289c))


### Bug Fixes

* **DateUtil:** 增加 null 檢查的錯誤訊息以改善可讀性 ([81b2dce](https://github.com/vancetang/demo/commit/81b2dce7be1ae9ea0cb917754fe547fbd9805d2e))

## [1.7.0](https://github.com/vancetang/demo/compare/v1.6.0...v1.7.0) (2025-04-29)


### Features

* 新增翻譯 README 的腳本 ([0dd8ead](https://github.com/vancetang/demo/commit/0dd8ead6d48e4cc1f0fdd1e0624fa70582db5452))

## [1.6.0](https://github.com/vancetang/demo/compare/v1.5.0...v1.6.0) (2025-04-10)


### Features

* 將 util 靜態工具類程式，標記為UtilityClass(lombok annotation) ([b13aa35](https://github.com/vancetang/demo/commit/b13aa358cb43174054f4949f4a4731be876297ff))

## [1.5.0](https://github.com/vancetang/demo/compare/v1.4.0...v1.5.0) (2025-04-08)


### Features

* **ApiResult:** 新增不可修改檢視的資料回應方法，簡化資料設定邏輯 ([bd5fc61](https://github.com/vancetang/demo/commit/bd5fc61782f6daabd52afbf65aa6f9a5d9f4e885))

## [1.4.0](https://github.com/vancetang/demo/compare/v1.3.0...v1.4.0) (2025-04-08)


### Features

* **GlobalExceptionHandler:** 更新全域例外狀況處理以支援 @RestController 註解 ([ab330fb](https://github.com/vancetang/demo/commit/ab330fb03320981ddb4d01686a2f18a729904536))
* **JsonUtil:** 新增 JSON 處理工具類別以支援物件轉換為 JSON 字串 ([98e8a9f](https://github.com/vancetang/demo/commit/98e8a9fa65cacb5da5b525036a3e631c909734af))
* **Order:** 新增訂單建立 API 以支援訂單請求物件 ([8933316](https://github.com/vancetang/demo/commit/893331698b088255221597e9ab91c64de17169c2))
* **VanceTest:** 更新測試類別以新增資料映射及 JSON 轉換功能 ([1f95987](https://github.com/vancetang/demo/commit/1f9598710df720038fb7721becc717b3408b6236))


### Bug Fixes

* **OrderRequest:** 增加Excpeiton文字描述 ([cf91571](https://github.com/vancetang/demo/commit/cf9157134dc2f2bb315da2291609fa345225017f))

## [1.3.0](https://github.com/vancetang/demo/compare/v1.2.0...v1.3.0) (2025-04-07)


### Features

* **bot:** 新增主程式以測試發送訊息功能 ([6d78ed7](https://github.com/vancetang/demo/commit/6d78ed7d89266d1ca28991c2d22b53bcc9d8ffeb))
* **bot:** 新增主程式以測試發送訊息功能 ([9fab58b](https://github.com/vancetang/demo/commit/9fab58b2f07d50a6abf2ca20b08f64e4c2a6ecc1))
* **ci:** 增加手動執行清理舊快取的功能 ([fc2c42b](https://github.com/vancetang/demo/commit/fc2c42bfb2de332422c201138e30f1affb8b5a13))
* **ci:** 新增清理舊快取的工作流程 ([8128235](https://github.com/vancetang/demo/commit/812823592f878c68ee815b85d2611b5a9fc6c401))
* **ci:** 新增翻譯工作流程以支援手動執行 ([b08e599](https://github.com/vancetang/demo/commit/b08e599a22f0303dd302359e47b9962879057917))
* **ci:** 更新測試工作流程，增加相關說明註解 ([8936329](https://github.com/vancetang/demo/commit/89363298cffe8823aab341e8c15429fe3b20089b))
* **ci:** 更新測試工作流程以支援 mvnw 檔案執行權限設定 ([7a08d3f](https://github.com/vancetang/demo/commit/7a08d3f5d9f250f0332594b3b511b0a72a36b6bc))
* **ci:** 更新測試工作流程以支援 mvnw 檔案執行權限設定 ([bed0887](https://github.com/vancetang/demo/commit/bed088709a9e9b19b99af38b1c025b5d87ac5695))
* **config:** 新增 BodyTransferFilter 的過濾器配置 ([2b0074d](https://github.com/vancetang/demo/commit/2b0074d81f49f71cf21e8961414fbd5f4773b273))
* **config:** 更新應用程式配置以支持不同的命名策略和日期序列化 ([6e5b0e4](https://github.com/vancetang/demo/commit/6e5b0e498ac057f234faf946466e75049df05aaa))
* **constants:** 新增常數類別以集中管理應用程式常數 ([1c2fd72](https://github.com/vancetang/demo/commit/1c2fd727601c65f8e4b9c5325866b42006043052))
* **controller:** 新增用戶不存在異常處理及測試接口 ([325e242](https://github.com/vancetang/demo/commit/325e242cb5f91ffa5a87f4548dc5697b9d7ccd8c))
* **date:** 新增日期工具類別以處理日期解析與格式化 ([1c2fd72](https://github.com/vancetang/demo/commit/1c2fd727601c65f8e4b9c5325866b42006043052))
* **freemarker:** 新增多個 Freemarker 方法以處理字串和 XML escape ([0db6be1](https://github.com/vancetang/demo/commit/0db6be15fe0035496004f9048f14badde293a7d7))
* **freemarker:** 新增多個 Freemarker 方法以處理字串和日期 ([9bf195f](https://github.com/vancetang/demo/commit/9bf195fb7681d600fd0ff74acde27ab23469dd0b))
* **freemarker:** 新增檢查資料模型有效性的功能 ([a009a6a](https://github.com/vancetang/demo/commit/a009a6af2a04169d06f3f5024baccfd7c9dd0e2d))
* **github-actions:** 新增 Telegram Bot 測試工作流程 ([1ffdc48](https://github.com/vancetang/demo/commit/1ffdc483b3e38434a3a9755574a5f3ecf11bd487))
* **github-actions:** 新增 Telegram Bot 測試工作流程 ([b1f9c92](https://github.com/vancetang/demo/commit/b1f9c921edc7039f7ef2906e53e35c0da5884382))
* **gmail:** 更新 Gmail 通知工作流程名稱及內容 ([926caf6](https://github.com/vancetang/demo/commit/926caf639c18d3572c8d79ef0afb1286e1161e47))
* **gmail:** 更新 Gmail 通知工作流程名稱及內容 ([55e1944](https://github.com/vancetang/demo/commit/55e1944cc83d2392ed0d16ad72b6eab8debe319d))
* **HolidayDataTool:** 重構假日資料處理邏輯，新增記錄處理方法 ([469fc38](https://github.com/vancetang/demo/commit/469fc38f177bd72b52f69263aa1a50e044e719b9))
* **labeler:** 新增標籤配置以支持 feature 和 bug 標籤 ([3e4a1c2](https://github.com/vancetang/demo/commit/3e4a1c2c0edb1b6271ab1b59106a238be91b9da1))
* **labeler:** 新增標籤配置以支持 feature 和 bug 標籤 ([b372a98](https://github.com/vancetang/demo/commit/b372a9839be7d9348f9f1a66e2ed45a9434d1486))
* **labeler:** 更新標籤管理配置以包含 XML 文件 ([c0848a9](https://github.com/vancetang/demo/commit/c0848a9ead6e08e16cf714a9ef0fc13573079d96))
* **labeler:** 更新標籤配置以使用新的文件變更格式 ([58fd937](https://github.com/vancetang/demo/commit/58fd937cc1fb50e3e572b2e9ede7e6112fd619c1))
* **lint:** 新增 PR 標題驗證失敗時的評論功能 ([c4abff5](https://github.com/vancetang/demo/commit/c4abff5d0b6ee6989abf3ddcdd9e05e16077e3bc))
* **Markdown:** 新增 Markdown 轉 HTML 的功能 ([735803c](https://github.com/vancetang/demo/commit/735803ca51f8911ae754b33b5325ad8849eeda84))
* **Markdown:** 新增 Markdown 轉 HTML 的註解 ([dea5b40](https://github.com/vancetang/demo/commit/dea5b4003909274837533a0b4559d657bff55b9d))
* **sonarqube:** 新增 SonarCloud 掃描工作流程 ([9303d38](https://github.com/vancetang/demo/commit/9303d3853d4dad7b5d8cc2a209808795baff2f51))
* **threadpool:** 新增 CompletableFuture 簡單線程池實現 ([aee9038](https://github.com/vancetang/demo/commit/aee9038783757806e00f263769b42d6c30a505b1))
* **util:** 新增 FtlUtil 類以支持 FreeMarker 模板處理 ([e7a146d](https://github.com/vancetang/demo/commit/e7a146d1d49a8ee3976dfd17baf37afcbeb20040))
* **util:** 新增 LambdaUtil 和 TimeUtil 工具類別 ([830b1f2](https://github.com/vancetang/demo/commit/830b1f26aac2b332ac30eb3a77d2b5fb755f4881))
* **util:** 新增 LambdaUtil 和 TimeUtil 工具類別 ([abf244f](https://github.com/vancetang/demo/commit/abf244f4d60d4000f3f8c6791a93fd35919ed9ba))
* **util:** 新增安全獲取映射值的方法 ([074e230](https://github.com/vancetang/demo/commit/074e2303b88c18c132a3b1b087e47a152e43cc97))
* **util:** 新增安全獲取映射值的方法 ([1966b0c](https://github.com/vancetang/demo/commit/1966b0c843b52e33aa4f3f156018831091cda5f4))
* **util:** 新增支持 TLSv1.2 的 RestTemplate 創建方法 ([7b8710f](https://github.com/vancetang/demo/commit/7b8710f6105d7c4c52f96e49376208c5545ea27e))
* **util:** 新增支持 TLSv1.2 的 RestTemplate 創建方法 ([1ab07bf](https://github.com/vancetang/demo/commit/1ab07bfdc720bede80f35fd480bb9470fb60a927))
* **util:** 新增支持 TLSv1.2 的 RestTemplate 創建方法 ([#114](https://github.com/vancetang/demo/issues/114)) ([988143d](https://github.com/vancetang/demo/commit/988143d6512814ac7393e84edb50866a2c333ec3))
* **util:** 更新工具類別以使用 Apache Commons Collections ([1c2fd72](https://github.com/vancetang/demo/commit/1c2fd727601c65f8e4b9c5325866b42006043052))
* **util:** 簡化資料模型有效性檢查邏輯 ([8485cfb](https://github.com/vancetang/demo/commit/8485cfbc53f8f713d290f614188427894c63b1f0))
* **VirtualThreadPool:** 新增虛擬線程池的共用程式及單元測試以驗證多任務並行執行功能 ([5b0be07](https://github.com/vancetang/demo/commit/5b0be07b74403945c15c363b03f15e58d9caa198))
* **workflow:** 在標籤創建過程中新增日誌輸出 ([e7753df](https://github.com/vancetang/demo/commit/e7753dfdde649070ff5610ff0e292deddb49365e))
* **workflow:** 增加重新設定labels的功能 ([788c83a](https://github.com/vancetang/demo/commit/788c83ac2965738f0968106be20c2ed09fe3c7e7))
* **workflow:** 將 Telegram Bot 測試程式移至 bot.py 檔案 ([4bffcca](https://github.com/vancetang/demo/commit/4bffccab4696a041ca4229baccaf69352d1078ae))
* **workflow:** 新增 Dependency-Check 掃描步驟 ([4cdf44d](https://github.com/vancetang/demo/commit/4cdf44dc93550e149a74cf008eac3909339ff19f))
* **workflow:** 新增 Dependency-Check 掃描步驟 ([0af8242](https://github.com/vancetang/demo/commit/0af8242e7623b42c6983c1cf8b2da160bd93e8c5))
* **workflow:** 新增 GitHub Actions 工作流程以管理 Labels 和釋出版本 ([0052b3b](https://github.com/vancetang/demo/commit/0052b3b2120c2a35838bcf1095123d0fb40a96a6))
* **workflow:** 新增 GitHub Actions 工作流程以管理 Labels 和釋出版本 ([dc4b3e2](https://github.com/vancetang/demo/commit/dc4b3e2ddc9a890608ca2b4ede2470c2e6212f6f))
* **workflow:** 新增 GitHub Actions 標籤管理工作流程及配置文件 ([5a11625](https://github.com/vancetang/demo/commit/5a1162576605c7b568673fec9075a8d67f15a10b))
* **workflow:** 新增 GitHub Actions 標籤管理工作流程及配置文件 ([8290f0a](https://github.com/vancetang/demo/commit/8290f0a3ab3d4e467c63303cd9f56ddc66cc4f12))
* **workflow:** 新增 README 翻譯工作流程支援中文簡體、繁體及英文 ([1bdc61b](https://github.com/vancetang/demo/commit/1bdc61ba25aa7a20fcc8be04b0d4aaeb2055bfa7))
* **workflow:** 新增 README 翻譯工作流程支援中文簡體、繁體及英文 ([efd77dd](https://github.com/vancetang/demo/commit/efd77dd7358694643b58e1282a84b949869f0778))
* **workflow:** 新增 SonarCloud 掃描工作流程 ([e0c14a8](https://github.com/vancetang/demo/commit/e0c14a8e8577436a4d20d92705c70102a075639c))
* **workflow:** 新增 Telegram Bot 於手動執行時輸入必要值 ([275d328](https://github.com/vancetang/demo/commit/275d3284c471f284ca4acf629c52d3299be94269))
* **workflow:** 新增 Telegram Bot 測試工作流程 ([e619371](https://github.com/vancetang/demo/commit/e619371ed610ddcffcb5b4f1e8c59e8b9287bb16))
* **workflow:** 新增 Telegram Bot 測試工作流程 ([08f565b](https://github.com/vancetang/demo/commit/08f565bf7cead7056e919156e6ed9ba7489293b4))
* **workflow:** 新增 Telegram Bot 訊息輸入功能，並檢查訊息是否為空 ([2b8d6ce](https://github.com/vancetang/demo/commit/2b8d6ce74acb8ec4663e252ee555e0dba60335ba))
* **workflow:** 新增 Telegram Bot 預設訊息 ([ff51134](https://github.com/vancetang/demo/commit/ff511344b011dfd05d39dcea43f7cf3aac0e69c0))
* **workflow:** 新增「stale」標籤以標示不活躍的問題或拉取請求 ([64086a5](https://github.com/vancetang/demo/commit/64086a5ed4004094037f0bd9bb65e8d5f4acaa67))
* **workflow:** 新增可手動設定過期天數的功能 ([7864156](https://github.com/vancetang/demo/commit/7864156978908d83310168eb684fbd8318e3e16f))
* **workflow:** 新增標籤以支持前端、後端、配置和構建相關的標籤 ([46cb3d6](https://github.com/vancetang/demo/commit/46cb3d694357afd78a2e20a48a01b7ede9f42dee))
* **workflow:** 新增標籤創建過程的開始日期設定 ([d4f4809](https://github.com/vancetang/demo/commit/d4f48091328a13153931528a4689dbc7ce621e8b))
* **workflow:** 新增翻譯工作流程以支持多語言文件翻譯 ([a0b43cf](https://github.com/vancetang/demo/commit/a0b43cf4993efd61b82a131b21a7fe3c2a9b23e2))
* **workflow:** 新增自動關閉不活躍的問題和拉取請求功能 ([87d9b99](https://github.com/vancetang/demo/commit/87d9b998cc684d1002d3dfcdadf4fd5620ec8209))
* **workflow:** 新增自動關閉不活躍的問題和拉取請求功能 ([fa5b607](https://github.com/vancetang/demo/commit/fa5b60778bdf12908b42d9bfb1264c624a091385))
* **workflow:** 更新 Dependency-Check 報告格式為 ALL 並新增 SARIF 上傳步驟 ([94ee4e3](https://github.com/vancetang/demo/commit/94ee4e3aa5a60a184882c18a198640e1ff4c9678))
* **workflow:** 更新 Dependency-Check 專案名稱及 JAVA_HOME 環境變數 ([a434530](https://github.com/vancetang/demo/commit/a43453032ad65fbe90c73a18c1028aab43f24241))
* **workflow:** 更新 Telegram Bot 測試流程，移除Python改用crul ([c1c877a](https://github.com/vancetang/demo/commit/c1c877aa7de6b44d90348728121b81febfb2eeee))
* **workflow:** 更新 Telegram Bot 測試流程，移除Python改用curl ([694f7e7](https://github.com/vancetang/demo/commit/694f7e7fde884d98bccdae11f7970a416576aed0))
* **workflow:** 更新標籤創建過程中的 JSON 解碼邏輯 ([5ccea71](https://github.com/vancetang/demo/commit/5ccea7191e5d7e67bbea6cb92a79c8503029c308))
* **workflow:** 更新翻譯工作流程以包含翻譯標題 ([7d2b77a](https://github.com/vancetang/demo/commit/7d2b77a13898918705c192cd5c21822cf768a56d))
* **workflow:** 更新翻譯工作流程以添加英文 README ([9dbd311](https://github.com/vancetang/demo/commit/9dbd31125e4f7bf6471d45be4888e77f42b9dcbf))
* **workflow:** 更新翻譯工作流程以添加英文 README ([bf989cd](https://github.com/vancetang/demo/commit/bf989cd3fed65997baecf5a67180726807b22d34))
* **workflow:** 更新自動關閉不活躍問題和拉取請求的工作流程名稱及版本 ([f5055b6](https://github.com/vancetang/demo/commit/f5055b683a005e7aee878f38989feaf01758a6f7))
* **workflow:** 更新自動關閉不活躍的問題和拉取請求功能的描述 ([fca29aa](https://github.com/vancetang/demo/commit/fca29aa2d46ff061741fbd730abb3274153c3f3e))
* **workflow:** 調整工作流程名稱及版號，並且新增stale標籤 ([e432c31](https://github.com/vancetang/demo/commit/e432c314c3af1f3e53bfc7500b61816b7f27f86f))
* **workflow:** 調整自動關閉不活躍問題和拉取請求的時間設定 ([4612141](https://github.com/vancetang/demo/commit/4612141e3a066f0987e5742881d750cfd0b06ad2))
* **workflow:** 調整自動關閉不活躍問題和拉取請求的時間設定 ([92e5915](https://github.com/vancetang/demo/commit/92e59153fd21307ebc6e06b8bcd8a8f3d9dade85))
* **workflow:** 重構 CodeQL 和 Dependency-Check 工作流程 ([a7e83ba](https://github.com/vancetang/demo/commit/a7e83baeddd2cd22d1cff6f0a868e3a5e4e52f8b))
* **workflow:** 重構不活躍問題和 PR 自動關閉流程 ([8f0afc8](https://github.com/vancetang/demo/commit/8f0afc8bdea33cddfa2563cfce8a2f3861aa8830))
* **workflow:** 重構不活躍問題和 PR 自動關閉流程 ([bb16a1b](https://github.com/vancetang/demo/commit/bb16a1b724bc50b1edb738e72d334b3df55817c5))
* **工作流程:** 修改新增標籤工作流程以支持標籤的更新 ([7018c4b](https://github.com/vancetang/demo/commit/7018c4bf2bb12ee25d4546ef9e42fe1adab1687b))
* **工作流程:** 修改新增標籤工作流程以支持標籤的更新 ([a879b3b](https://github.com/vancetang/demo/commit/a879b3b7d482c5d8f628b44606adf5de1338bba7))
* **工作流程:** 修改重置標籤工作流程以支持手動觸發 ([08e29f1](https://github.com/vancetang/demo/commit/08e29f179726b2bdfe15b59ce5c1989d34cd715a))
* **工作流程:** 修改重置標籤工作流程以支持手動觸發 ([bdca7b2](https://github.com/vancetang/demo/commit/bdca7b252d1a191c20574191c159d70f2d8bf1d9))
* **工作流程:** 刪除新增標籤工作流程並新增重置標籤工作流程 ([88cda9b](https://github.com/vancetang/demo/commit/88cda9b9d6df0ba25e9fb9a1dbeca687304d2fa6))
* **工作流程:** 將標籤名稱進行 URI 編碼以確保正確處理 ([a868545](https://github.com/vancetang/demo/commit/a868545043163be8bf107d2e23a2a0315bd325f5))
* **工作流程:** 將標籤名稱進行 URI 編碼以確保正確處理 ([b0197b7](https://github.com/vancetang/demo/commit/b0197b72106112544fbed477bc9b2ae0b38614c3))
* **工作流程:** 新增 GitHub Actions 工作流程以自動新增預設 Labels ([6eb9b04](https://github.com/vancetang/demo/commit/6eb9b0415d0793a9597cec84cbee45a463e68d88))
* **工作流程:** 新增 PR 關閉時通知所有協作者的功能 ([1093eae](https://github.com/vancetang/demo/commit/1093eae2eaab5247db59034fa7089f5758580ec1))
* **工作流程:** 新增 PR 關閉時通知所有協作者的功能 ([0d57fda](https://github.com/vancetang/demo/commit/0d57fda8469e77a3caa61b8eeace75b3680a19c8))
* **工作流程:** 新增「進行中」標籤以標示工作進度 ([1c6c9cb](https://github.com/vancetang/demo/commit/1c6c9cbfc3eff75510a8543d81348f424380387e))
* **工作流程:** 新增CodeQL分析工作流程 ([388af12](https://github.com/vancetang/demo/commit/388af12a9778f5eb8206e7e78ea2b7dc9632a9be))
* **工作流程:** 新增CodeQL分析工作流程 ([6bdecfd](https://github.com/vancetang/demo/commit/6bdecfd4a6966f3dcdf20bf2043d0018272c0fba))
* **工作流程:** 新增CodeQL分析工作流程並簡化設定 ([170595e](https://github.com/vancetang/demo/commit/170595e566c9b16dd583613a6e457dbc6d257cdb))
* **工作流程:** 新增CodeQL分析工作流程並簡化設定 ([2231df3](https://github.com/vancetang/demo/commit/2231df3751f6bb16908285ae272a6020fb02d57b))
* **工作流程:** 新增CodeQL分析工作流程取代舊有設定 ([4ac89b3](https://github.com/vancetang/demo/commit/4ac89b39e7af45ed7584e447891d31fb058ffef0))
* **工作流程:** 新增CodeQL分析工作流程取代舊有設定 ([6e83585](https://github.com/vancetang/demo/commit/6e83585bd08484cba7fd53f287b19efc25cf0379))
* **工作流程:** 新增CodeQL功能 ([283cd55](https://github.com/vancetang/demo/commit/283cd550157241244cc5d294371ebc620df92475))
* **工作流程:** 新增多個標籤及其描述以增強標籤功能 ([220a760](https://github.com/vancetang/demo/commit/220a760aa4a39010c3447719b40d27142497a032))
* **工作流程:** 新增標籤描述以增強標籤信息並刪除重置標籤工作流程 ([c25aa11](https://github.com/vancetang/demo/commit/c25aa1195de3dc2de4b8748e41fac0bf890e69e2))
* **工作流程:** 新增標籤查詢功能以顯示當前標籤 ([14fc22d](https://github.com/vancetang/demo/commit/14fc22d066f5e92a7a76db35ba5f9d7f548eb7a9))
* **工作流程:** 新增標籤檢查日誌以增強除錯能力 ([69cb775](https://github.com/vancetang/demo/commit/69cb77594c83fac4e6295c55220c8b451b124c7a))
* **工作流程:** 新增權限設置以允許寫入內容和問題 ([6b994ef](https://github.com/vancetang/demo/commit/6b994efb983c5f70833e421060c98b93445b1b84))
* **工作流程:** 新增權限設置以允許寫入內容和問題 ([f5940bb](https://github.com/vancetang/demo/commit/f5940bbf0189be42b004f0e91f334202d49ce0c3))
* **工作流程:** 新增測試標籤以標記需要測試的功能 ([6de0238](https://github.com/vancetang/demo/commit/6de0238aa50df4cc44bd553df84e946f93e9d77f))
* **工作流程:** 新增測試標籤以標記需要測試的功能 ([e03ed92](https://github.com/vancetang/demo/commit/e03ed922463e5e238bf2aeef1c05d3c4c81b290f))
* **工作流程:** 新增預設 Labels 工作流程以支持手動觸發 ([dcc0079](https://github.com/vancetang/demo/commit/dcc0079a298e41d9f647cc9797570819a96aaae2))
* **工作流程:** 新增預設 Labels 工作流程以支持手動觸發 ([d506a23](https://github.com/vancetang/demo/commit/d506a2311bc2fd0ff17f5863d39701bb3e90f41d))
* **工作流程:** 更新新增標籤工作流程以使用 heredoc 格式定義標籤 ([7197d1a](https://github.com/vancetang/demo/commit/7197d1a2f7170c1e0ebe052567fbea3f5e647f67))
* **工作流程:** 更新新增標籤工作流程以移除不必要的標籤描述 ([e853356](https://github.com/vancetang/demo/commit/e8533567d3321ec20822c274b076ac6d81d0b683))
* **工作流程:** 更新標籤描述以簡化信息並添加更新提示 ([47214f1](https://github.com/vancetang/demo/commit/47214f18fb83798f6f2d84dd0a15b19aca9d4f24))
* **工作流程:** 更新標籤格式以使用 JSON 陣列並簡化標籤處理邏輯 ([4edd6e5](https://github.com/vancetang/demo/commit/4edd6e5ab26cb40636fedbf5f625583408673adc))
* 新增 GitHub Actions 工作流程以自動創建版本釋出 ([2228d0c](https://github.com/vancetang/demo/commit/2228d0c11367879903208ec05ffb555c65dad061))
* 新增 GitHub Actions 工作流程以自動創建版本釋出 ([3285f30](https://github.com/vancetang/demo/commit/3285f30d6e390b52f133f6bde46de0e09046270b))
* 新增 GitHub Actions 工作流程以自動化版本釋出 ([4dfe6f9](https://github.com/vancetang/demo/commit/4dfe6f9676cc8db281e27f75f206279eff177f2d))
* 新增 GitHub Actions 工作流程以自動化版本釋出 ([a8e0ab9](https://github.com/vancetang/demo/commit/a8e0ab9bfbbbb79f7911ce570d468475007037e5))
* 新增 README 翻譯工作流程與 PR 模板 ([6b00b25](https://github.com/vancetang/demo/commit/6b00b25ef09124330a352d3af3f2b874df85fcda))
* 新增 README 翻譯工作流程與 PR 模板 ([7059e8a](https://github.com/vancetang/demo/commit/7059e8a153f9848cde5cf72a7341a3d0e87273f7))
* 新增 release-please GitHub Action 工具說明 ([4a4288b](https://github.com/vancetang/demo/commit/4a4288b481fbf96f40810b8fd026545fc4346d65))
* 新增util工具類及constants常數類 ([f5f8f33](https://github.com/vancetang/demo/commit/f5f8f3306a0a5540d3df9fec81d0257343689ced))
* 新增多個 Freemarker 方法以處理字串和日期及調整package name ([b56da34](https://github.com/vancetang/demo/commit/b56da34866e509b0a5fcc8bec7ec2f375c326a98))
* 新增多項功能及優化現有代碼 ([58ac012](https://github.com/vancetang/demo/commit/58ac0127ba1a4dad1c254269276698bd8b2edf4a))
* 新增錯誤回報模板以改善問題追蹤流程 ([5115738](https://github.com/vancetang/demo/commit/5115738af2ecc139d88dd246f01b26402909b68a))
* 新增錯誤回報模板以改善問題追蹤流程 ([a415d7f](https://github.com/vancetang/demo/commit/a415d7fab89b68842679503262e0e820f5e06676))
* 更新 release-please.yml 配置以跳過 PR 直接生成 Release 和標籤 ([0bb2a79](https://github.com/vancetang/demo/commit/0bb2a792e6344346e646e17fcd4ea3a37a6ea4ff))
* 更新 release-please.yml 配置以跳過 PR 直接生成 Release 和標籤 ([85ecc6c](https://github.com/vancetang/demo/commit/85ecc6c36f67e4f0fd30d72eb15b93c0011bc519))
* 更新測試工作流程並新增檔案雜湊計算功能 ([4d7ae76](https://github.com/vancetang/demo/commit/4d7ae76b3690254ec1308863aeea1ca76aab55c5))
* 更新錯誤回報模板以改善使用者體驗 ([0e21a4f](https://github.com/vancetang/demo/commit/0e21a4f5ebb1a70d3728fa6d70f1299aa76e24ad))
* **模板:** 將截圖輸入類型從輸入框改為多行文本框 ([2b0e786](https://github.com/vancetang/demo/commit/2b0e78654d8fdb65b6d6c673985146b8c3100639))


### Bug Fixes

* **CompletableFutureThreadPool:** 修改異常處理邏輯以返回 null 而非拋出異常 ([fbb6656](https://github.com/vancetang/demo/commit/fbb665614b3bb6d12f82f49b1ad53b21fdb67210))
* **config:** 將 gemini 的 summary 設置為 false ([94a4444](https://github.com/vancetang/demo/commit/94a4444f1ea656bf7b7d4d89b18e0fbbb9e72306))
* **freemarker:** 更新 FreeMarker 版本至 2.3.34 ([573a9cf](https://github.com/vancetang/demo/commit/573a9cfe48b152b4f64d1426d51612d74cd13e8a))
* **freemarker:** 更新模板檢查延遲時間為 60 秒 ([4d62502](https://github.com/vancetang/demo/commit/4d625021406329cab45699e6290a092c99ce0290))
* **HolidayDataTool:** 修正描述文字的串接方式 ([090ee95](https://github.com/vancetang/demo/commit/090ee952e090d1372a51b1fc6b3d442438502e02))
* **MarkdownToHtml:** 修正測試字串以包含標題標記 ([cbfd707](https://github.com/vancetang/demo/commit/cbfd7073c9ec3a658f8a12d4c727133b34c15208))
* **pom.xml:** 排除相依 commons-codec 1.17.2，改用 commons-codec 1.18.0 ([d111c34](https://github.com/vancetang/demo/commit/d111c34ad83d33a3cc7f6a75a74580ea18a29acd))
* **pom.xml:** 排除相依 commons-codec 1.17.2，改用 commons-codec 1.18.0 ([f60e4cd](https://github.com/vancetang/demo/commit/f60e4cd4aae5c1069c7b45a8a292635eb2d153df))
* **pr-template:** 更新關聯問題的關閉語法 ([184535b](https://github.com/vancetang/demo/commit/184535bded946ba1660f5e8757eca3c2fe9d4793))
* **pr-template:** 更新關聯問題的關閉語法 ([7ca8ba5](https://github.com/vancetang/demo/commit/7ca8ba5bb73cba62af8e998cda42495dc0d9c3a7))
* **workflow:** 使用 sed 移除空白字符並檢查訊息是否為空 ([3b43d5b](https://github.com/vancetang/demo/commit/3b43d5b636d0e2033b47d2c6dd48206495e3eedf))
* **workflow:** 修正合併策略以避免合併衝突 ([f42b189](https://github.com/vancetang/demo/commit/f42b1898be280c10c470da0d370664d0567fe851))
* **workflow:** 修正快取清理工作流程中的 JSON 屬性格式 ([9995bb3](https://github.com/vancetang/demo/commit/9995bb31aa6fe7e00179d42a2e715e630d35117e))
* **workflow:** 修正快取清理工作流程中的時間計算邏輯 ([f823f1e](https://github.com/vancetang/demo/commit/f823f1e231e14a4e9235a5d0e8d1990405b72803))
* **workflow:** 修正快取清理工作流程中的秒數計算邏輯 ([9c976e5](https://github.com/vancetang/demo/commit/9c976e5b49ff422c8e8ae4d44b2138120e8ef4be))
* **workflow:** 修正快取清理工作流程中的秒數計算邏輯 ([94e8049](https://github.com/vancetang/demo/commit/94e80499e8673f14bc3ee2df61144c7479f399cb))
* **workflow:** 修正快取清理腳本中的快取 ID 列表顯示邏輯 ([15f4317](https://github.com/vancetang/demo/commit/15f4317ea59c0874c42ccb54773e8a60c6859463))
* **workflow:** 修正快取清理腳本中的時間比較邏輯 ([f3e73b0](https://github.com/vancetang/demo/commit/f3e73b0b7b8019330bea867b3fca1a701b216b0a))
* **workflow:** 修正快取清理腳本中的時間比較邏輯以正確過濾過期快取 ([e118d17](https://github.com/vancetang/demo/commit/e118d170b9f0213bd4f56b9e4a4cea59d5fe4855))
* **workflow:** 修正快取清理腳本中的時間比較邏輯以正確過濾過期快取 ([29d6705](https://github.com/vancetang/demo/commit/29d670545e7c43ca5dc300319518d878829acb81))
* **workflow:** 修正快取清理腳本中的時間比較邏輯以正確過濾過期快取 ([52dc9bf](https://github.com/vancetang/demo/commit/52dc9bfe7036ae54490a0a9b6b28da042dc2c1b1))
* **workflow:** 修正快取清理腳本中的時間比較邏輯以正確過濾過期快取 ([046d7c7](https://github.com/vancetang/demo/commit/046d7c75a5f22d5809f1dec717a69eb3bc68faa2))
* **workflow:** 修正快取清理腳本中的時間比較邏輯以正確過濾過期快取 ([05b2ee8](https://github.com/vancetang/demo/commit/05b2ee8f8900507438ab5ff1bc5be4242a2f5169))
* **workflow:** 修正快取清理腳本中的秒數計算及輸出格式 ([3a3c634](https://github.com/vancetang/demo/commit/3a3c6342cda54c4f2b42af46d6140fc08c86bb86))
* **workflow:** 修正快取清理腳本中的認證狀態命令 ([c0821d1](https://github.com/vancetang/demo/commit/c0821d1c53fa414007e8da968aa185469b273010))
* **workflow:** 修正快取清理腳本以正確列出過期快取的 ID ([63c90fb](https://github.com/vancetang/demo/commit/63c90fba160b36ac9d548f94cf1255b5fbb586c7))
* **workflow:** 修正快取清理腳本以正確列出過期快取的 ID ([79d59f4](https://github.com/vancetang/demo/commit/79d59f49df70b53239d56f95f5233959eee9901f))
* **workflow:** 修正快取清理腳本以正確刪除過期快取 ([8b74fa1](https://github.com/vancetang/demo/commit/8b74fa1fcdaf9e093e83deffc26344fe5d424204))
* **workflow:** 修正環境變數的計算方式 ([996300c](https://github.com/vancetang/demo/commit/996300cc56cb9779f978714475d3e6b2bd4cff00))
* **workflow:** 修正環境變數輸出至 GITHUB_OUTPUT ([bf7ea4a](https://github.com/vancetang/demo/commit/bf7ea4a5918370377f464e0aafa7b66fbe2363f8))
* **workflow:** 修正舊快取清理腳本中的變數計算方式 ([6ec21b8](https://github.com/vancetang/demo/commit/6ec21b8be2e7c5797b016c17ae4dd78cf5082bd5))
* **workflow:** 修正舊快取清理腳本中的變數計算方式 ([5cfedfc](https://github.com/vancetang/demo/commit/5cfedfc05fb0573825b04baf96b9fdc53d0a5de9))
* **workflow:** 將 Python 版本從 3.13 更改為 3.12 以確保相容性 ([df04eb3](https://github.com/vancetang/demo/commit/df04eb31d0d5f527a629d008c6a7bfd98a088e73))
* **workflow:** 將 Python 版本從 3.13 更改為 3.12 以確保相容性 ([22dce8f](https://github.com/vancetang/demo/commit/22dce8fb76fe8cabb857b2290cbb27573ba9c3e3))
* **workflow:** 指定 googletrans 版本以避免安裝不兼容的版本 ([b7d1a44](https://github.com/vancetang/demo/commit/b7d1a44033c125476838e76556a3288f3a2b8561))
* **workflow:** 新增分支檢查以限制工作流程僅在 develop 分支上執行 ([238248d](https://github.com/vancetang/demo/commit/238248dde14c68bd347317458b42eb189cd6d0a3))
* **workflow:** 新增分支檢查以限制工作流程僅在 develop 分支上執行 ([ea8394b](https://github.com/vancetang/demo/commit/ea8394b6fdcb474bfeab0adb2556c531c5ac4b3c))
* **workflow:** 更新合併策略以避免不必要的合併提交 ([efbed6c](https://github.com/vancetang/demo/commit/efbed6c1ad7631233435339cdac84e6bb5b2e416))
* **workflow:** 更新合併策略以避免不必要的合併提交 ([817c355](https://github.com/vancetang/demo/commit/817c3552d30d5f3ca199950d1018ef2566e6747a))
* **workflow:** 更新快取清理工作流程以修正秒數計算 ([6ed2e2d](https://github.com/vancetang/demo/commit/6ed2e2d47994519264791fb5bf200dc8c89b2deb))
* **workflow:** 更新快取清理工作流程以修正逾期天數設定 ([de39c42](https://github.com/vancetang/demo/commit/de39c42d1b3385a442eba5d0120acc8364f67602))
* **workflow:** 更新快取清理工作流程以修正過期快取的篩選邏輯 ([965947c](https://github.com/vancetang/demo/commit/965947cbb84636c3bcd7a304470e3d6b9a85fc2b))
* **workflow:** 更新快取清理工作流程以列出快取項目的創建和最後存取時間 ([df5d19a](https://github.com/vancetang/demo/commit/df5d19ae165fd52d7e0f0c6d0a6bc59ecc9da522))
* **workflow:** 更新快取清理工作流程以增加寫入權限 ([c596c83](https://github.com/vancetang/demo/commit/c596c8310b55a641be62e3fecf7391b6e6000c69))
* **workflow:** 更新快取清理工作流程以改善輸出訊息 ([14387c3](https://github.com/vancetang/demo/commit/14387c3ddc9b4392c251b22139f10b86ef24e2e6))
* **workflow:** 更新快取清理工作流程以正確設定未使用逾期天數 ([7576f5d](https://github.com/vancetang/demo/commit/7576f5d44938103d5e0b33ddf24a66189b3c5129))
* **workflow:** 更新快取清理工作流程以正確過濾創建和最後存取時間 ([653ae55](https://github.com/vancetang/demo/commit/653ae554c2265f16f6e2305006314d2fbfe8c8cb))
* **workflow:** 更新快取清理工作流程以縮短逾期天數 ([32274bd](https://github.com/vancetang/demo/commit/32274bd128e05a7b1e8e986372b5a5e7e614ba9b))
* **workflow:** 更新快取清理腳本中的變數名稱及輸出格式 ([3511828](https://github.com/vancetang/demo/commit/35118287f72bd99afa4655328744008103220e01))
* **workflow:** 更新快取清理腳本以列出過期快取的 ID ([e1750d7](https://github.com/vancetang/demo/commit/e1750d7c02bb93e724f15e449b6bfecec8e2c3ba))
* **workflow:** 更新快取清理腳本以正確過濾最後存取時間 ([fd9ac95](https://github.com/vancetang/demo/commit/fd9ac9538d7d28cf1e97069c07121cf99d7d95e3))
* **workflow:** 更新快取清理腳本以正確過濾過期快取 ([769e229](https://github.com/vancetang/demo/commit/769e229761d1faa9fdbe0ce4454ca3e0266b7ada))
* **workflow:** 更新快取清理腳本以設定逾期天數並列出舊快取 ([e13bacf](https://github.com/vancetang/demo/commit/e13bacf7086c5fae36bf88b53a42538583fc3b97))
* **workflow:** 更新快取清理腳本以顯示快取 ID 列表 ([3428866](https://github.com/vancetang/demo/commit/3428866d807ebb4a416ca94eea80a92f29f67174))
* **workflow:** 更新翻譯工作流程以使用預設語言列表 ([d921db4](https://github.com/vancetang/demo/commit/d921db4709f573e5c0584197e6d48f4faefc2b52))
* **workflow:** 更新翻譯工作流程以移除不必要的語言選項並修正檔案命名 ([ca2c623](https://github.com/vancetang/demo/commit/ca2c6239a5c95d3e61cffc2f7e8681f9c1b059bc))
* **workflow:** 更新翻譯工作流程以移除不必要的語言選項並修正檔案命名 ([31adae6](https://github.com/vancetang/demo/commit/31adae6f2a8dd067cc36a78f127d1a51171a9283))
* **workflow:** 移除快取清理腳本中的多餘 jq 選項 ([8bfe18b](https://github.com/vancetang/demo/commit/8bfe18b1e07d5ef920cb3ef9e35f4156c10d6a7b))
* **workflow:** 移除快取清理腳本中的多餘命令 ([9d92075](https://github.com/vancetang/demo/commit/9d9207547719bce92271e5a9e119a076c664b5fd))
* **workflow:** 移除快取清理腳本中的註解代碼 ([e15209e](https://github.com/vancetang/demo/commit/e15209ec031f6f4be66046c572edba25a441683b))
* **workflow:** 移除標籤創建過程中的多餘日誌輸出 ([900c70f](https://github.com/vancetang/demo/commit/900c70f2e708249f3b064385f1240c0822fd3086))
* **workflow:** 移除標籤創建過程中的多餘日誌輸出 ([b5899e8](https://github.com/vancetang/demo/commit/b5899e8db1529f5680cffab3b323aa473b122c4a))
* **workflow:** 移除測試步驟以簡化工作流程 ([54770cc](https://github.com/vancetang/demo/commit/54770cc4ee476322167153d1155428044e2d434c))
* **workflow:** 移除舊快取清理腳本中的不必要步驟 ([25a6985](https://github.com/vancetang/demo/commit/25a6985263ece2c3c55da120213810aa2e0961e9))
* **workflow:** 移除過期天數輸入的必填要求 ([20dfca2](https://github.com/vancetang/demo/commit/20dfca205ab26ccfda6919e56c6d9e9b61bac243))
* **workflow:** 調整工作流程觸發條件的格式 ([48e8a23](https://github.com/vancetang/demo/commit/48e8a23d2518f0c08a8fdd03b58fc93bfdd9876d))
* **workflow:** 調整翻譯工作流程以確保僅在 develop 分支上執行 ([1398536](https://github.com/vancetang/demo/commit/1398536410cfffdd865335d54f00e652b785b000))
* **workflow:** 調整翻譯工作流程以確保僅在 develop 分支上執行 ([dd15972](https://github.com/vancetang/demo/commit/dd15972dc0807b749186d5a61385dae0f8f6572d))
* **workflow:** 調整過期問題和 PR 的閒置天數設定為 0 ([adea48c](https://github.com/vancetang/demo/commit/adea48ce74ac4e7a51ea6c8235b2bc892cdae007))
* 因為不生效故取消跳過 PR 的配置以便於生成 Release 和標籤 ([1cee5a8](https://github.com/vancetang/demo/commit/1cee5a8551d87da199141039dad29c1c73f1b6b1))
* 因為不生效故取消跳過 PR 的配置以便於生成 Release 和標籤 ([165794b](https://github.com/vancetang/demo/commit/165794b85677ddd097e6d50e19859449bda86c35))
* **工作流程:** jq指令錯誤 ([185fe96](https://github.com/vancetang/demo/commit/185fe9622805d2a6cea95629b5eab2cd3ea11e3d))
* **工作流程:** 修正新增標籤工作流程中的 JSON 格式錯誤 ([f4a0f25](https://github.com/vancetang/demo/commit/f4a0f25d4bbbd31af1b52a06d614d722d502b54f))
* **工作流程:** 修正新增標籤工作流程中的描述格式錯誤 ([5913195](https://github.com/vancetang/demo/commit/5913195a68d58559aa650120f07bf1fd86a1dd10))
* **工作流程:** 修正標籤名稱的 URI 編碼步驟以確保正確處理 ([1dfd6cd](https://github.com/vancetang/demo/commit/1dfd6cd6ef171c74ce53f5cae47be4db577921c2))
* **工作流程:** 修正標籤名稱解碼時的引號處理 ([c76873a](https://github.com/vancetang/demo/commit/c76873a3761101dc33e5fcc0331d4e651f9b98d1))
* **工作流程:** 修正標籤名稱解碼過程中的 URI 編碼步驟 ([d52428f](https://github.com/vancetang/demo/commit/d52428f399523e7d26446d421112d4bce1214b6d))
* **工作流程:** 修正標籤名稱解碼過程中的變數處理 ([edc6dac](https://github.com/vancetang/demo/commit/edc6daca552b39305fec3ccf8dc323608c452b1a))
* **工作流程:** 合併標籤名稱的 URI 編碼步驟以簡化程式碼 ([5bb8050](https://github.com/vancetang/demo/commit/5bb80508234e56d410e3ebb6a5ea8d12c040cc24))
* **工作流程:** 增加base64指令 ([313ffcb](https://github.com/vancetang/demo/commit/313ffcbe3a4b9f80292efc87f40740816836acf2))
* **工作流程:** 簡化CodeQL分析語言設定 ([baf7ee8](https://github.com/vancetang/demo/commit/baf7ee8f2bb1dcc19fd62184affecb3ac5ea03b6))
* **拉取請求模板:** 修正其他選項的標籤格式 ([3a3b1df](https://github.com/vancetang/demo/commit/3a3b1df391b6422225f04e48fac6bf5fe5f32168))
* 調整日誌輸出格式以增加可讀性 ([31ba9d0](https://github.com/vancetang/demo/commit/31ba9d02dec964a42eae40319ac8fe46dc4f7aca))
* 調整日誌輸出格式以增加可讀性 ([fd1a1bb](https://github.com/vancetang/demo/commit/fd1a1bb3dae6f0f777c705e73ad3c972fd930efa))

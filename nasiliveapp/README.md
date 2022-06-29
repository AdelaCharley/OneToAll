1,基础环境
  minSdkVersion 21
  targetSdkVersion 29
  versionCode 28
  compileSdkVersion 29
  buildToolsVersion "29.0.0"
  compileOptions {
  targetCompatibility = "8"
  sourceCompatibility = "8"
  }
  android stuido 4.2
  jdk 1.8


2,基础配置
2.1 /base/MyApp
ugcLicenceUrl,ugcKey 腾讯短视频KEY

2.2 AndroidManifest
WXPayEntryActivity 微信支付设置

2.3 build.gradle
MobSdk 各种分享参数
licenceURL,licenceKey 直播相关参数

2.4 /net/APIService
baseUrl 总API服务地址

2.5 /model/entity/UserConfig/LoginConfig
im_sdkappid 腾讯IMID
qcloud_appid 腾讯云上传APPID
wx_appid 微信ID
socket_server socket服务器
tisdk_key 美颜SDK KEY
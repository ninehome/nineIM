# README



## app 配置

配置 `com.tiocloud.chat.constant.TioConfig` 中的 `BASE_URL` 和 `HAND_SHAKE_KEY`



## jpush 配置

1、配置 jpush module 下的 `build.gradle` 文件

```java
android {
    defaultConfig {
        manifestPlaceholders = [
                JPUSH_APPKEY : "xxx",
                JPUSH_CHANNEL: "xxx",
                XIAOMI_APPID:"MI-xxx",
                XIAOMI_APPKEY:"MI-xxx",
                OPPO_APPKEY:"OP-xxx",
                OPPO_APPID:"OP-xxx",
                OPPO_APPSECRET:"OP-xxx",
                VIVO_APPKEY:"xxx",
                VIVO_APPID:"xxx"
        ]
		}
}
```



2、将华为推送生成的 `agconnect-services.json` 文件放在 app module 根目录下。



## 易支付钱包配置

> 包含的module
>
> - `lib-wallet`
>
> - `pay-ease`  
>
> - `common-silent` 



1、替换 `common-silent` 中的生物识别证书 "SenseID_Liveness_Silent.lic"（新证书命名，需要与老证书保持一致）

2、替换 `pay-ease` 中的商户号证书 "894420924.cer"

3、修改 `pay-ease` 中的商户ID  `com.tiocloud.payease.PayEase # MERCHANT_ID`

4、确保 app module 的 application 主题为 NoActionBar。否则易支付的 “拍照获取银行卡页面” 会崩溃。(此为易支付 v1.1.0 的 bug )



## 谭聊社交配置

> 包含的module
>
> - `tio-social`
>
> - `lib-social`



1、在 `tio-social` -> `AndroidManifest.xml` 中将  `{qq_app_id}` 整体替换成自己的 qq_app_id

```java
<activity
    android:name="com.tencent.tauth.AuthActivity"
    android:launchMode="singleTask"
    android:noHistory="true">
    <intent-filter>
        <action android:name="android.intent.action.VIEW" />

        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />

        <data android:scheme="tencent{qq_app_id}" />
    </intent-filter>
</activity>
<activity
    android:name="com.tencent.connect.common.AssistActivity"
    android:configChanges="orientation|keyboardHidden|screenSize"
    android:theme="@android:style/Theme.Translucent.NoTitleBar" />
```



2、在 `com.tiocloud.social.TioSocial` 中

- 将  `{qq_app_id}` 整体替换成自己的 qq_app_id

- 将  `{wx_app_id}` 整体替换成自己的 wx_app_id
- 将  `{wx_app_secret}` 整体替换成自己的 wx_app_secret

```java
SocialHelper socialHelper = new SocialHelper.Builder()
        .setQqAppId({qq_app_id})
        .setWxAppId({wx_app_id})
        .setWxAppSecret({wx_app_secret})
        .build();
```



3、将 `com.tiocloud.chat.wxapi.WXEntryActivity` 移动至 `{yourPackageName}.wxapi.WXEntryActivity`
-dontwarn com.blankj.utilcode.**

-keepclassmembers class * {
    @com.blankj.utilcode.util.BusUtils$Bus <methods>;
}

-keep public class * extends com.blankj.utilcode.util.ApiUtils$BaseApi
-keep,allowobfuscation @interface com.blankj.utilcode.util.ApiUtils$Api
-keep @com.blankj.utilcode.util.ApiUtils$Api class *

-keepattributes *Annotation*

-keep class com.chad.library.adapter.** {*;}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {<init>(...);}

-keep public class * extends com.watayouxiang.androidutils.recyclerview.BaseFetchLoadAdapter {*;}
-keep public class * extends com.watayouxiang.androidutils.recyclerview.RecyclerViewHolder {*;}

-keep class com.huantansheng.easyphotos.models.** { *; }

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

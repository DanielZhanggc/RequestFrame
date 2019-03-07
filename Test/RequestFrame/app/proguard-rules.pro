#-------------------------------------------基本不用动区域--------------------------------------------
#---------------------------------基本指令区----------------------------------
-optimizationpasses 5                                                       # 压缩级别[Default：5]
-dontusemixedcaseclassnames                                                # Not混合类名
-dontskipnonpubliclibraryclasses                                           # Not忽略非公共的库类
-dontskipnonpubliclibraryclassmembers                                     # Not忽略包可见的库类的成员
-dontpreverify                                                              # Not预校验
-verbose                                                                     # 映射文件
-printmapping proguardMapping.txt                                            #指定映射文件
-optimizations !code/simplification/cast,!field/*,!class/merging/*           # 算法[from google]
-keepattributes *Annotation*,InnerClasses                                    #Keep Annotation[JSON]
-keepattributes Signature                                                    #Keep 泛型[JSON]
-keepattributes SourceFile,LineNumberTable                                   #Keep Exception行号
#----------------------------------------------------------------------------
-ignorewarnings                                                              # Not警告
#---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity                             #Keep Activity[SYS]
-keep public class * extends android.app.Application                          #Keep Application[SYS]
-keep public class * extends android.app.Service                              #Keep Service[SYS]
-keep public class * extends android.content.BroadcastReceiver                #Keep BroadcastReceiver[SYS]
-keep public class * extends android.content.ContentProvider                  #Keep ContentProvider[SYS]
-keep public class * extends android.app.backup.BackupAgentHelper             #Keep BackupAgentHelper[SYS]
-keep public class * extends android.preference.Preference                    #Keep Preference[SYS]
-keep public class * extends android.view.View                                #Keep View[SYS]
-keep public class com.android.vending.licensing.ILicensingService            #Keep ILicensingService[SYS]
-keep class android.support.** {*;}                                           #Keep support[jar][SYS]

                                                                               #Keep V4
-keep class android.support.v4.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment

                                                                               #Keep Native
-keepclasseswithmembernames class * {
    native <methods>;
}

                                                                               #Keep Activity[Code]
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}

                                                                               #Keep enum[SYS]
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

                                                                               #Keep View[Code]
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

                                                                               #Keep AttributeSet[SYS]
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

                                                                               #Keep Parcelable[SYS]
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

                                                                               #Keep Serializable[SYS]
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

                                                                               #Keep R[SYS]
-keep class **.R$* {
    public static <fields>;
}

                                                                               #Keep On*Event[OnBusEvent]
-keepclassmembers class * {
    void *(**On*Event);
}

                                                                               #Keep Webview[About]
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}

-keep class com.yitong.requestframe.bean.**{*;}

#---------------------------------第三方库---------------------------------
#okhttp3
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-keep class okhttp3.** { *;}
-keep class okio.** { *;}
-dontwarn sun.security.**
-keep class sun.security.** { *;}
-dontwarn okio.**
-dontwarn okhttp3.**

#retrofit2
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-dontwarn org.robovm.**
-keep class org.robovm.** { *; }

# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
-dontnote rx.internal.util.PlatformDependent

# Retrofit, OkHttp, Gson
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**
-dontwarn rx.**
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Rxjava-promises
-keep class com.darylteo.rx.** { *; }
-dontwarn com.darylteo.rx.**

# RxJava
-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}

#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# RxLifeCycle2
-keep class com.trello.rxlifecycle2.** { *; }
-keep interface com.trello.rxlifecycle2.** { *; }
-dontwarn com.trello.rxlifecycle2.**

-keep class com.github.mikephil.charting.** { *; }
-dontwarn com.github.mikephil.charting.data.realm.**

#Apache&HttpClient
-keep class android.net.**{*;}
-dontwarn android.net.**
-keep class com.android.internal.http.multipart.**{*;}
-dontwarn com.android.internal.http.multipart.**
-keep class org.apache.**{*;}
-dontwarn org.apache.**

#commons-lang
-keep class org.apache.commons.lang.**{*;}
-dontwarn org.apache.commons.lang.**


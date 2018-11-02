# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class androidx.core.app.CoreComponentFactory { *; }

# --------------------------------------------------------------------
# Dagger
# --------------------------------------------------------------------
    -dontwarn dagger.internal.codegen.**
    -keepclassmembers,allowobfuscation class * {
        @javax.inject.* *;
        @dagger.* *;
        <init>();
    }
    -keep class dagger.* { *; }
    -keep class javax.inject.* { *; }
    -keep class * extends dagger.internal.Binding
    -keep class * extends dagger.internal.ModuleAdapter
    -keep class * extends dagger.internal.StaticInjection

# --------------------------------------------------------------------
# rxjava
# --------------------------------------------------------------------

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
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    long producerNode;
    long consumerNode;
}

# --------------------------------------------------------------------
# okhttp3
# --------------------------------------------------------------------
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-keepclassmembers class * implements javax.net.ssl.SSLSocketFactory {
    private final javax.net.ssl.SSLSocketFactory delegate;
}

# --------------------------------------------------------------------
# retrofit2
# --------------------------------------------------------------------
-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions
-keepattributes EnclosingMethod
-keep class sun.misc.Unsafe { *; }
-dontwarn sun.misc.Unsafe

# --------------------------------------------------------------------
# Picasso
# --------------------------------------------------------------------
-dontwarn com.squareup.okhttp.**

# --------------------------------------------------------------------
# Kotlin
# --------------------------------------------------------------------
-keep class kotlin.internal.annotations.AvoidUninitializedObjectCopyingCheck { *; }
-dontwarn kotlin.internal.annotations.AvoidUninitializedObjectCopyingCheck

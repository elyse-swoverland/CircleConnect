object Versions {

    // Build Config
    const val minSdk = 21
    const val compileSdk = 28
    const val targetSdk = 28

    // App Version
    const val appVersionCode = 1
    const val appVersionName = "1.0.0"

    // Android Gradle Plugin
    const val gradlePlugin = "3.3.0-beta01"

    // Kotlin
    const val kotlin = "1.2.71"

    // Support
    const val androidx = "1.0.0"
    const val constraintLayout = "1.1.3"

    // CoreKtx
    const val coreKtx = "1.0.0"

    // Navigation
    const val navigation = "1.0.0-alpha07"

    // Maps
    const val maps = "16.0.0"

    // Dagger
    const val dagger = "2.16"

    // Javax
    const val javax = "1.2"

    // Reactivex
    const val rxAndroid = "1.2.1"
    const val rxJava = "1.1.6"

    // OkHttp
    const val okHttp = "3.10.0"

    // Retrofit
    const val retrofit = "2.3.0"
    const val gsonConverter = "2.3.0"
    const val jacksonConverter = "2.0.2"
    const val rxJavaAdapter = "2.0.2"

    // Picasso
    const val picasso = "2.5.2"

    // Testing
    const val jUnit = "4.12"
    const val testRunner = "1.1.0"
    const val espresso = "3.1.0"
}

object Deps {
    /* PROJECT LEVEL */

    // Gradle Plugin
    const val gradlePlugin = "com.android.tools.build:gradle:${Versions.gradlePlugin}"

    // Kotlin
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val kotlinAndroidExtensions = "org.jetbrains.kotlin:kotlin-android-extensions:${Versions.kotlin}"

    /* MODULE LEVEL */

    // Kotlin
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    // Support
    const val support = "androidx.appcompat:appcompat:${Versions.androidx}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    // Core Ktx
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"

    // Design
    const val design = "com.google.android.material:material:${Versions.androidx}"

    // Navigation
    const val navFragment = "android.arch.navigation:navigation-fragment:${Versions.navigation}"
    const val navUi = "android.arch.navigation:navigation-ui:${Versions.navigation}"
    const val navFragmentKtx = "android.arch.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navUiKtx = "android.arch.navigation:navigation-ui-ktx:${Versions.navigation}"

    // Maps
    const val maps = "com.google.android.gms:play-services-maps:${Versions.maps}"
    const val location = "com.google.android.gms:play-services-location:${Versions.maps}"

    // Dagger
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"

    // Javax
    const val javaxAnnotation = "javax.annotation:javax.annotation-api:${Versions.javax}"

    // Reactivex
    const val rxAndroid = "io.reactivex:rxandroid:${Versions.rxAndroid}"
    const val rxJava = "io.reactivex:rxjava:${Versions.rxJava}"

    //OkHttp
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"

    // Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.gsonConverter}"
    const val jacksonConverter = "com.squareup.retrofit2:converter-jackson:${Versions.jacksonConverter}"
    const val rxJavaAdapter = "com.squareup.retrofit2:adapter-rxjava:${Versions.rxJavaAdapter}"

    // Picasso
    const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"

    // Testing
    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val testRunner = "com.android.support.test:runner:${Versions.testRunner}"
    const val espresso = "com.android.support.test.espresso:espresso-core:${Versions.espresso}"
}
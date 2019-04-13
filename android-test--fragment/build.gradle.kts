plugins {
    id("com.android.library")
    kotlin("android")
    id("org.jetbrains.kotlin.android.extensions")
}

android {
    compileSdkVersion(Project.Android.compileSdkVersion)
    defaultConfig {
        minSdkVersion(Project.Android.minSdkVersion)
        targetSdkVersion(Project.Android.targetSdkVersion)
        testInstrumentationRunner = Project.Android.testInstrumentationRunner
    }
}

androidExtensions {
    isExperimental = true
}

dependencies {
    implementation(project(":android-test-base"))
}
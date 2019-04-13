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
    api(Deps.Kotlin.StdLib.jdk)
    api(Deps.Android.X.appCompat)
    api(Deps.Android.X.espresso)
    api(Deps.Android.X.testRunner)
    api(Deps.Android.X.testRules)
    api(Deps.Android.X.junit)
    api(project(":android"))
}
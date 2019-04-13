plugins {
    id("com.android.application")
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
    implementation(Deps.Kotlin.StdLib.jdk)
    implementation(Deps.Android.X.appCompat)
    implementation(Deps.Android.X.constraintLayout)
    implementation(Deps.Android.X.material)
    implementation(Deps.Android.X.lifecycleExtensions)
    implementation(Deps.Android.X.lifecycleViewModel)
    testImplementation(Deps.junit)
    androidTestImplementation(Deps.Android.X.espresso)
    androidTestImplementation(Deps.Android.X.testRunner)
    androidTestImplementation(Deps.Android.X.junit)
    implementation(project(":android"))
}
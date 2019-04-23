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

repositories {
    maven { url = uri("https://dl.bintray.com/sellmair/sellmair") }
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
    androidTestImplementation(Deps.Android.X.testRules)
    androidTestImplementation(Deps.Android.X.testRunner)
    androidTestImplementation(Deps.Android.X.junit)
    //implementation(project(":kompass-android"))
    implementation("io.sellmair:kompass-android:0.2.0-pre-alpha.1")
    //implementation("io.sellmair:kompass-core-jvm:0.2.0-pre-alpha.0")

}
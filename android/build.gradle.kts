plugins {
    id("com.android.library")
    kotlin("android")
    id("org.jetbrains.kotlin.android.extensions")
}

android {
    compileSdkVersion(Project.Android.compileSdkVersion)
    defaultConfig {
        targetSdkVersion(Project.Android.targetSdkVersion)
        minSdkVersion(Project.Android.minSdkVersion)
    }
}

androidExtensions {
    isExperimental = true
}

dependencies {
    /* Kotlin */
    implementation(Deps.Kotlin.StdLib.jdk)

    /* Android X */
    implementation(Deps.Android.X.lifecycleExtensions)
    implementation(Deps.Android.X.lifecycleViewModel)
    implementation(Deps.Android.X.fragment)


    api(project(":core"))

}


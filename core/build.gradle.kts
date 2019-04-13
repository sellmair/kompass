@file:Suppress("UNUSED_VARIABLE")

plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

kotlin {
    macosX64("macos")
    android("android")
    jvm("jvm")

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Deps.Kotlin.StdLib.common)
                api(Deps.Kotlin.Coroutines.common)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(Deps.Kotlin.Test.common)
                implementation(Deps.Kotlin.Test.annotations)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(Deps.Kotlin.StdLib.jdk)
            }
        }

        val jvmTest by getting {
            dependsOn(commonTest)
            dependencies {
                implementation(Deps.Kotlin.Test.junit)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(Deps.Kotlin.StdLib.jdk)
                api(Deps.Kotlin.Coroutines.android)
            }
        }

        val androidTest by getting {
            dependsOn(commonTest)
            dependencies {
                implementation(Deps.Kotlin.Test.junit)
            }
        }
    }


}


android {
    compileSdkVersion(Project.Android.compileSdkVersion)
    defaultConfig {
        targetSdkVersion(Project.Android.targetSdkVersion)
        minSdkVersion(Project.Android.minSdkVersion)
    }
}


@file:Suppress("UNUSED_VARIABLE")


plugins {
    kotlin("multiplatform")
    `maven-publish`
}


// TODO work around for https://youtrack.jetbrains.com/issue/KT-27170
configurations.create("compileClasspath")

group = Library.group
version = Library.version


kotlin {
    macosX64("macos")
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
    }
}


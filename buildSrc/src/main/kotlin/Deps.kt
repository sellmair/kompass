object Versions {
    const val kotlin = "1.3.50"
    const val androidGradlePlugin = "3.5.0"
    const val lifecycleExtensions = "2.0.0"
    const val lifecycleViewModel = "2.0.0"
    const val fragment = "1.1.0-rc04"
    const val appCompat = "1.0.2"
    const val junit = "4.12"
    const val androidTestRunner = "1.1.0"
    const val androidTestRules = "1.1.0"
    const val espresso = "3.1.0"
    const val bintrayGradlePlugin = "1.8.4"
}


object Deps {

    object Plugins {
        const val android = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val kotlinSerialization = "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}"
        const val bintray = "com.jfrog.bintray.gradle:gradle-bintray-plugin:${Versions.bintrayGradlePlugin}"
    }

    object Kotlin {

        object StdLib {
            const val common = "org.jetbrains.kotlin:kotlin-stdlib-common:${Versions.kotlin}"
            const val jdk = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
        }

        object Test {
            const val common = "org.jetbrains.kotlin:kotlin-test-common:${Versions.kotlin}"
            const val annotations = "org.jetbrains.kotlin:kotlin-test-annotations-common:${Versions.kotlin}"
            const val junit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
        }

    }

    object Android {

        object X {
            const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExtensions}"
            const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleViewModel}"
            const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
            const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
            const val testRunner = "androidx.test:runner:${Versions.androidTestRunner}"
            const val testRules = "androidx.test:rules:${Versions.androidTestRules}"
            const val junit = "androidx.test.ext:junit:${Versions.androidTestRunner}"
            const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
            const val material = "com.google.android.material:material:1.0.0-rc01"
            const val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.2"
        }

    }

    const val junit = "junit:junit:${Versions.junit}"

}

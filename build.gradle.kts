group = "io.sellmair"
version = "2.0-SNAPSHOT"

buildscript {

    repositories {
        mavenCentral()
        google()
        jcenter()
    }

    dependencies {
        classpath(Deps.Plugins.android)
        classpath(Deps.Plugins.kotlin)
        classpath(Deps.Plugins.kotlinSerialization)
        classpath(Deps.Plugins.bintray)
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()
        maven { url = uri("https://kotlin.bintray.com/kotlinx") }
        jcenter()
    }
}



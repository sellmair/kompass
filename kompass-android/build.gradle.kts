plugins {
    id("com.android.library")
    kotlin("android")
    id("org.jetbrains.kotlin.android.extensions")
    `maven-publish`
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


    api(project(":kompass-core"))

}


//region Publishing

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets["main"].java.srcDirs)
}

publishing {
    publications {
        create<MavenPublication>("aar") {
            groupId = Library.group
            version = Library.version
            artifactId = Library.Android.name

            artifact(file("$buildDir/outputs/aar/kompass-android-release.aar"))
            artifact(sourcesJar.get())

            pom.withXml {
                asNode().appendNode("dependencies").apply {
                    val dependencyNode = appendNode("dependency")
                    dependencyNode.appendNode("groupId", Library.group)
                    dependencyNode.appendNode("artifactId", Library.Core.Jvm.name)
                    dependencyNode.appendNode("version", Library.version)
                }
            }
        }
    }
}


//endregion
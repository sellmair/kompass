plugins {
    id("com.android.library")
    kotlin("android")
    id("org.jetbrains.kotlin.android.extensions")
    id("com.jfrog.bintray")
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
    api(Deps.Android.X.lifecycleExtensions)
    api(Deps.Android.X.fragment)


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
                val node = asNode()

                val dependenciesNode = node.appendNode("dependencies")

                dependenciesNode.apply {
                    val coreNode = appendNode("dependency")
                    coreNode.appendNode("groupId", Library.group)
                    coreNode.appendNode("artifactId", Library.Core.name)
                    coreNode.appendNode("version", Library.version)
                }


                configurations.getByName("api").dependencies.toList()
                    .filter { dependency -> dependency !is ProjectDependency }
                    .forEach { dependency ->
                        dependenciesNode.appendNode("dependency").apply {
                            appendNode("groupId", dependency.group)
                            appendNode("artifactId", dependency.name)
                            appendNode("version", dependency.version)
                        }
                    }
            }
        }
    }
}


bintray {
    user = project.properties.getOrDefault("bintray_user", "stub").toString()
    key = project.properties.getOrDefault("bintray_apikey", "stub").toString()
    override = false
    setPublications("aar")
    with(pkg) {
        name = Library.Android.name
        repo = Bintray.repository
        desc = Library.Android.Meta.description
        websiteUrl = Library.Android.Meta.websiteUrl
        vcsUrl = Library.Android.Meta.gitUrl
        setLicenses(*Bintray.allLicenses)
        publish = true
        with(version) {
            name = Library.version
            desc = Library.version
            with(gpg) {
                sign = false
                passphrase == project.properties.getOrDefault(
                    "bintray_gpg_password",
                    "stub"
                ).toString()
            }
        }
    }
}

val bintrayUpload: Task by tasks.getting
bintrayUpload.dependsOn("assembleRelease")


//endregion
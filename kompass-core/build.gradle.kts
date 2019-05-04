@file:Suppress("UNUSED_VARIABLE")

import com.jfrog.bintray.gradle.tasks.BintrayUploadTask
import org.gradle.api.publish.maven.internal.artifact.FileBasedMavenArtifact


plugins {
    kotlin("multiplatform")
    `maven-publish`
    id("com.jfrog.bintray")
}

group = Library.group
version = Library.version


kotlin {
    macosX64("macos")
    jvm("jvm")
    iosArm64("iosArm64")
    iosX64("iosX64")

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Deps.Kotlin.StdLib.common)
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

        val macosMain by getting {

        }

        val macosTest by getting {

        }

        val iosX64Main by getting {
            dependsOn(macosMain)
        }

        val iosX64Test by getting {
            dependsOn(macosTest)
        }

        val iosArm64Main by getting {
            dependsOn(macosMain)
        }

        val iosArm64Test by getting {
            dependsOn(macosTest)
        }

    }
}


//region Workarounds

/* https://youtrack.jetbrains.com/issue/KT-27170 */
configurations.create("compileClasspath")

//endregion


//region Publishing


fun publications(): List<String> {
    return project.publishing.publications.toList()
        .filter { !it.name.contains("test") }
        .map { publication -> publication.name }
}


bintray {
    user = project.properties.getOrDefault("bintray_user", "stub").toString()
    key = project.properties.getOrDefault("bintray_apikey", "stub").toString()
    override = true
    setPublications(*publications().toTypedArray())
    with(pkg) {
        name = Library.Core.name
        repo = Bintray.repository
        desc = Library.Core.Meta.description
        websiteUrl = Library.Core.Meta.websiteUrl
        vcsUrl = Library.Core.Meta.gitUrl
        setLicenses(*Bintray.allLicenses)
        publish = true
        with(version) {
            name = Library.version
            desc = Library.version
        }
    }
}


val bintrayUpload: Task by tasks.getting
bintrayUpload.dependsOn("publishToMavenLocal")


/* https://github.com/bintray/gradle-bintray-plugin/issues/229 */
tasks.withType<BintrayUploadTask> {
    doFirst {
        publishing.publications
            .filterIsInstance<MavenPublication>()
            .forEach { publication ->
                val moduleFile = buildDir.resolve("publications/${publication.name}/module.json")
                if (moduleFile.exists()) {
                    publication.artifact(object : FileBasedMavenArtifact(moduleFile) {
                        override fun getDefaultExtension() = "module"
                    })
                }
            }
    }
}

//endregion


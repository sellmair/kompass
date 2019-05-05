object Library {
    const val group = "io.sellmair"
    const val version = "0.2.0-alpha.1"

    object Core {
        const val name = "kompass-core"

        object Meta {
            const val description = "Kompass Core Module"
            const val gitUrl = "https://github.com/sellmair/kompass"
            const val websiteUrl = "https://github.com/sellmair/kompass"
        }
    }


    object Android {
        const val name = "kompass-android"


        object Meta {
            const val description = "Kompass Android Module"
            const val gitUrl = "https://github.com/sellmair/kompass"
            const val websiteUrl = "https://github.com/sellmair/kompass"
        }
    }

}


object Bintray {
    const val repository = "sellmair"
    val allLicenses = arrayOf("MIT")
}
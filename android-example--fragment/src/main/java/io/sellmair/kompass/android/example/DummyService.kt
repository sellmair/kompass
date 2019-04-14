package io.sellmair.kompass.android.example

/**
 * Created by sebastiansellmair on 27.01.18.
 */
class DummyService {
    companion object {
        var isLoggedIn: Boolean = false


        val contacts = listOf(
            Contact(
                name = "Julian B.",
                phone = "-",
                email = "julian@tikkr.app",
                nickname = "Julian"
            ),
            Contact(
                name = "Stefan K.",
                phone = "0176/43404485",
                email = "stefan@fasteaglefactory.com",
                nickname = "Steffen"
            ),
            Contact(
                name = "Malte B.",
                phone = "08442/90909090",
                email = "malte@fasteaglefactory.com",
                nickname = "Malthe"
            ),
            Contact(
                name = "Klaus N.",
                phone = "0800/32-16-8",
                email = "klaus@rustserver.io",
                nickname = "Klaus"
            ),
            Contact(
                name = "G. Nasir",
                phone = "00491500-not-his-own",
                email = "nasir@slackisthereforareason.com",
                nickname = "Nasir"
            ),
            Contact(
                name = "Mathias Q.",
                phone = "0176/46779438",
                email = "mathias@pleasedontleave.io",
                nickname = "Mathias"
            ),
            Contact(
                name = "Balazs T.",
                phone = "0152/837782458",
                email = "balazs@",
                nickname = "Balazs"
            ),
            Contact(
                name = "Niko T.",
                phone = "0800/0800",
                email = "niko@",
                nickname = "Niko"
            ),
            Contact(
                name = "Paul K.",
                phone = "0800/dj-paul-power",
                email = "super@paul.io",
                nickname = "Paul"
            )
        )

        /*
        val contacts: List<Contact> = mutableListOf<Contact>()
            .also { list ->
                list.add(
                    Contact(
                        "Sebastian Sellmair",
                        "0176/00003344",
                        "sebastiansellmair@gmail.com",
                        "Sebi"
                    )
                )

                list.add(
                    Contact(
                        "Malte B.",
                        "0176/13003344",
                        "malte@...",
                        "Malte"
                    )
                )

                list.add(
                    Contact(
                        "Stefan K.",
                        "0176/02403344",
                        "stephan@...",
                        "Stephan"
                    )
                )

                list.add(
                    Contact(
                        "Julian B.",
                        "0176/12303344",
                        "julian@...",
                        "Julian"
                    )
                )

                list.add(
                    Contact(
                        "Klaus N",
                        "0176/43505560",
                        "klaus@...",
                        "Klaus"
                    )
                )*
            }*/
    }
}
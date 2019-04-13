package io.sellmair.kompass.android.example

/**
 * Created by sebastiansellmair on 27.01.18.
 */
class DummyService {
    companion object {
        var isLoggedIn: Boolean = false
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
                        "Joan D.",
                        "0176/43505560",
                        "joan@...", "" +
                                "Joan"
                    )
                )
            }
    }
}
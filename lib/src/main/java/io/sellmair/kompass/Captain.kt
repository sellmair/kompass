package io.sellmair.kompass

import android.content.Intent
import android.support.v4.app.Fragment

internal sealed class Captain(val destination: Any)
internal class IntentCaptain(val intent: Intent, destination: Any) : Captain(destination)
internal class FragmentCaptain(val fragment: Fragment, destination: Any) : Captain(destination)
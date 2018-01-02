package io.sellmair.kompass

import android.content.Intent
import android.support.v4.app.Fragment

internal sealed class Captain
internal class IntentCaptain(val intent: Intent) : Captain()
internal class FragmentCaptain(val fragment: Fragment) : Captain()
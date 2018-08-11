package io.sellmair.kompass.app

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class FragmentOne : Fragment() {

    companion object {
        const val TEXT = "ONE"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return TextView(context).apply {
            text = TEXT
        }
    }
}
package io.sellmair.kompass.app

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

abstract class TargetFragment : Fragment(), TargetIdentifiable {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_target, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val root: View = view.findViewById(R.id.target_root)
        val id: TextView = view.findViewById(R.id.target_id)
        val key: TextView = view.findViewById(R.id.target_key)

        root.setBackgroundColor(this.id.color)
        id.text = this.id.name
        key.text = this.arguments?.getString("key")
    }
}
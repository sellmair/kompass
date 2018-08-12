package io.sellmair.kompass.app

import android.graphics.Color
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

        root.setBackgroundColor(color)
        id.text = this.id.name
        key.text = this.arguments?.getString("key")
    }

    protected val color by lazy {
        val targets = Target.values().size
        val hueMax = 360f
        val hue = id.ordinal.toFloat() / targets.toFloat() * hueMax
        val saturation = 0.8f
        val value = 0.8f

        val hsv = floatArrayOf(hue, saturation, value)
        Color.HSVToColor(hsv)
    }
}
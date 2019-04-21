package io.sellmair.kompass.android.test.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import io.sellmair.kompass.android.fragment.FragmentRouter
import io.sellmair.kompass.android.fragment.KompassFragment
import io.sellmair.kompass.android.test.FragmentHostActivity
import java.util.*
import kotlin.reflect.KClass

abstract class BaseFragment : Fragment(), KompassFragment {

    override val router: FragmentRouter<*>
        get() = FragmentHostActivity.router

    abstract val route: BaseRoute


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        view.findViewById<TextView>(R.id.routeText).apply {
            text = route.text
        }

        view.findViewById<TextView>(R.id.fragmentText).apply {
            text = text()
        }

        view.background = ColorDrawable(backgroundColor())
    }

    fun text() = this::class.text().orEmpty()

    private fun backgroundColor(): Int {
        val color = Objects.hash(route.text, this::class.text())
        return Color.rgb(Color.red(color), Color.green(color), Color.blue(color))
    }
}

fun KClass<out BaseFragment>.text() = this.java.simpleName
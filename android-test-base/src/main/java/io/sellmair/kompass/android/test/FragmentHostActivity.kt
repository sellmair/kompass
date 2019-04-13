package io.sellmair.kompass.android.test

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import io.sellmair.kompass.android.fragment.FragmentRouter
import io.sellmair.kompass.android.fragment.KompassFragmentActivity
import io.sellmair.kompass.android.test.base.R

class FragmentHostActivity : AppCompatActivity(), KompassFragmentActivity {

    private var _router: FragmentRouter<FragmentHostRoute>? = null

    private val handler = Handler(Looper.getMainLooper())

    var router: FragmentRouter<FragmentHostRoute>
        set(value) {
            _router = value
            handler.post { value.setup(R.id.root) }
        }
        get() {
            return _router ?: throw IllegalStateException("router not set")
        }


    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_host)
    }

}
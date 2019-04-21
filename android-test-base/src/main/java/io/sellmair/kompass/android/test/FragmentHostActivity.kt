package io.sellmair.kompass.android.test

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import io.sellmair.kompass.android.fragment.FragmentRouter
import io.sellmair.kompass.android.fragment.KompassFragmentActivity
import io.sellmair.kompass.android.test.base.R

class FragmentHostActivity : AppCompatActivity(), KompassFragmentActivity {


    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_host)
        router.setup(savedInstanceState, R.id.root)
    }


    companion object {
        private var routerField: FragmentRouter<FragmentHostRoute>? = null

        var router: FragmentRouter<FragmentHostRoute>
            set(value) {
                routerField = value
            }
            get() {
                return routerField ?: throw IllegalStateException("router not set")
            }
    }
}
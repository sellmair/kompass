package io.sellmair.kompass.android.test.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.sellmair.kompass.android.fragment.FragmentRouter
import io.sellmair.kompass.android.fragment.KompassFragment
import io.sellmair.kompass.android.test.FragmentHostActivity

class SubRouteHostFragment : Fragment(), KompassFragment {

    override val router: FragmentRouter<*> = FragmentHostActivity.router
    private var subRouter: FragmentRouter<*> = FragmentHostActivity.subRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subRouter.setup(savedInstanceState, R.id.container)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fargment_sub_route_host, container, false)
    }
}

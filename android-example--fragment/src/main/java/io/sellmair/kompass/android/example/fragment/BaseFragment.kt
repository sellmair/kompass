package io.sellmair.kompass.android.example.fragment

import androidx.fragment.app.Fragment
import io.sellmair.kompass.android.example.Dependencies
import io.sellmair.kompass.android.fragment.FragmentRouter
import io.sellmair.kompass.android.fragment.KompassFragment

open class BaseFragment : Fragment(), KompassFragment {
    override val router: FragmentRouter<*> = Dependencies.router
}
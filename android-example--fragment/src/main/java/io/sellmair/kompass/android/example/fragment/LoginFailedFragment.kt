package io.sellmair.kompass.android.example.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.sellmair.kompass.android.example.LoginFailedRoute
import io.sellmair.kompass.android.example.R
import io.sellmair.kompass.android.route
import kotlinx.android.synthetic.main.fragment_login_failed.*


/**
 * Created by sebastiansellmair on 07.02.18.
 */
class LoginFailedFragment : BaseFragment() {

    val route: LoginFailedRoute by route()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_login_failed, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username.text = route.username
    }
}
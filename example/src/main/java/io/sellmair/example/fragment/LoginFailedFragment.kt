package io.sellmair.example.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.sellmair.example.R
import io.sellmair.kompass.asLoginFailedDestination
import kotlinx.android.synthetic.main.fragment_login_failed.*

/**
 * Created by sebastiansellmair on 07.02.18.
 */
class LoginFailedFragment : Fragment() {

    val destination by lazy {
        arguments?.asLoginFailedDestination() ?: throw IllegalArgumentException()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login_failed, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username.text = destination.username
    }
}
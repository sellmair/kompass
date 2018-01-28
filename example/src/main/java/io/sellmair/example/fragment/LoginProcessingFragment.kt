package io.sellmair.example.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.sellmair.example.R
import io.sellmair.example.destination.LoginProcessingDestination
import io.sellmair.example.viewmodel.LoginProcessingViewModel
import io.sellmair.kompass.tryAsLoginProcessingDestination

/**
 * Created by sebastiansellmair on 28.01.18.
 */
class LoginProcessingFragment : Fragment() {
    private lateinit var destination: LoginProcessingDestination
    private lateinit var viewModel: LoginProcessingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        destination = arguments?.tryAsLoginProcessingDestination()
                ?: throw IllegalArgumentException()

        viewModel = ViewModelProviders.of(this)[LoginProcessingViewModel::class.java]
        viewModel.setCredentials(destination.email, destination.password)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login_processing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name: TextView = view.findViewById(R.id.name)
        name.text = destination.email
    }

    override fun onPause() {
        super.onPause()
        viewModel.stop()
    }
}
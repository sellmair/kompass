package io.sellmair.kompass.android.example.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import io.sellmair.kompass.android.example.LoginProcessingRoute
import io.sellmair.kompass.android.example.R
import io.sellmair.kompass.android.example.viewmodel.LoginProcessingViewModel
import io.sellmair.kompass.android.route


/**
 * Created by sebastiansellmair on 28.01.18.
 */
class LoginProcessingFragment : BaseFragment() {

    private val route: LoginProcessingRoute by route()
    private lateinit var viewModel: LoginProcessingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[LoginProcessingViewModel::class.java]
        viewModel.setCredentials(route.email, route.password)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_processing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name: TextView = view.findViewById(R.id.name)
        name.text = route.email
    }

    override fun onPause() {
        super.onPause()
        viewModel.stop()
    }
}
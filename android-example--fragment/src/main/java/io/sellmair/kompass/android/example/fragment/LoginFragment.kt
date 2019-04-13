package io.sellmair.kompass.android.example.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputLayout
import io.sellmair.kompass.android.example.R
import io.sellmair.kompass.android.example.viewmodel.LoginViewModel


/**
 * Created by sebastiansellmair on 27.01.18.
 */
class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    private lateinit var loginButton: View
    private lateinit var email: TextInputLayout
    private lateinit var password: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[LoginViewModel::class.java]

        viewModel.alertText.observe({ lifecycle }) { alertText ->
            alertText?.let {
                Toast.makeText(context, alertText, Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginButton = view.findViewById(R.id.login_button)
        email = view.findViewById(R.id.email)
        password = view.findViewById(R.id.password)

        email.editText?.setText(viewModel.email, TextView.BufferType.EDITABLE)
        password.editText?.setText(viewModel.password, TextView.BufferType.EDITABLE)

        loginButton.setOnClickListener {
            viewModel.onLoginClicked()
        }

        email.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable) {
                viewModel.email = editable.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        })

        password.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable) {
                viewModel.password = editable.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        })
    }


    override fun onStart() {
        super.onStart()
        activity?.title = getString(R.string.login)
    }
}
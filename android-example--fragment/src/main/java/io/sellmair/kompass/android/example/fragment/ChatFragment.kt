package io.sellmair.kompass.android.example.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import io.sellmair.kompass.android.example.ChatRoute
import io.sellmair.kompass.android.example.R
import io.sellmair.kompass.android.example.viewmodel.ChatViewModel
import io.sellmair.kompass.android.route
import java.util.*

/**
 * Created by sebastiansellmair on 27.01.18.
 */
class ChatFragment : BaseFragment() {
    private lateinit var viewModel: ChatViewModel
    private val route: ChatRoute by route()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apCompatActivity = activity as AppCompatActivity
        apCompatActivity.supportActionBar?.title = route.chatTitle
        apCompatActivity.supportActionBar?.subtitle = Date(route.lastSeenTime).toString()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }
}
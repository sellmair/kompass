package io.sellmair.example.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.sellmair.example.R
import io.sellmair.example.destination.ChatDestination
import io.sellmair.example.viewmodel.ChatViewModel
import io.sellmair.kompass.tryAsChatDestination
import java.util.*

/**
 * Created by sebastiansellmair on 27.01.18.
 */
class ChatFragment : Fragment() {
    private lateinit var viewModel: ChatViewModel
    private lateinit var destination: ChatDestination

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        destination = arguments?.tryAsChatDestination() ?: throw IllegalStateException()
        val apCompatActivity = activity as AppCompatActivity
        apCompatActivity.supportActionBar?.title = destination.chatTitle
        apCompatActivity.supportActionBar?.subtitle = Date(destination.lastSeenTime).toString()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }
}
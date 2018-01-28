package io.sellmair.example.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.sellmair.example.R
import io.sellmair.example.destination.ContactListDestination
import io.sellmair.example.viewmodel.ContactListViewModel
import io.sellmair.kompass.tryAsContactListDestination

/**
 * Created by sebastiansellmair on 27.01.18.
 */
class ContactListFragment : Fragment() {
    private lateinit var destination: ContactListDestination
    private lateinit var viewModel: ContactListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[ContactListViewModel::class.java]
        destination = arguments?.tryAsContactListDestination()
                ?: throw IllegalArgumentException()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contact_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.contact_list)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = Adapter()
    }

    override fun onResume() {
        super.onResume()
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.supportActionBar?.title = getString(R.string.contacts)
        appCompatActivity.supportActionBar?.subtitle = null
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val phone: TextView = view.findViewById(R.id.phone)
    }

    inner class Adapter : RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_contact, parent, false)

            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return destination.contacts.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val contact = destination.contacts[position]
            holder.phone.text = contact.phone
            holder.name.text = contact.name
            holder.itemView.setOnClickListener {
                viewModel.onContactClicked(contact)
            }
        }

    }
}
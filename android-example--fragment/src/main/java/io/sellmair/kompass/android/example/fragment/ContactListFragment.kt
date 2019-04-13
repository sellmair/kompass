package io.sellmair.kompass.android.example.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.sellmair.kompass.android.example.ContactListRoute
import io.sellmair.kompass.android.example.R
import io.sellmair.kompass.android.example.viewmodel.ContactListViewModel
import io.sellmair.kompass.android.route

/**
 * Created by sebastiansellmair on 27.01.18.
 */
class ContactListFragment : BaseFragment() {
    private val route: ContactListRoute by route()
    private lateinit var viewModel: ContactListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[ContactListViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            return route.contacts.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val contact = route.contacts[position]
            holder.phone.text = contact.phone
            holder.name.text = contact.name
            holder.itemView.setOnClickListener {
                viewModel.onContactClicked(contact)
            }
        }

    }
}
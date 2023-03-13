package com.khmaies.mycontacts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khmaies.mycontacts.R
import com.khmaies.mycontacts.databinding.ContactItemBinding
import com.khmaies.mycontacts.model.Contact
import com.khmaies.mycontacts.util.TextAvatar.createAvatarBitmap


/**
 * Created by Khmaies Hassen on 09,March,2023
 */
class ContactAdapter(val listener: OnItemClickListener) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    private val data = ArrayList<Contact>()

    interface OnItemClickListener {
        fun onItemClick(contact: Contact)
    }

    class ContactViewHolder(
        private val binding: ContactItemBinding,
        private val listener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Contact) {
            with(binding) {
                nameTextView.text =
                    binding.root.context.getString(R.string.full_name, data.name, data.lastName)
                numberTextView.text = data.number
                profilePicture.setImageBitmap(createAvatarBitmap("${data.name.first()}${data.lastName.first()}"))
                contactCard.setOnClickListener {
                    listener.onItemClick(data)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            ContactItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listener
        )
    }

    override fun getItemCount(): Int = data.count()

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(data[position])
    }

    // below method is use to update our list of notes.
    fun updateList(newList: List<Contact>) {
        // on below line we are clearing
        // our notes array list
        data.clear()
        // on below line we are adding a
        // new list to our all notes list.
        data.addAll(newList)
        // on below line we are calling notify data
        // change method to notify our adapter.
        notifyDataSetChanged()
    }

}
package com.example.contactsapp

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class ContactAdapter(context: Context, contacts: ArrayList<Contact>) :
    ArrayAdapter<Contact>(context, android.R.layout.simple_list_item_1, contacts) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val contact = getItem(position)

        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = "${contact?.name} - ${contact?.phone}"

        return view
    }
}

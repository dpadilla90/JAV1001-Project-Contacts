package com.example.contactsapp

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

/**
 * ArrayAdapter for displaying a list of contacts.
 *
 * @param context The context of the activity or fragment.
 * @param contacts The list of contacts to display.
 */
class ContactAdapter(context: Context, contacts: ArrayList<Contact>) :
    ArrayAdapter<Contact>(context, android.R.layout.simple_list_item_1, contacts) {

    /**
     * Get the view that displays the data at the specified position in the data set.
     *
     * @param position The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent The parent that this view will eventually be attached to.
     * @return The View corresponding to the data at the specified position.
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val contact = getItem(position)

        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = "${contact?.name} - ${contact?.phone}"

        return view
    }
}

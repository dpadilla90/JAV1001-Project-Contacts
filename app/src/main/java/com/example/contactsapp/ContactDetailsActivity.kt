package com.example.contactsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity



class ContactDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_view)

        val selectedContact = ContactManager.selectedContact

        if (selectedContact != null) {
            // Display the contact's information in the UI
            val textViewName: TextView = findViewById(R.id.textViewName)
            val textViewPhoneNumber: TextView = findViewById(R.id.textViewPhoneNumber)

            textViewName.text = selectedContact.name
            textViewPhoneNumber.text = selectedContact.phone

            // Set additional TextViews or views with the respective contact details

            val buttonEditContact: Button = findViewById(R.id.buttonEditContact)
            buttonEditContact.setOnClickListener {
                val intent = Intent(this, EditContactActivity::class.java)
                intent.putExtra("contact", selectedContact)
                startActivity(intent)
            }
        }
    }
    override fun onResume() {
        super.onResume()

        // Retrieve the selected contact from ContactManager
        val selectedContact = ContactManager.selectedContact

        if (selectedContact != null) {
            // Display the updated contact's information in the UI
            val textViewName: TextView = findViewById(R.id.textViewName)
            val textViewPhoneNumber: TextView = findViewById(R.id.textViewPhoneNumber)

            textViewName.text = selectedContact.name
            textViewPhoneNumber.text = selectedContact.phone
        }
    }

}



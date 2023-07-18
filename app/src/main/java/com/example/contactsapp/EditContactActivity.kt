package com.example.contactsapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.contactsapp.databinding.ActivityEditContactBinding

class EditContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditContactBinding
    private lateinit var contact: Contact
    private lateinit var viewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ContactViewModel::class.java]

        val selectedContact = ContactManager.selectedContact
        if (selectedContact != null) {
            contact = selectedContact

            // Populate the EditText fields with the contact's information
            binding.editTextName.setText(contact.name)
            binding.editTextPhone.setText(contact.phone)

            // Set the click listener for the Save button
            binding.buttonSaveContact.setOnClickListener {
                val newName = binding.editTextName.text.toString()
                val newPhone = binding.editTextPhone.text.toString()

                // Update the contact's information
                contact.name = newName
                contact.phone = newPhone

                // Update the contact in the viewModel.contacts list
                val index = viewModel.contacts.indexOfFirst { it.id == contact.id }
                if (index != -1) {
                    viewModel.contacts[index] = contact
                }

                // Return to the MainActivity with the updated contact information
                val returnIntent = Intent()
                returnIntent.putExtra("updatedContact", contact)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        }
    }
}

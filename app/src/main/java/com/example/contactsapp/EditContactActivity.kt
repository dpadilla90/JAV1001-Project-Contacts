package com.example.contactsapp

import android.app.Activity
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
            binding.editTextCompany.setText(contact.company) // Populate the company field
            binding.editTextEmail.setText(contact.email) // Populate the email field

            // Set the click listener for the Save button
            binding.buttonSaveContact.setOnClickListener {
                val newName = binding.editTextName.text.toString()
                val newPhone = binding.editTextPhone.text.toString()
                val newCompany = binding.editTextCompany.text.toString()
                val newEmail = binding.editTextEmail.text.toString()

                // Update the contact's information
                contact.name = newName
                contact.phone = newPhone
                contact.company = newCompany // Update the company field
                contact.email = newEmail // Update the email field

                // Use ViewModel to save or update contact
                viewModel.addOrUpdateContact(contact)

                setResult(Activity.RESULT_OK) // Indicate that the operation was successful
                finish()
            }

            // Set the click listener for the Delete button
            binding.buttonDeleteContact.setOnClickListener {
                viewModel.deleteContact(contact)
                setResult(Activity.RESULT_OK) // Indicate that the operation was successful
                finish()
            }
        }
    }
}


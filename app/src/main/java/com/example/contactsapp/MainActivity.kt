package com.example.contactsapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.contactsapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var contactAdapter: ArrayAdapter<Contact>
    private lateinit var viewModel: ContactViewModel
    private var selectedContact: Contact? = null

    private lateinit var editContactLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up the view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Initialize the ViewModel
        viewModel = ViewModelProvider(this)[ContactViewModel::class.java]


        // Initialize the adapter with an empty mutable list
        contactAdapter = ContactAdapter(this, mutableListOf())
        binding.listViewContacts.adapter = contactAdapter

        // Observe the LiveData from the ViewModel
        viewModel.contacts.observe(this) { contacts ->
            // Replace the internal data of the adapter with the new list
            contactAdapter.clear()
            contactAdapter.addAll(contacts)
            contactAdapter.notifyDataSetChanged()
        }

        // Set up the ActivityResultLauncher for launching EditContactActivity
        editContactLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val updatedContact: Contact? = data?.getParcelableExtra("updatedContact")
                val deletedContacts: ArrayList<Contact>? = data?.getParcelableArrayListExtra("deletedContact")

                // Handle the updated contact
                updatedContact?.let {
                    viewModel.addOrUpdateContact(it)
                }

                // Handle the deleted contacts
                deletedContacts?.let { contacts ->
                    for (contact in contacts) {
                        viewModel.deleteContact(contact)
                    }
                }

            }
        }

        // Set up the click listener for the Add Contact button
        binding.buttonAddContact.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val phone = binding.editTextPhone.text.toString()
            val company = binding.editTextCompany.text.toString()
            val email = binding.editTextEmail.text.toString()

            // Validate name and phone number
            if (name.isBlank() || phone.isBlank()) {
                Toast.makeText(this, "Name and Phone number are required!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedContact != null) {
                // Update existing contact
                selectedContact?.apply {
                    this.name = name
                    this.phone = phone
                    this.company = company
                    this.email = email
                }
                selectedContact?.let { viewModel.addOrUpdateContact(it) }
                selectedContact = null
            } else {
                // Add new contact
                val contact = Contact(0, name, phone, company, email)
                viewModel.addOrUpdateContact(contact)
            }

            clearFields()
        }


        // Set up the item click listener for the ListView
        binding.listViewContacts.setOnItemClickListener { _, _, position, _ ->
            val contact = viewModel.contacts.value?.get(position) ?: return@setOnItemClickListener
            ContactManager.selectedContact = contact

            val intent = Intent(this, EditContactActivity::class.java)
            intent.putExtra("contact", contact)
            editContactLauncher.launch(intent)
        }

        // Set up the item long click listener for the ListView
        binding.listViewContacts.setOnItemLongClickListener { _, _, position, _ ->
            val contact = viewModel.contacts.value?.get(position) ?: return@setOnItemLongClickListener true
            viewModel.deleteContact(contact)
            clearFields()
            showDeleteSnackbar(contact)
            true
        }
    }



    // Function to clear the input fields
    private fun clearFields() {
        binding.editTextName.text.clear()
        binding.editTextPhone.text.clear()
        binding.editTextCompany.text.clear()
        binding.editTextEmail.text.clear()
    }

    // Function to show a Snackbar indicating contact deletion
    private fun showDeleteSnackbar(contact: Contact) {
        Snackbar.make(
            binding.root,
            "Contact ${contact.name} deleted",
            Snackbar.LENGTH_LONG
        ).show()
    }
}

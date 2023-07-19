package com.example.contactsapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.contactsapp.databinding.ActivityMainBinding

import com.google.android.material.snackbar.Snackbar
import parcelableArrayList


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var contactAdapter: ArrayAdapter<Contact>
    private lateinit var viewModel: ContactViewModel
    private var selectedContact: Contact? = null

    private lateinit var editContactLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this)[ContactViewModel::class.java]

        contactAdapter = ContactAdapter(this, viewModel.contacts)
        binding.listViewContacts.adapter = contactAdapter

        editContactLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val updatedContacts: ArrayList<Contact>? = data?.parcelableArrayList("updatedContacts")
                val deletedContact: ArrayList<Contact>? = data?.parcelableArrayList("deletedContact")




                if (updatedContacts != null) {
                    for (updatedContact in updatedContacts) {
                        // Update the contact in the contact list
                        val index = viewModel.contacts.indexOfFirst { it.id == updatedContact.id }
                        if (index != -1) {
                            viewModel.contacts[index] = updatedContact
                        }
                    }
                } else if (deletedContact != null) {
                    // Remove the contacts from the contact list
                    for (contact in deletedContact) {
                        viewModel.contacts.remove(contact)
                    }
                }

                // Notify the contact adapter of the changes
                contactAdapter.notifyDataSetChanged()
            }
        }

        binding.buttonAddContact.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val phone = binding.editTextPhone.text.toString()

            if (selectedContact != null) {
                // Update existing contact
                selectedContact?.name = name
                selectedContact?.phone = phone
                selectedContact = null
            } else {
                // Add new contact
                val contact = Contact(viewModel.contacts.size + 1, name, phone)
                viewModel.contacts.add(contact)
            }

            contactAdapter.notifyDataSetChanged()
            clearFields()
        }

        binding.listViewContacts.setOnItemClickListener { _, _, position, _ ->
            val contact = viewModel.contacts[position]
            binding.editTextName.setText(contact.name)
            binding.editTextPhone.setText(contact.phone)
            selectedContact = contact
        }

        binding.listViewContacts.setOnItemLongClickListener { _, _, position, _ ->
            val contact = viewModel.contacts[position]
            viewModel.contacts.removeAt(position)
            contactAdapter.notifyDataSetChanged()
            clearFields()

            showDeleteSnackbar(contact)

            true
        }

        binding.listViewContacts.setOnItemClickListener { _, _, position, _ ->
            val contact = viewModel.contacts[position]
            ContactManager.selectedContact = contact

            // Start the EditContactActivity using the ActivityResultLauncher
            val intent = Intent(this, EditContactActivity::class.java)
            intent.putExtra("contact", contact)
            editContactLauncher.launch(intent)
        }
    }

    private fun clearFields() {
        binding.editTextName.text.clear()
        binding.editTextPhone.text.clear()
    }

    private fun showDeleteSnackbar(contact: Contact) {
        Snackbar.make(
            binding.root,
            "Contact ${contact.name} deleted",
            Snackbar.LENGTH_LONG
        ).show()
    }
}

package com.example.contactsapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.contactsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var contactAdapter: ArrayAdapter<Contact>
    private lateinit var viewModel: ContactViewModel
    private var selectedContact: Contact? = null

    private companion object {
        private const val EDIT_CONTACT_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this)[ContactViewModel::class.java]

        contactAdapter = ContactAdapter(this, viewModel.contacts)
        binding.listViewContacts.adapter = contactAdapter

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
            viewModel.contacts.removeAt(position)
            contactAdapter.notifyDataSetChanged()
            clearFields()
            true
        }

        binding.listViewContacts.setOnItemClickListener { _, _, position, _ ->
            val contact = viewModel.contacts[position]
            ContactManager.selectedContact = contact

            // Start the EditContactActivity and wait for the result
            val intent = Intent(this, EditContactActivity::class.java)
            intent.putExtra("contact", contact)
            startActivityForResult(intent, EDIT_CONTACT_REQUEST_CODE)
        }
    }

    private fun clearFields() {
        binding.editTextName.text.clear()
        binding.editTextPhone.text.clear()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == EDIT_CONTACT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val updatedContact = data?.getParcelableExtra<Contact>("updatedContact")
            if (updatedContact != null) {
                // Update the contact in the contact list
                val index = viewModel.contacts.indexOfFirst { it.id == updatedContact.id }
                if (index != -1) {
                    viewModel.contacts[index] = updatedContact
                    contactAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}

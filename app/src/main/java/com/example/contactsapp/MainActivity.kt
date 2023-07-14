package com.example.contactsapp

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.contactsapp.databinding.ActivityMainBinding
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var contactAdapter: ArrayAdapter<Contact>
    private lateinit var viewModel: ContactViewModel
    private var selectedContact: Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)

        // Initialize the contactAdapter only if it is null
        if (!::contactAdapter.isInitialized) {
            contactAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, viewModel.contacts)
            binding.listViewContacts.adapter = contactAdapter
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
            viewModel.contacts.removeAt(position)
            contactAdapter.notifyDataSetChanged()
            clearFields()
            true
        }

        binding.buttonEditContact.setOnClickListener {
            if (selectedContact != null) {
                binding.editTextName.setText(selectedContact?.name)
                binding.editTextPhone.setText(selectedContact?.phone)
            } else {
                Toast.makeText(this, "Select a contact to edit", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonDeleteContact.setOnClickListener {
            if (selectedContact != null) {
                viewModel.contacts.remove(selectedContact)
                contactAdapter.notifyDataSetChanged()
                clearFields()
                selectedContact = null
            } else {
                Toast.makeText(this, "Select a contact to delete", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearFields() {
        binding.editTextName.text.clear()
        binding.editTextPhone.text.clear()
    }
}




package com.example.contactsapp

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.contactsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var contactAdapter: ArrayAdapter<String>
    private lateinit var contacts: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        contacts = ArrayList()
        contactAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contacts)
        binding.listViewContacts.adapter = contactAdapter

        binding.buttonAddContact.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val phone = binding.editTextPhone.text.toString()
            val contact = "$name - $phone"

            contacts.add(contact)
            contactAdapter.notifyDataSetChanged()
            clearFields()
        }

        binding.listViewContacts.setOnItemClickListener { _, _, position, _ ->
            val contact = contacts[position]
            val name = contact.split(" - ")[0]
            val phone = contact.split(" - ")[1]

            binding.editTextName.setText(name)
            binding.editTextPhone.setText(phone)
        }

        binding.listViewContacts.setOnItemLongClickListener { _, _, position, _ ->
            contacts.removeAt(position)
            contactAdapter.notifyDataSetChanged()
            clearFields()
            true
        }
    }

    private fun clearFields() {
        binding.editTextName.text.clear()
        binding.editTextPhone.text.clear()
    }
}

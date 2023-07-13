package com.example.contactsapp


import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var editTextName: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var buttonAddContact: Button
    private lateinit var listViewContacts: ListView
    private lateinit var contactAdapter: ArrayAdapter<String>
    private lateinit var contacts: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextName = findViewById(R.id.editTextName)
        editTextPhone = findViewById(R.id.editTextPhone)
        buttonAddContact = findViewById(R.id.buttonAddContact)
        listViewContacts = findViewById(R.id.listViewContacts)

        contacts = ArrayList()
        contactAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contacts)
        listViewContacts.adapter = contactAdapter

        buttonAddContact.setOnClickListener {
            val name = editTextName.text.toString()
            val phone = editTextPhone.text.toString()
            val contact = "$name - $phone"

            contacts.add(contact)
            contactAdapter.notifyDataSetChanged()
            clearFields()
        }

        listViewContacts.setOnItemClickListener { _, _, position, _ ->
            val contact = contacts[position]
            val name = contact.split(" - ")[0]
            val phone = contact.split(" - ")[1]

            editTextName.setText(name)
            editTextPhone.setText(phone)
        }

        listViewContacts.setOnItemLongClickListener { _, _, position, _ ->
            contacts.removeAt(position)
            contactAdapter.notifyDataSetChanged()
            clearFields()
            true
        }
    }

    private fun clearFields() {
        editTextName.text.clear()
        editTextPhone.text.clear()
    }
}

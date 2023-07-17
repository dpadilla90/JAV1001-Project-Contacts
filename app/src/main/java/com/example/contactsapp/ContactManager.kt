package com.example.contactsapp

object ContactManager {
    private val contacts: ArrayList<Contact> = ArrayList()
    var selectedContact: Contact? = null
    // Function to update a contact
    fun updateContact(updatedContact: Contact) {
        val index = contacts.indexOfFirst { it.id == updatedContact.id }
        if (index != -1) {
            contacts[index] = updatedContact
        }
    }
}

package com.example.contactsapp

/**
 * Singleton object that manages contacts and provides functionality to update contacts.
 */
object ContactManager {
    private val contacts: ArrayList<Contact> = ArrayList()
    var selectedContact: Contact? = null

    /**
     * Updates the provided contact in the contact list.
     *
     * @param updatedContact The contact with updated information.
     */
    fun updateContact(updatedContact: Contact) {
        val index = contacts.indexOfFirst { it.id == updatedContact.id }
        if (index != -1) {
            contacts[index] = updatedContact
        }
    }
}

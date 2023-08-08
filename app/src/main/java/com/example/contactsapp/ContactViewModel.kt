package com.example.contactsapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel class that holds the contact data.
 */
class ContactViewModel(application: Application) : AndroidViewModel(application) {
    private val dao: ContactDao = AppDatabase.getDatabase(application).contactDao()

    val contacts: LiveData<List<Contact>> = dao.getAllContacts()

    fun addOrUpdateContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) { // Use Dispatchers.IO for database operations
            if (contact.id <= 0) {
                dao.insert(contact)
            } else {
                dao.update(contact)
            }
        }
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) { // Use Dispatchers.IO for database operations
            dao.delete(contact)
        }
    }

}

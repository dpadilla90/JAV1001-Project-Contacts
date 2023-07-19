package com.example.contactsapp

import androidx.lifecycle.ViewModel

/**
 * ViewModel class that holds the contact data.
 */
class ContactViewModel : ViewModel() {
    val contacts: ArrayList<Contact> = ArrayList()
}

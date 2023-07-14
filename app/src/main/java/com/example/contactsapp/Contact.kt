package com.example.contactsapp

data class Contact(val id: Int, var name: String, var phone: String) {
    override fun toString(): String {
        return "$name - $phone"
    }
}

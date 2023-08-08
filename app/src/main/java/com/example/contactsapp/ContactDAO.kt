package com.example.contactsapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact")
    fun getAllContacts(): LiveData<List<Contact>>  // Make sure this returns LiveData

    @Insert
    fun insert(contact: Contact)

    @Update
    fun update(contact: Contact)

    @Delete
    fun delete(contact: Contact)
}

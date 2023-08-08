package com.example.contactsapp

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a contact with an ID, name, phone number, company, and email.
 *
 * @property id The ID of the contact.
 * @property name The name of the contact.
 * @property phone The phone number of the contact.
 * @property company The company of the contact.
 * @property email The email of the contact.
 */
@Entity
data class Contact(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var name: String,
    var phone: String,
    var company: String?,
    var email: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString()
    )

    /**
     * Write the object's data to the provided Parcel.
     *
     * @param parcel The Parcel in which to write the data.
     * @param flags Additional flags about how the object should be written.
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(phone)
        parcel.writeString(company)
        parcel.writeString(email)
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable instance's marshaled representation.
     *
     * @return A bitmask indicating the set of special object types marshaled by this Parcelable object instance.
     */
    override fun describeContents(): Int {
        return 0
    }

    /**
     * Parcelable creator object that implements the Parcelable.Creator interface.
     */
    companion object CREATOR : Parcelable.Creator<Contact> {
        /**
         * Create a new instance of the Parcelable class, instantiating it from the given Parcel.
         *
         * @param parcel The Parcel from which to re-create the object.
         * @return The new instance of the Parcelable class.
         */
        override fun createFromParcel(parcel: Parcel): Contact {
            return Contact(parcel)
        }

        /**
         * Create a new array of the Parcelable class.
         *
         * @param size The size of the array.
         * @return An array of the Parcelable class, with every entry initialized to null.
         */
        override fun newArray(size: Int): Array<Contact?> {
            return arrayOfNulls(size)
        }
    }
}

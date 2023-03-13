package com.khmaies.mycontacts.repository

import com.khmaies.mycontacts.dao.ContactDao
import com.khmaies.mycontacts.model.Contact
import kotlinx.coroutines.flow.Flow

/**
 * Created by Khmaies Hassen on 08,March,2023
 */
class ContactRepository(private val contactDao: ContactDao) {

    val allContacts: Flow<List<Contact>> = contactDao.getAll()

    suspend fun insert(contact: Contact) {
        contactDao.insert(contact)
    }

    suspend fun deleteContact(contact: Contact) {
        contactDao.delete(contact)
    }

    fun searchContact(search: String): Flow<List<Contact>> {
        return contactDao.find(search)
    }

    suspend fun updateContact(contact: Contact) {
        contactDao.update(contact)
    }
}
package com.khmaies.mycontacts.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.khmaies.mycontacts.model.Contact
import com.khmaies.mycontacts.repository.ContactRepository
import kotlinx.coroutines.launch

/**
 * Created by Khmaies Hassen on 08,March,2023
 */
class ContactViewModel(private val repository: ContactRepository) : ViewModel() {

    val allContacts: LiveData<List<Contact>> = repository.allContacts.asLiveData()

    fun insert(contact: Contact) = viewModelScope.launch {
        repository.insert(contact)
    }

    fun delete(contact: Contact) = viewModelScope.launch {
        repository.deleteContact(contact)
    }

    fun update(contact: Contact) = viewModelScope.launch {
        repository.updateContact(contact)
    }

    fun search(search: String): LiveData<List<Contact>> {
        return repository.searchContact(search).asLiveData()
    }
}
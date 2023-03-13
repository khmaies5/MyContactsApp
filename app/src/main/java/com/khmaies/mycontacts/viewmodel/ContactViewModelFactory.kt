package com.khmaies.mycontacts.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khmaies.mycontacts.datasource.ContactDatabase
import com.khmaies.mycontacts.repository.ContactRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * Created by Khmaies Hassen on 08,March,2023
 */
class ContactViewModelFactory private constructor(private val application: Application) : ViewModelProvider.Factory {

    companion object {
        private var instance: ContactViewModelFactory? = null

        fun initialize(application: Application) {
            synchronized(this) {
                if (instance == null) {
                    instance = ContactViewModelFactory(application)
                }
            }
        }

        fun getInstance(): ContactViewModelFactory {
            return instance ?: throw ExceptionInInitializerError("You must initialize the factory with 'fun initialize(Application)'.")
        }
    }
    val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { ContactDatabase.getDatabaseInstance(application, applicationScope) }
    private val repository by lazy { ContactRepository(database.contactsDao()) }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            ContactViewModel::class.java -> ContactViewModel(repository) as T
            else -> throw IllegalArgumentException("No class found for '${modelClass.name}'")
        }
    }
}
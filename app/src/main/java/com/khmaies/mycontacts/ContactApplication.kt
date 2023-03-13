package com.khmaies.mycontacts

import android.app.Application
import com.khmaies.mycontacts.datasource.ContactDatabase
import com.khmaies.mycontacts.repository.ContactRepository
import com.khmaies.mycontacts.viewmodel.ContactViewModelFactory

/**
 * Created by Khmaies Hassen on 09,March,2023
 */
class ContactApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        ContactViewModelFactory.initialize(this)
    }
}
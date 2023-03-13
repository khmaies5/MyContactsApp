package com.khmaies.mycontacts

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.khmaies.mycontacts.dao.ContactDao
import com.khmaies.mycontacts.datasource.ContactDatabase
import com.khmaies.mycontacts.model.Contact
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Khmaies Hassen on 13,March,2023
 */
@RunWith(AndroidJUnit4::class)
@SmallTest
class ContactDaoTest {

    private lateinit var contactDatabase: ContactDatabase
    private lateinit var contactDao: ContactDao
    private val firstContact =
        Contact(id = 1, name = "Khmaies", lastName = "Hassen", number = "21383494")
    private val secondContact =
        Contact(id = 2, name = "Salma", lastName = "Smith", number = "22587963")

    @Before
    fun setUpDatabase() {
        contactDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ContactDatabase::class.java
        ).build()

        contactDao = contactDatabase.contactsDao()
    }

    @After
    fun closeDatabase() {
        contactDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertContact_returnsTrue() = runBlocking {
        contactDao.insert(firstContact)
        contactDao.getAll().test {

            val list = awaitItem()
            assertThat(list).contains(firstContact)
            cancel()
        }

    }

    @Test
    fun deleteContact_returnsTrue() = runBlocking {
        contactDao.insert(firstContact)
        contactDao.insert(secondContact)
        contactDao.delete(firstContact)

        contactDao.getAll().test {
            val list = awaitItem()
            assertThat(list).doesNotContain(firstContact)
            cancel()
        }

    }

    @Test
    fun updateContact_returnsTrue() = runBlocking {
        contactDao.insert(firstContact)

        val editedContact = Contact(1, "Ahmad", "Hassen", "21383494")

        contactDao.update(editedContact)

        contactDao.getAll().test {
            val list = awaitItem()
            assertThat(list).contains(editedContact)
            cancel()
        }

    }

}
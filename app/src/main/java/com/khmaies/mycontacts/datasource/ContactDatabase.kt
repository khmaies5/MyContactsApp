package com.khmaies.mycontacts.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.khmaies.mycontacts.dao.ContactDao
import com.khmaies.mycontacts.model.Contact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Created by Khmaies Hassen on 08,March,2023
 */
@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactsDao(): ContactDao

    private class ContactDatabaseCallback(private val scope: CoroutineScope): RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val contactDao = database.contactsDao()

                    contactDao.deleteAll()

                    // add sample data
                    var contact = Contact(name = "Khmaies", lastName = "Hassen", number =  "21383494")
                    contactDao.insert(contact)
                    contact = Contact(name = "Riadh", lastName = "Moussa", number =  "55874123")
                    contactDao.insert(contact)
                    contact = Contact(name = "Amal", lastName = "ben doudou", number =  "72211051")
                    contactDao.insert(contact)
                    contact = Contact(name = "Salma", lastName = "Smith", number =  "+21678945612")
                    contactDao.insert(contact)

                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ContactDatabase? = null

        fun getDatabaseInstance(context: Context, scope: CoroutineScope): ContactDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDatabase::class.java,
                    "contact_database"
                ).addCallback(ContactDatabaseCallback(scope)).build()
                INSTANCE = instance
                // return instance
                instance
            }

        }
    }
}
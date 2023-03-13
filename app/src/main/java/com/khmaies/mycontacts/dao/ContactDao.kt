package com.khmaies.mycontacts.dao

import androidx.room.*
import com.khmaies.mycontacts.model.Contact
import kotlinx.coroutines.flow.Flow

/**
 * Created by Khmaies Hassen on 08,March,2023
 */
@Dao
interface ContactDao {
    @Query("Select * from Contact")
    fun getAll(): Flow<List<Contact>>

    @Query("Select * from Contact where (name LIKE '%' || :search || '%' OR  last_name LIKE '%' || :search || '%' OR number LIKE '%' || :search || '%')")
    fun find(search: String?): Flow<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contact: Contact)

    @Update
    suspend fun update(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Query("DELETE FROM Contact")
    suspend fun deleteAll()
}
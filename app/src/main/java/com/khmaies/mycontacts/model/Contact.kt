package com.khmaies.mycontacts.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Created by Khmaies Hassen on 08,March,2023
 */
@Entity(tableName = "Contact")
@Parcelize
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    val number: String
) : Parcelable
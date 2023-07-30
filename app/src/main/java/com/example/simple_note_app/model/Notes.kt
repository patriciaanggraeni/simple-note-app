package com.example.simple_note_app.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "note")
data class Notes(

    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "notes")
    val notes: String,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "pinned")
    val pin: Boolean = false
) : Parcelable
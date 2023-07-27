package com.example.simple_note_app.database

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.simple_note_app.model.Note

interface NoteDao {

    @Query("SELECT * FROM note ORDER BY id DESC")
    fun read(): LiveData<List<Note>>

    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

}
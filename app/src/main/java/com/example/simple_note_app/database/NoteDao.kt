package com.example.simple_note_app.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.simple_note_app.model.Notes

@Dao
interface NoteDao {

    @Query("SELECT * FROM note ORDER BY id DESC")
    fun read(): LiveData<List<Notes>>

    @Insert
    suspend fun insert(notes: Notes)

    @Update
    suspend fun update(notes: Notes)

    @Delete
    suspend fun delete(notes: Notes)

}
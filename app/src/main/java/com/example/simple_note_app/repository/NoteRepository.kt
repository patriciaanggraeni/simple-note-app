package com.example.simple_note_app.repository

import androidx.lifecycle.LiveData
import com.example.simple_note_app.database.NoteDao
import com.example.simple_note_app.model.Note

class NoteRepository(
    private val noteDao: NoteDao
) {

    fun read(): LiveData<List<Note>> = this.noteDao.read()
    suspend fun insert(note: Note) = this.noteDao.insert(note)
    suspend fun update(note: Note) = this.noteDao.update(note)
    suspend fun delete(note: Note) = this.noteDao.delete(note)

}
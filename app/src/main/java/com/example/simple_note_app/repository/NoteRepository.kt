package com.example.simple_note_app.repository

import androidx.lifecycle.LiveData
import com.example.simple_note_app.database.NoteDao
import com.example.simple_note_app.model.Notes

class NoteRepository(
    private val noteDao: NoteDao
) {

    fun read(): LiveData<List<Notes>> = this.noteDao.read()
    fun search(query: String) = this.noteDao.searchNotes("%$query%")
    suspend fun insert(notes: Notes) = this.noteDao.insert(notes)
    suspend fun update(notes: Notes) = this.noteDao.update(notes)
    suspend fun delete(notes: Notes) = this.noteDao.delete(notes)

}
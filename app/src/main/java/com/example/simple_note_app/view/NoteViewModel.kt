package com.example.simple_note_app.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.simple_note_app.database.NoteDatabase
import com.example.simple_note_app.model.Notes
import com.example.simple_note_app.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {

    private val fetchNotes: LiveData<List<Notes>>
    private val noteRepository: NoteRepository

    init {
        val noteDao = NoteDatabase.getInstance(application).noteDao()
        noteRepository = NoteRepository(noteDao)
        fetchNotes = noteRepository.read()
    }

    fun readNote() = fetchNotes

    fun searchNote(query: String) = noteRepository.search(query)

    fun insertNote(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insert(notes)
        }
    }

    fun updateNote(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.update(notes)
        }
    }

    fun deleteNote(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.delete(notes)
        }
    }

}
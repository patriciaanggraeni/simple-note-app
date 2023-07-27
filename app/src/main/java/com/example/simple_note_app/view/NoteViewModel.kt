package com.example.simple_note_app.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.simple_note_app.helper.NoteDatabase
import com.example.simple_note_app.model.Note
import com.example.simple_note_app.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {

    private val fetchNote: LiveData<List<Note>>
    private val noteRepository: NoteRepository
    private  val noteDao by lazy { NoteDatabase(application).noteDao() }

    init {
        noteRepository = NoteRepository(noteDao)
        fetchNote = noteRepository.read()
    }

    fun readNote() = fetchNote

    fun insertNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insert(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.update(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.delete(note)
        }
    }

}
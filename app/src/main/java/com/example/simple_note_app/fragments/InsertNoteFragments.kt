package com.example.simple_note_app.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.simple_note_app.R
import com.example.simple_note_app.adapter.NoteAdapter
import com.example.simple_note_app.helper.FunctionHelper
import com.example.simple_note_app.model.Note
import com.example.simple_note_app.view.NoteViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class InsertNoteFragments : Fragment(), FunctionHelper {

    private lateinit var view: View
    private lateinit var btnBack: ImageView
    private lateinit var btnSave: ImageView
    private lateinit var noteViewModel: NoteViewModel

    private lateinit var inputTitle: EditText
    private lateinit var inputNotes: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = inflater.inflate(R.layout.fragment_insert_note_fragments, container, false)

        initComponents()
        setupListener()

        return view;
    }

    override fun initComponents() {
        btnBack = view.findViewById(R.id.btn_back)
        btnSave = view.findViewById(R.id.btn_save)
        inputTitle = view.findViewById(R.id.input_title)
        inputNotes = view.findViewById(R.id.input_notes)
    }

    override fun setupListener() {
        btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_from_insert_note_fragments_to_list_note_fragments)
        }

        btnSave.setOnClickListener {
            insertNote()
        }
    }

    private fun insertNote() {
        val title: String = inputTitle.text.toString()
        val notes: String = inputNotes.text.toString()
        val currentDate = Date()
        val date = SimpleDateFormat("dd-mm-yyyy hh-mm-ss", Locale.getDefault()).format(currentDate)
        val note = Note(0, title = title, notes = notes, date = date)

        noteViewModel = ViewModelProvider(this@InsertNoteFragments)[NoteViewModel::class.java]
        noteViewModel.insertNote(note)
        Toast.makeText(requireContext(), "Berhasil Menambahkan Catatan", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_from_insert_note_fragments_to_list_note_fragments)
    }
}
package com.example.simple_note_app.fragments

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.simple_note_app.R
import com.example.simple_note_app.helper.FunctionHelper
import com.example.simple_note_app.model.Notes
import com.example.simple_note_app.view.NoteViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UpdateNoteFragments : Fragment(), FunctionHelper {

    private val noteArgument by navArgs<UpdateNoteFragmentsArgs>()
    private lateinit var view: View
    private lateinit var noteViewModel: NoteViewModel

    private lateinit var btnBack: ImageView
    private lateinit var btnSave: ImageView
    private lateinit var inputNewDate: TextView
    private lateinit var inputNewTitle: EditText
    private lateinit var inputNewNotes: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = inflater.inflate(R.layout.fragment_update_note_fragments, container, false)

        initComponents()
        setupListener()

        return view
    }

    override fun initComponents() {
        btnBack = view.findViewById(R.id.btn_back_update)
        btnSave = view.findViewById(R.id.btn_save_updated)
        inputNewDate = view.findViewById(R.id.input_date_updated)
        inputNewTitle = view.findViewById(R.id.input_title_updated)
        inputNewNotes = view.findViewById(R.id.input_notes_updated)
    }

    override fun setupListener() {
        setUpdateNote()

        btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_from_update_note_fragments_to_list_note_fragments)
        }

        btnSave.setOnClickListener {
            updateNote()
        }
    }

    private fun updateNote() {
        val currentDate = Date()
        val id: Long = noteArgument.noteParcel.id
        val title: String = inputNewTitle.text.toString()
        val notes: String = inputNewNotes.text.toString()
        val date = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(currentDate)
        val pin: Boolean = noteArgument.noteParcel.pin

        val note = Notes(id, title, notes, date, pin)
        noteViewModel = ViewModelProvider(this@UpdateNoteFragments)[NoteViewModel::class.java]
        noteViewModel.updateNote(note)
        Toast.makeText(requireContext(), "Catatan Berhasil Diperbarui", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_from_update_note_fragments_to_list_note_fragments)
    }

    private fun setUpdateNote() {
        val title: String = noteArgument.noteParcel.title
        val notes: String = noteArgument.noteParcel.notes
        val date: String = noteArgument.noteParcel.date

        inputNewTitle.setText(title)
        inputNewNotes.setText(notes)
        inputNewDate.text = dateParse(date)
    }

    private fun dateParse(date: String): String? {
        val temp = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
        val formatted = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val parsedDate = temp.parse(date)

        return parsedDate?.let { formatted.format(it) }
    }
}
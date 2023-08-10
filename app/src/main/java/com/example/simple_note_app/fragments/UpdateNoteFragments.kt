package com.example.simple_note_app.fragments

import android.app.AlertDialog
import android.graphics.PorterDuff
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
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

    private lateinit var btnPin: ImageView
    private lateinit var btnDelete: ImageView
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
        btnPin = view.findViewById(R.id.btn_pin_note)
        btnBack = view.findViewById(R.id.btn_back_update)
        btnSave = view.findViewById(R.id.btn_save_updated)
        btnDelete = view.findViewById(R.id.btn_delete_note)
        inputNewDate = view.findViewById(R.id.input_date_updated)
        inputNewTitle = view.findViewById(R.id.input_title_updated)
        inputNewNotes = view.findViewById(R.id.input_notes_updated)
    }

    override fun setupListener() {
        setUpdateNote()

        btnPin.setOnClickListener {
            val pinned: Boolean = !noteArgument.noteParcel.pin
            val note = Notes(noteArgument.noteParcel.id, noteArgument.noteParcel.title, noteArgument.noteParcel.notes, noteArgument.noteParcel.date, pinned)
            noteViewModel = ViewModelProvider(this@UpdateNoteFragments)[NoteViewModel::class.java]
            noteViewModel.updateNote(note)

            val ifPinned = if (pinned) R.drawable.icn_pinned else R.drawable.icn_pin_white
            btnPin.setImageResource(ifPinned)
            Toast.makeText(requireContext(), "Note Berhasil Dipin", Toast.LENGTH_LONG).show()
        }

        btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_from_update_note_fragments_to_list_note_fragments)
        }

        btnSave.setOnClickListener {
            updateNote()
        }

        btnDelete.setOnClickListener {
            deleteNote()
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
        val pin: Boolean = noteArgument.noteParcel.pin

        inputNewTitle.setText(title)
        inputNewNotes.setText(notes)
        inputNewDate.text = dateParse(date)
        if (pin) btnPin.setImageResource(R.drawable.icn_pinned)
    }

    private fun deleteNote() {
        val deleteNoteBuilder = AlertDialog.Builder(requireContext())
        noteViewModel = ViewModelProvider(this@UpdateNoteFragments)[NoteViewModel::class.java]

        deleteNoteBuilder.setNegativeButton("Tidak") { _, _, -> }
        deleteNoteBuilder.setPositiveButton("Iya") { _, _, ->
            noteViewModel.deleteNote(noteArgument.noteParcel)
            Toast.makeText(requireContext(), "Catatan Berhasil Dihapus", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_from_update_note_fragments_to_list_note_fragments)
        }

        deleteNoteBuilder.setTitle("Hapus Catatan")
        deleteNoteBuilder.setMessage("Apakah kamu yakin akan menghapus catatan ini?")
        deleteNoteBuilder.create().show()
    }

    private fun dateParse(date: String): String? {
        val temp = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
        val formatted = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val parsedDate = temp.parse(date)

        return parsedDate?.let { formatted.format(it) }
    }
}
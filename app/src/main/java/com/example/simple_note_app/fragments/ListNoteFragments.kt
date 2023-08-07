package com.example.simple_note_app.fragments

import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.simple_note_app.R
import com.example.simple_note_app.adapter.NoteAdapter
import com.example.simple_note_app.helper.FunctionHelper
import com.example.simple_note_app.helper.OnNotePinClickListener
import com.example.simple_note_app.view.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListNoteFragments : Fragment(), FunctionHelper, OnNotePinClickListener {

    private lateinit var view: View
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteRecyclerView: RecyclerView
    private lateinit var btnAddNote: FloatingActionButton
//    private var selectedNotePosition: Int = RecyclerView.NO_POSITION

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = inflater.inflate(R.layout.fragment_list_note_fragments, container, false)

        initComponents()
        setupComponents()
        setupListener()

        registerForContextMenu(noteRecyclerView)

        return view
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        activity?.menuInflater?.inflate(R.menu.notes_menu, menu)
    }

    override fun initComponents() {
        btnAddNote = view.findViewById(R.id.btn_add_new_notes)
        noteRecyclerView = view.findViewById(R.id.notes_recyclerview)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        // val selectedNote = (noteRecyclerView.adapter as NoteAdapter).getNoteAtPosition()
        // val adapter = noteRecyclerView.adapter as? NoteAdapter
        // val selectedNote = adapter?.getNoteAtPosition()

        val adapter = noteRecyclerView.adapter as? NoteAdapter

        return when (item.itemId) {
            R.id.pin_note -> {
                adapter?.getSelectedId()?.let { id ->
                    val selectedNote = adapter.getNoteById(id)
                    selectedNote?.apply {
                        pin = !pin
                        Log.i("position", pin.toString())
                        noteViewModel.updateNote(this)
                        item.title = if (pin) getString(R.string.unpin_notes) else getString(R.string.pin_notes)
                    }
                }
                true
            }
            R.id.delete_note -> {
                true
            }
            else -> {
                super.onContextItemSelected(item)
            }
        }
    }

    override fun setupListener() {
        btnAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_from_list_note_fragments_to_insert_note_fragments)
        }
    }

    override fun setupComponents() {
        val adapter = NoteAdapter(requireActivity())
        noteRecyclerView.adapter = adapter
        noteRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        noteViewModel = ViewModelProvider(requireActivity())[NoteViewModel::class.java]
        noteViewModel.readNote().observe(viewLifecycleOwner) { note -> adapter.setDataNote(note) }
    }
}
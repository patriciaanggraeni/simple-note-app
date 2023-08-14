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
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.simple_note_app.R
import com.example.simple_note_app.adapter.NoteAdapter
import com.example.simple_note_app.helper.FunctionHelper
import com.example.simple_note_app.helper.OnNotePinClickListener
import com.example.simple_note_app.model.Notes
import com.example.simple_note_app.view.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListNoteFragments : Fragment(), FunctionHelper, OnNotePinClickListener {

    private lateinit var view: View
    private lateinit var adapter: NoteAdapter
    private lateinit var searchNote: SearchView
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteRecyclerView: RecyclerView
    private lateinit var btnAddNote: FloatingActionButton

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
        searchNote = view.findViewById(R.id.search_notes)
        btnAddNote = view.findViewById(R.id.btn_add_new_notes)
        noteRecyclerView = view.findViewById(R.id.notes_recyclerview)
    }

    override fun setupListener() {
        noteViewModel = ViewModelProvider(this@ListNoteFragments)[NoteViewModel::class.java]
        var searchResultObserver: Observer<List<Notes>>? = null

        btnAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_from_list_note_fragments_to_insert_note_fragments)
        }

        searchNote.setOnQueryTextListener( object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                 newText?.let {
                    searchResultObserver?.let { observer ->
                        noteViewModel.searchNote(it).removeObserver(observer)
                    }

                    searchResultObserver = Observer { note ->
                        adapter.setDataNote(note)
                    }

                    noteViewModel.searchNote(it).observe(viewLifecycleOwner, searchResultObserver!!)
                }
                return true
            }
        })
    }

    override fun setupComponents() {
        adapter = NoteAdapter(requireActivity())
        noteRecyclerView.adapter = adapter
        noteRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        noteViewModel = ViewModelProvider(requireActivity())[NoteViewModel::class.java]
        noteViewModel.readNote().observe(viewLifecycleOwner) { note -> adapter.setDataNote(note) }
    }
}
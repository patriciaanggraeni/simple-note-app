package com.example.simple_note_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.simple_note_app.R
import com.example.simple_note_app.adapter.NoteAdapter
import com.example.simple_note_app.helper.FunctionHelper
import com.example.simple_note_app.view.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListNoteFragments : Fragment(), FunctionHelper {

    private lateinit var view: View
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

        return view
    }

    override fun initComponents() {
        btnAddNote = view.findViewById(R.id.btn_add_new_notes)
        noteRecyclerView = view.findViewById(R.id.notes_recyclerview)
    }

    override fun setupListener() {
        btnAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_from_list_note_fragments_to_insert_note_fragments)
        }
    }

    override fun setupComponents() {
        val adapter = NoteAdapter(requireContext())
        noteRecyclerView.adapter = adapter
        noteRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        noteViewModel = ViewModelProvider(requireActivity())[NoteViewModel::class.java]
        noteViewModel.readNote().observe(viewLifecycleOwner) { note -> adapter.setDataNote(note) }
    }
}
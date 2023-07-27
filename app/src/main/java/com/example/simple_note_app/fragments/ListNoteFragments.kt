package com.example.simple_note_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.simple_note_app.R
import com.example.simple_note_app.helper.FunctionHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListNoteFragments : Fragment(), FunctionHelper {

    private lateinit var view: View
    private lateinit var btnAddNote: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = inflater.inflate(R.layout.fragment_list_note_fragments, container, false)

        initComponents()
        setupListener()

        return view
    }

    @Override
    override fun initComponents() {
        btnAddNote = view.findViewById(R.id.btn_add_new_notes)
    }

    @Override
    override fun setupListener() {
        btnAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_from_list_note_fragments_to_insert_note_fragments)
        }
    }
}
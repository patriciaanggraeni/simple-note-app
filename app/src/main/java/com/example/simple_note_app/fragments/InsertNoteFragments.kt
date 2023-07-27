package com.example.simple_note_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.example.simple_note_app.R
import com.example.simple_note_app.helper.FunctionHelper

class InsertNoteFragments : Fragment(), FunctionHelper {

    private lateinit var view: View
    private lateinit var btnBack: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = inflater.inflate(R.layout.fragment_insert_note_fragments, container, false)

        initComponents()
        setupListener()

        return view;
    }

    @Override
    override fun initComponents() {
        btnBack = view.findViewById(R.id.btn_back)
    }

    @Override
    override fun setupListener() {
        btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_from_insert_note_fragments_to_list_note_fragments)
        }
    }
}
package com.example.simple_note_app.fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.PorterDuff.Mode
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.simple_note_app.R
import com.example.simple_note_app.helper.FunctionHelper
import com.google.android.material.textfield.TextInputEditText

class LoginFragment : Fragment(), FunctionHelper {

    private lateinit var view: View
    private lateinit var btnSaveLogin: Button
    private lateinit var inputEmail: EditText
    private lateinit var inputPassword: EditText
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = inflater.inflate(R.layout.fragment_login, container, false)

        initComponents()
        setupComponents()
        setupListener()

        return view;
    }

    override fun initComponents() {
        inputEmail = view.findViewById(R.id.input_user_email)
        btnSaveLogin = view.findViewById(R.id.btn_login_user)
        inputPassword = view.findViewById(R.id.input_user_password)
    }

    override fun setupComponents() {
        sharedPreferences = requireContext().getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    override fun setupListener() {
        val inputEmail = inputEmail.text.toString()
        val inputPassword = inputPassword.text.toString()

        btnSaveLogin.setOnClickListener {
            editor.putString("username", inputEmail)
            editor.putString("password", inputPassword)
            editor.putBoolean("is_logged_in", true)
            editor.apply()
            Toast.makeText(requireContext(), "Login Berhasil", Toast.LENGTH_LONG).show()
            Toast.makeText(requireContext(), "Selamat Datang", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_from_login_fragments_to_list_note_fragments)
        }
    }
}
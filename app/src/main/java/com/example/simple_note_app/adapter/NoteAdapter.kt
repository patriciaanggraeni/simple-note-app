package com.example.simple_note_app.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simple_note_app.R
import com.example.simple_note_app.model.Note

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private var listNote: List<Note> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.notes_cardview, parent, false)
    )

    override fun onBindViewHolder(holder: NoteAdapter.ViewHolder, position: Int) {
        val note = listNote[position]
        holder.title.text = note.title
        holder.notes.text = note.notes
        holder.date.text = note.date

        if (note.pin) {
            holder.pinned.setImageResource(R.drawable.icn_pin)
        } else {
            holder.pinned.setImageResource(0)
        }
    }

    override fun getItemCount() = listNote.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val notes: TextView = view.findViewById(R.id.notes)
        val date: TextView = view.findViewById(R.id.date)
        val pinned: ImageView = view.findViewById(R.id.pinned)
    }

    fun setDataNote(listNote: List<Note>) {
        this.listNote = listNote
        notifyDataSetChanged()
    }

}
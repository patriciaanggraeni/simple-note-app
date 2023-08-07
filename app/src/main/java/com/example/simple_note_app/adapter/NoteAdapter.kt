package com.example.simple_note_app.adapter

import android.content.Context
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.simple_note_app.R
import com.example.simple_note_app.fragments.ListNoteFragmentsDirections
import com.example.simple_note_app.model.Notes

class NoteAdapter(
    private val context: Context
) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private var listNotes: List<Notes> = emptyList()
    private var selectedNotePosition: Int = RecyclerView.NO_POSITION
    private var selectedNoteId: Long? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {
        val title: TextView = view.findViewById(R.id.title)
        val notes: TextView = view.findViewById(R.id.notes)
        val date: TextView = view.findViewById(R.id.date)
        val pinned: ImageView = view.findViewById(R.id.pinned)
        val cardview: CardView = view.findViewById(R.id.notes_card)

        init {
            view.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(menu: ContextMenu?, view: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            if (menuInfo is AdapterView.AdapterContextMenuInfo) {
                selectedNotePosition = bindingAdapterPosition
                selectedNoteId = listNotes[bindingAdapterPosition].id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.notes_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = listNotes[position]
        holder.title.text = note.title
        holder.notes.text = note.notes
        holder.date.text = note.date
        holder.cardview.setCardBackgroundColor(getRandomColor())
        if (note.pin) holder.pinned.setImageResource(R.drawable.icn_pin)
        else holder.pinned.setImageResource(0)

        holder.cardview.setOnClickListener {
            val action = ListNoteFragmentsDirections.actionFromListNoteFragmentsToUpdateNoteFragments(note)
            holder.cardview.findNavController().navigate(action)
        }

        holder.cardview.setOnLongClickListener {
            it.showContextMenu()
            true
        }
    }

    override fun getItemCount() = listNotes.size

    fun setDataNote(listNotes: List<Notes>) {
        this.listNotes = listNotes
        notifyDataSetChanged()
    }

    private fun getRandomColor(): Int {
        val colors = listOf(
            R.color.color_note_pink,
            R.color.color_note_green,
            R.color.color_note_blue,
            R.color.color_note_purple,
            R.color.color_note_white,
            R.color.color_note_yellow
        )

        return ContextCompat.getColor(context, colors.random())
    }

    fun getNoteById(id: Long): Notes? {
        return listNotes.find { it.id == id }
    }

    fun getSelectedId() = this.selectedNoteId
}

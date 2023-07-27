package com.example.simple_note_app.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class NoteDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {

        @Volatile
        private var INSTANCE: NoteDatabase? = null
        private const val DATABASE_NAME: String = "NoteDatabase.db"

        operator fun invoke(context: Context) = synchronized(this) {
            INSTANCE ?: getInstance(context).also {
                INSTANCE = it
            }
        }

        private fun getInstance(context: Context): NoteDatabase = Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}
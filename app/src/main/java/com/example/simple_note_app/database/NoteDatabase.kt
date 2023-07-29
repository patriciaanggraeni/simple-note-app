package com.example.simple_note_app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.simple_note_app.model.Note

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = true
)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {

        @Volatile
        private var INSTANCE: NoteDatabase? = null
        private const val DATABASE_NAME: String = "NoteDatabase.db"

        fun getInstance(context: Context): NoteDatabase {
            val temp = INSTANCE
            if (temp != null) return temp
            else {
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java,
                        DATABASE_NAME
                    ).build()

                    INSTANCE = instance
                    return instance
                }
            }
        }
//        operator fun invoke(context: Context) = synchronized(this) {
//            INSTANCE ?: getInstance(context).also {
//                INSTANCE = it
//            }
//        }
//
//        private fun getInstance(context: Context): NoteDatabase = Room.databaseBuilder(
//            context.applicationContext,
//            NoteDatabase::class.java,
//            DATABASE_NAME
//        ).build()
    }
}
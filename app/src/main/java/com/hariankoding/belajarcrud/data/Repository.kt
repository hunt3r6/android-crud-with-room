package com.hariankoding.belajarcrud.data

import androidx.lifecycle.LiveData

class Repository private constructor(
    private val database: NoteDatabase
) {
    companion object {
        private var instance: Repository? = null
        fun getInstance(
            database: NoteDatabase
        ): Repository = instance ?: synchronized(this) {
            instance ?: Repository(database)
        }
    }

    fun getAllNote(): LiveData<List<Note>> = database.noteDao().getAllNote()

    suspend fun insert(note: Note) = database.noteDao().insertNote(note)
    suspend fun deleteNote(note: Note) = database.noteDao().deleteNote(note)
    suspend fun updateNote(note: Note) = database.noteDao().updateNote(note)

}
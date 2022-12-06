package com.hariankoding.belajarcrud.di

import android.content.Context
import com.hariankoding.belajarcrud.data.NoteDatabase
import com.hariankoding.belajarcrud.data.Repository

object Injection {
    fun provideRepository(context: Context): Repository {
        val database = NoteDatabase.getDatabase(context)
        return Repository.getInstance(database)
    }
}
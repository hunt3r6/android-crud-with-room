package com.hariankoding.belajarcrud.ui.crud

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hariankoding.belajarcrud.data.Note
import com.hariankoding.belajarcrud.data.Repository
import kotlinx.coroutines.launch

class CrudViewModel(private val repository: Repository) : ViewModel() {

    fun insertNote(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        repository.updateNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }

}
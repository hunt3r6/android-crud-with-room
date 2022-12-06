package com.hariankoding.belajarcrud.ui.main

import androidx.lifecycle.ViewModel
import com.hariankoding.belajarcrud.data.Repository

class MainViewModel(private val repository: Repository) : ViewModel() {
    fun getAllNote() = repository.getAllNote()
}
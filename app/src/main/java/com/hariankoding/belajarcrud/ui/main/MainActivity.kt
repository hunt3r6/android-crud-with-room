package com.hariankoding.belajarcrud.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hariankoding.belajarcrud.data.Note
import com.hariankoding.belajarcrud.databinding.ActivityMainBinding
import com.hariankoding.belajarcrud.ui.crud.CrudActivity
import com.hariankoding.belajarcrud.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private val noteAdapter by lazy { NoteAdapter { note -> detailNote(note) } }

    private fun detailNote(note: Note) {
        val intent = Intent(this@MainActivity, CrudActivity::class.java)
        intent.putExtra(CrudActivity.EXTRA_NOTE, note)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUi()
        setObserver()
        setOnClick()
    }

    private fun setOnClick() = with(binding) {
        addNote.setOnClickListener {
            startActivity(Intent(this@MainActivity, CrudActivity::class.java))
        }
    }

    private fun setObserver() {
        viewModel.getAllNote().observe(this) {
            noteAdapter.submitList(it)
        }
    }

    private fun setUi() = with(binding) {
        rvMote.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = noteAdapter
            setHasFixedSize(true)
        }
    }
}
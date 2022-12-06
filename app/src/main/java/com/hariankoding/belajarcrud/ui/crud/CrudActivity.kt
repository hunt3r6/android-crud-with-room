package com.hariankoding.belajarcrud.ui.crud

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.hariankoding.belajarcrud.R
import com.hariankoding.belajarcrud.data.Note
import com.hariankoding.belajarcrud.databinding.ActivityCrudBinding
import com.hariankoding.belajarcrud.utils.DateHelper
import com.hariankoding.belajarcrud.viewmodel.ViewModelFactory

class CrudActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrudBinding
    private var isEdit = false
    private var note: Note? = null
    private val crudViewModel: CrudViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrudBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUi()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> showAlertDialog()
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle("Delete")
            setMessage("Do you want delete?")
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                crudViewModel.deleteNote(note as Note)
                showToast(getString(R.string.delete_success))
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.cancel()
            }
        }
        alertDialogBuilder.show()
    }

    private fun setUi() {
        note = intent.getParcelableExtra(EXTRA_NOTE)
        when (note) {
            null -> {
                note = Note()
            }
            else -> {
                isEdit = true
            }
        }
        val actionBarTitle: String
        val btnTitle: String
        when (isEdit) {
            true -> {
                actionBarTitle = getString(R.string.change)
                btnTitle = getString(R.string.update)
                note?.let {
                    binding.edtTitle.setText(it.title)
                    binding.edtDesc.setText(it.description)
                }
            }
            else -> {
                actionBarTitle = getString(R.string.add_note)
                btnTitle = getString(R.string.save)
            }
        }

        supportActionBar?.apply {
            title = actionBarTitle
            setDisplayHomeAsUpEnabled(true)
        }

        binding.btnSave.apply {
            text = btnTitle
            setOnClickListener {
                val title = binding.edtTitle.text.toString().trim()
                val desc = binding.edtDesc.text.toString().trim()
                note?.let { note ->
                    note.title = title
                    note.description = desc
                }
                when {
                    title.isEmpty() -> {
                        binding.tlTitle.error = getString(R.string.note_empty)
                    }
                    desc.isEmpty() -> {
                        binding.tlDesc.error = getString(R.string.note_empty)
                    }
                    else -> {
                        if (isEdit) {
                            crudViewModel.updateNote(note as Note)
                            showToast(getString(R.string.update_success))
                        } else {
                            note?.let { note ->
                                note.date = DateHelper.getCurrentDate()
                            }
                            crudViewModel.insertNote(note as Note)
                            showToast(getString(R.string.save_success))
                        }
                        finish()
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_NOTE = "extra_note"
    }
}
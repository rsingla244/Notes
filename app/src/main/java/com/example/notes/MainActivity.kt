package com.example.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), INotesRVAdapter {

    //this is how we create instance
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = NotesRVAdapter(this, this)
        recyclerView.adapter = adapter

        //viewmodelprovder is a class which provide view model like so that it get connect with fun of mainactivity like oncreate which are there in your activity
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        //we are fetching data by view model
        //like allnote is livecycle so we can observe
        viewModel.allNotes.observe(this, Observer {list ->
            list?.let {
                adapter.updateList(it)
            }
        })


    }

    fun submitData(view: View) {
        val noteText = input.text.toString()
        if (noteText.isNotEmpty()) {
            viewModel.insertNote(Note(noteText))
            Toast.makeText(this, "$noteText Inserted", Toast.LENGTH_LONG).show()
        }
    }

    override fun onItemClicked(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this, "${note.text} Deleted", Toast.LENGTH_LONG).show()
    }
}
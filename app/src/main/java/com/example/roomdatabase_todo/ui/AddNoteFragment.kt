package com.example.roomdatabase_todo.ui


import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.roomdatabase_todo.R
import com.example.roomdatabase_todo.db.Note
import com.example.roomdatabase_todo.db.NoteDatabase
import kotlinx.android.synthetic.main.fragment_add_note.*

class AddNoteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //NoteDatabase(activity!!).getNoteDao()
        button_Save.setOnClickListener{
            val noteTitle=editText_title.text.toString().trim()
            val noteBody=editText_note.text.toString().trim()

            if(noteTitle.isEmpty()){
                editText_title.error="title required"
                editText_title.requestFocus()
                return@setOnClickListener
            }

            if(noteBody.isEmpty()){
                editText_note.error="note required"
                editText_note.requestFocus()
                return@setOnClickListener
            }

            val note= Note(noteTitle,noteBody)
           // NoteDatabase(activity!!).getNoteDao().addNote(note)

            Log.i("Save note", "Save note function worked")
            SaveNote(note)
        }
    }

    //Custom function to save note
    private fun SaveNote(note:Note){
        class saveNote:AsyncTask<Void,Void,Void>(){
            override fun doInBackground(vararg params: Void?): Void? {
                NoteDatabase(activity!!).getNoteDao().addNote(note)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                Toast.makeText(activity,"Note saved",Toast.LENGTH_SHORT).show()
            }
        }
        saveNote().execute()
    }

}
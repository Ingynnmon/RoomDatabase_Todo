package com.example.roomdatabase_todo.ui


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation

import com.example.roomdatabase_todo.R
import com.example.roomdatabase_todo.db.Note
import com.example.roomdatabase_todo.db.NoteDatabase
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.launch

class AddNoteFragment : BaseFragment() {

    private var note: Note? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //NoteDatabase(activity!!).getNoteDao()

        arguments?.let {
            note=AddNoteFragmentArgs.fromBundle(it).note
            editText_title.setText(note?.title)
            editText_note.setText(note?.note)
        }
        button_Save.setOnClickListener{view->
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
            launch {
                context?.let {
                    val mNote = Note(noteTitle, noteBody)
                    if (note == null) {
                        NoteDatabase(it).getNoteDao().addNote(mNote)
                        it.toast("Note Saved")
                    } else {
                        mNote.id = note!!.id
                        NoteDatabase(it).getNoteDao().updateNote(mNote)
                        it.toast("Note Updated")
                    }


                    val action = AddNoteFragmentDirections.actionSaveNote()
                    Navigation.findNavController(view).navigate(action)
                }
                }
            }



            //val note= Note(noteTitle,noteBody)
           // NoteDatabase(activity!!).getNoteDao().addNote(note)

           /* Log.i("Save note", "Save note function worked")
           //call custom save note fun
            SaveNote(note)*/


        }

    private fun deleteNote() {
        AlertDialog.Builder(context!!).apply {
            setTitle("Are you sure?")
            setMessage("You cannot undo this operation")
            setPositiveButton("Yes") { _, _ ->
                launch {
                    NoteDatabase(context).getNoteDao().deleteNote(note!!)
                    val action = AddNoteFragmentDirections.actionSaveNote()
                    Navigation.findNavController(view!!).navigate(action)
                }
            }
            setNegativeButton("No") { _, _ ->

            }
        }.create().show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete -> if (note != null) deleteNote() else context?.toast("Cannot Delete")
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }
    /*//Custom function to save note
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
*/
}

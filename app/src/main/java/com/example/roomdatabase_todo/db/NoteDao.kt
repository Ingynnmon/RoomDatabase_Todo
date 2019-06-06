package com.example.roomdatabase_todo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {
    @Insert
    fun addNote(note:Note)

    @Query("SELECT * from Note")
    fun getAllNotes():List<Note>

    @Insert
    fun addMultipleNotes(vararg note:Note)
}
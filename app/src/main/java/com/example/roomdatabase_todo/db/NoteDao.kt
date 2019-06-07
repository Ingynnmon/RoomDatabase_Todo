package com.example.roomdatabase_todo.db

import androidx.room.*

@Dao
interface NoteDao {
    @Insert
    suspend fun addNote(note:Note)

    @Query("SELECT * from Note ORDER BY id DESC")
    suspend fun getAllNotes():List<Note>

    @Insert
    suspend fun addMultipleNotes(vararg note:Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}
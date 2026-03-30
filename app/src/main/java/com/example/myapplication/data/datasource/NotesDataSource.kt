package com.example.myapplication.data.datasource

import com.example.myapplication.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesDataSource {
    fun getAllNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: Long): Note?
    suspend fun addNote(note: Note): Long
    suspend fun deleteNote(id: Long)
}
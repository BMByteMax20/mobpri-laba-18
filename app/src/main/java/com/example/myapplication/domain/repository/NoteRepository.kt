package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.Note


interface NoteRepository {
    suspend fun getNotes(): Result<List<Note>>
    suspend fun addNote(note: Note): Result<Unit>
    suspend fun deleteNote(id: Long): Result<Unit>
}
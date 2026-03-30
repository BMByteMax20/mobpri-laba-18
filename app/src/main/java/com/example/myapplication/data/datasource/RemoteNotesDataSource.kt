package com.example.myapplication.data.datasource

import com.example.myapplication.domain.model.Note
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteNotesDataSource @Inject constructor() : NotesDataSource {
    private val remoteNotes = mutableListOf<Note>()

    override fun getAllNotes(): Flow<List<Note>> = flow {
        delay(1000)
        emit(remoteNotes.toList())
    }

    override suspend fun getNoteById(id: Long): Note? =
        remoteNotes.find { it.id == id }

    override suspend fun addNote(note: Note): Long {
        delay(500)
        val newNote = note.copy(id = System.currentTimeMillis())
        remoteNotes.add(newNote)
        return newNote.id
    }

    override suspend fun deleteNote(id: Long) {
        delay(500)
        remoteNotes.removeAll { it.id == id }
    }
}
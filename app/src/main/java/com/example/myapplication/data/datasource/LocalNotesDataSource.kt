package com.example.myapplication.data.datasource

import com.example.myapplication.data.local.dao.NoteDao
import com.example.myapplication.data.local.mapper.toDomain
import com.example.myapplication.data.local.mapper.toEntity
import com.example.myapplication.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalNotesDataSource @Inject constructor(
    private val dao: NoteDao
) : NotesDataSource {

    override fun getAllNotes(): Flow<List<Note>> =
        dao.getAll().map { entities -> entities.map { it.toDomain() } }

    override suspend fun getNoteById(id: Long): Note? =
        dao.getById(id)?.toDomain()

    override suspend fun addNote(note: Note): Long =
        dao.insert(note.toEntity())

    override suspend fun deleteNote(id: Long) {
        dao.getById(id)?.let { dao.delete(it) }
    }
}
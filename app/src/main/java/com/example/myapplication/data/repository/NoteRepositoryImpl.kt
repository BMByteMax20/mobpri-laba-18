package com.example.myapplication.data.repository

import com.example.myapplication.data.datasource.NotesDataSource
import com.example.myapplication.di.qualifiers.LocalDataSource
import com.example.myapplication.di.qualifiers.RemoteDataSource
import com.example.myapplication.domain.model.Note
import com.example.myapplication.domain.repository.NoteRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NotesRepositoryImpl @Inject constructor(
    @LocalDataSource private val localDataSource: NotesDataSource,
    @RemoteDataSource private val remoteDataSource: NotesDataSource
) : NoteRepository {

    override suspend fun getNotes(): Result<List<Note>> {
        return try {
            val localNotes = localDataSource.getAllNotes().first()
            if (localNotes.isNotEmpty()) {
                Result.success(localNotes)
            } else {
                val remoteNotes = remoteDataSource.getAllNotes().first()
                remoteNotes.forEach { note ->
                    localDataSource.addNote(note)
                }
                Result.success(remoteNotes)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addNote(note: Note): Result<Unit> {
        return try {
            localDataSource.addNote(note)
            remoteDataSource.addNote(note)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteNote(id: Long): Result<Unit> {
        return try {
            localDataSource.deleteNote(id)
            remoteDataSource.deleteNote(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
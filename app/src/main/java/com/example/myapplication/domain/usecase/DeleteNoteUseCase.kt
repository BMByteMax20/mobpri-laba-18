package com.example.myapplication.domain.usecase

import com.example.myapplication.di.qualifiers.IoDispatcher
import com.example.myapplication.domain.repository.NoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteNoteUseCase @Inject constructor(
    private val repository: NoteRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(id: Long): Result<Unit> = withContext(ioDispatcher) {
        repository.deleteNote(id)
    }
}
package com.example.myapplication.domain.usecase

import com.example.myapplication.di.qualifiers.IoDispatcher
import com.example.myapplication.domain.model.Note
import com.example.myapplication.domain.repository.NoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddNoteUseCase @Inject constructor(
    private val repository: NoteRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(title: String, content: String): Result<Unit> = withContext(ioDispatcher) {
        if (title.isBlank()) {
            return@withContext Result.failure(IllegalArgumentException("Заголовок не может быть пустым"))
        }

        val note = Note(
            id = 0,
            title = title,
            content = content,
            createdAt = System.currentTimeMillis()
        )

        repository.addNote(note)
    }
}
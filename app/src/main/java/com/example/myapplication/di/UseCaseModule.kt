package com.example.myapplication.di

import com.example.myapplication.di.qualifiers.IoDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.example.myapplication.domain.repository.NoteRepository
import com.example.myapplication.domain.usecase.AddNoteUseCase
import com.example.myapplication.domain.usecase.DeleteNoteUseCase
import com.example.myapplication.domain.usecase.GetNotesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideGetNotesUseCase(
        repository: NoteRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): GetNotesUseCase {
        return GetNotesUseCase(repository, ioDispatcher)
    }

    @Provides
    @Singleton
    fun provideAddNoteUseCase(
        repository: NoteRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): AddNoteUseCase {
        return AddNoteUseCase(repository, ioDispatcher)
    }

    @Provides
    @Singleton
    fun provideDeleteNoteUseCase(
        repository: NoteRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): DeleteNoteUseCase {
        return DeleteNoteUseCase(repository, ioDispatcher)
    }
}
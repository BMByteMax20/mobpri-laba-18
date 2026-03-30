package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.datasource.LocalNotesDataSource
import com.example.myapplication.data.datasource.NotesDataSource
import com.example.myapplication.data.datasource.RemoteNotesDataSource
import com.example.myapplication.data.local.dao.NoteDao
import com.example.myapplication.data.repository.NotesRepositoryImpl
import com.example.myapplication.di.qualifiers.LocalDataSource
import com.example.myapplication.di.qualifiers.RemoteDataSource
import com.example.myapplication.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "notes_db_v2"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideNoteDao(db: AppDatabase): NoteDao = db.noteDao()

    @Provides
    @Singleton
    @LocalDataSource
    fun provideLocalDataSource(dao: NoteDao): NotesDataSource =
        LocalNotesDataSource(dao)

    @Provides
    @Singleton
    @RemoteDataSource
    fun provideRemoteDataSource(): NotesDataSource =
        RemoteNotesDataSource()

    @Provides
    @Singleton
    fun provideNoteRepository(
        @LocalDataSource localDataSource: NotesDataSource,
        @RemoteDataSource remoteDataSource: NotesDataSource
    ): NoteRepository =
        NotesRepositoryImpl(localDataSource, remoteDataSource)
}
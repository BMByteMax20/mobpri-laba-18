package com.example.myapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.di.qualifiers.IoDispatcher
import com.example.myapplication.di.qualifiers.MainDispatcher
import com.example.myapplication.domain.usecase.AddNoteUseCase
import com.example.myapplication.domain.usecase.DeleteNoteUseCase
import com.example.myapplication.domain.usecase.GetNotesUseCase
import com.example.myapplication.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val logger: Logger
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun loadNotes() {
        viewModelScope.launch(ioDispatcher) {
            logger.log("Загрузка заметок")
            _uiState.update { it.copy(isLoading = true, error = null) }

            val result = getNotesUseCase()

            withContext(mainDispatcher) {
                if (result.isSuccess) {
                    _uiState.update {
                        it.copy(notes = result.getOrNull() ?: emptyList(), isLoading = false)
                    }
                    logger.log("Загружено ${result.getOrNull()?.size} заметок")
                } else {
                    _uiState.update {
                        it.copy(error = result.exceptionOrNull()?.message, isLoading = false)
                    }
                    logger.log("Ошибка загрузки: ${result.exceptionOrNull()?.message}")
                }
            }
        }
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch(ioDispatcher) {
            logger.log("Добавление заметки: $title")
            _uiState.update { it.copy(isLoading = true, error = null) }

            val result = addNoteUseCase(title, content)

            withContext(mainDispatcher) {
                if (result.isSuccess) {
                    loadNotes()
                    _uiState.update { it.copy(isLoading = false) }
                    logger.log("Заметка успешно добавлена")
                } else {
                    _uiState.update {
                        it.copy(error = result.exceptionOrNull()?.message, isLoading = false)
                    }
                    logger.log("Ошибка добавления: ${result.exceptionOrNull()?.message}")
                }
            }
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch(ioDispatcher) {
            logger.log("Удаление заметки с id: $id")
            _uiState.update { it.copy(isLoading = true, error = null) }

            val result = deleteNoteUseCase(id)

            withContext(mainDispatcher) {
                if (result.isSuccess) {
                    loadNotes()
                    _uiState.update { it.copy(isLoading = false) }
                    logger.log("Заметка успешно удалена")
                } else {
                    _uiState.update {
                        it.copy(error = result.exceptionOrNull()?.message, isLoading = false)
                    }
                    logger.log("Ошибка удаления: ${result.exceptionOrNull()?.message}")
                }
            }
        }
    }
}
package com.example.myapplication.presentation

import com.example.myapplication.domain.model.Note

data class UiState(

    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null

)
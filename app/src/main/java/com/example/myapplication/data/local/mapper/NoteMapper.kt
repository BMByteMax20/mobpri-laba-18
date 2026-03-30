package com.example.myapplication.data.local.mapper

import com.example.myapplication.data.local.entity.NoteEntity
import com.example.myapplication.domain.model.Note

fun NoteEntity.toDomain() = Note(
    id = this.id,
    title = this.title,
    content = this.content,
    createdAt = this.createdAt
)

fun Note.toEntity() = NoteEntity(
    id = this.id,
    title = this.title,
    content = this.content,
    createdAt = this.createdAt
)
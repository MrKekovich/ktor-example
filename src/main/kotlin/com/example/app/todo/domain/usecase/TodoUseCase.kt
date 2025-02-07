package com.example.app.todo.domain.usecase

import com.example.app.todo.application.dto.TodoRequest
import com.example.app.todo.domain.entity.TodoEntity
import java.util.UUID

interface TodoUseCase {
    fun save(
        userId: UUID,
        todoRequest: TodoRequest,
    ): TodoEntity

    fun findByUser(
        userId: UUID,
        isComplete: Boolean? = null,
    ): List<TodoEntity>

    fun update(
        id: UUID,
        userId: UUID,
        request: TodoRequest,
    ): TodoEntity

    fun delete(
        id: UUID,
        userId: UUID,
    )
}

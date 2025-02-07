package com.example.app.todo.domain.repository

import com.example.app.todo.domain.entity.TodoEntity
import java.util.UUID

interface TodoRepository {
    fun save(todoEntity: TodoEntity): TodoEntity

    fun findByUser(
        userId: UUID,
        isComplete: Boolean? = null,
    ): List<TodoEntity>

    fun update(todoEntity: TodoEntity): TodoEntity?

    fun deleteByIdAndUser(
        id: UUID,
        userId: UUID,
    ): Boolean
}

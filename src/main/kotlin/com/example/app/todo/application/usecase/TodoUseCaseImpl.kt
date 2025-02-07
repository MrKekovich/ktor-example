package com.example.app.todo.application.usecase

import com.example.app.todo.application.dto.TodoRequest
import com.example.app.todo.application.violation.TodoViolation
import com.example.app.todo.domain.entity.TodoEntity
import com.example.app.todo.domain.repository.TodoRepository
import com.example.app.todo.domain.usecase.TodoUseCase
import com.example.shared.util.exception.notFound
import com.example.shared.util.exception.requireOrNotFound
import java.util.UUID

class TodoUseCaseImpl(
    private val todoRepository: TodoRepository,
) : TodoUseCase {
    override fun save(
        userId: UUID,
        todoRequest: TodoRequest,
    ): TodoEntity {
        val todoEntity = todoRequest.toEntity(id = UUID.randomUUID(), userId = userId)

        return todoRepository.save(todoEntity)
    }

    override fun findByUser(
        userId: UUID,
        isComplete: Boolean?,
    ): List<TodoEntity> = todoRepository.findByUser(userId, isComplete)

    override fun update(
        id: UUID,
        userId: UUID,
        request: TodoRequest,
    ): TodoEntity {
        val todoEntity =
            request.toEntity(
                id = id,
                userId = userId,
            )

        return todoRepository.update(todoEntity)
            ?: notFound(
                TodoViolation.TodoNotFound(id = id),
            )
    }

    override fun delete(
        id: UUID,
        userId: UUID,
    ): Unit =
        requireOrNotFound(
            todoRepository.deleteByIdAndUser(
                id = id,
                userId = userId,
            ),
        ) {
            TodoViolation.TodoNotFound(id = id)
        }
}

private fun TodoRequest.toEntity(
    id: UUID,
    userId: UUID,
): TodoEntity =
    TodoEntity(
        id = id,
        userId = userId,
        title = title,
        isComplete = isComplete,
        startDate = startDate,
        endDate = endDate,
    )

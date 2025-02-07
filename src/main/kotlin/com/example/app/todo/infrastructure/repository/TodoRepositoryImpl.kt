package com.example.app.todo.infrastructure.repository

import com.example.app.todo.domain.entity.TodoEntity
import com.example.app.todo.domain.repository.TodoRepository
import com.example.app.todo.infrastructure.persistence.TodoDao
import com.example.app.todo.infrastructure.persistence.TodoTable
import com.example.app.user.infrastructure.persistence.UserTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class TodoRepositoryImpl : TodoRepository {
    override fun save(todoEntity: TodoEntity): TodoEntity =
        transaction {
            TodoDao
                .new(
                    todoEntity.id,
                    todoEntity.toDaoClosure(),
                ).toEntity()
        }

    override fun findByUser(
        userId: UUID,
        isComplete: Boolean?,
    ): List<TodoEntity> =
        transaction {
            TodoTable
                .selectAll()
                .where { TodoTable.userId eq userId }
                .applyIsCompleteFilter(isComplete)
                .map { TodoDao.wrapRow(it).toEntity() }
        }

    override fun update(todoEntity: TodoEntity): TodoEntity? =
        transaction {
            TodoDao
                .findByIdAndUpdate(
                    todoEntity.id,
                    todoEntity.toDaoClosure(),
                )?.toEntity()
        }

    override fun deleteByIdAndUser(
        id: UUID,
        userId: UUID,
    ): Boolean =
        transaction {
            TodoTable.deleteWhere {
                it.run {
                    (TodoTable.id eq id) and
                        (TodoTable.userId eq userId)
                }
            } > 0
        }
}

private fun Query.applyIsCompleteFilter(isComplete: Boolean?): Query =
    apply {
        if (isComplete != null) andWhere { TodoTable.isComplete eq isComplete }
    }

private fun TodoDao.toEntity(): TodoEntity =
    TodoEntity(
        id = id.value,
        userId = userId.value,
        title = title,
        isComplete = isComplete,
        startDate = startDate,
        endDate = endDate,
    )

fun TodoEntity.toDaoClosure(): (TodoDao) -> Unit =
    {
        it.userId = EntityID(userId, UserTable)
        it.title = title
        it.isComplete = isComplete
        it.startDate = startDate
        it.endDate = endDate
    }

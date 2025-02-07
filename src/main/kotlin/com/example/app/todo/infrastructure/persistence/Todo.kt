package com.example.app.todo.infrastructure.persistence

import com.example.app.user.infrastructure.persistence.UserTable
import com.example.shared.infrastructure.persistence.timestamp
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import java.util.UUID

object TodoTable : UUIDTable("todos") {
    val userId =
        reference(
            "user_id",
            UserTable,
            onDelete = ReferenceOption.CASCADE,
        )

    val title = varchar("title", 5000)
    val isComplete = bool("is_complete").default(false)

    val startDate = timestamp("start_date").nullable()
    val endDate = timestamp("end_date").nullable()
}

class TodoDao(
    id: EntityID<UUID>,
) : UUIDEntity(id) {
    companion object : UUIDEntityClass<TodoDao>(TodoTable)

    var userId by TodoTable.userId

    var title by TodoTable.title
    var isComplete by TodoTable.isComplete

    var startDate by TodoTable.startDate
    var endDate by TodoTable.endDate
}

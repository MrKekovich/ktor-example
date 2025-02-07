package com.example.app.user.infrastructure.persistence

import com.example.shared.infrastructure.persistence.timestamp
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.UUID

object UserTable : UUIDTable("users") {
    val phoneNumber = varchar("phone_number", 255).uniqueIndex()
    val displayName = varchar("display_name", 255).nullable()
    val createdAt = timestamp("created_at")
}

class UserDao(
    id: EntityID<UUID>,
) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserDao>(UserTable)

    var phoneNumber by UserTable.phoneNumber
    var displayName by UserTable.displayName
    var createdAt by UserTable.createdAt
}

package com.example.app.session.infrastructure.persistence

import com.example.app.user.infrastructure.persistence.UserTable
import com.example.shared.infrastructure.persistence.timestamp
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.UUID

object SessionTable : UUIDTable("sessions") {
    val userId = reference("user_id", UserTable)
    val refreshToken = varchar("refresh_token", 255)
    val userAgent = varchar("user_agent", 255).nullable()
    val ipAddress = varchar("ip_address", 45).nullable()
    val revoked = bool("revoked").default(false)
    val lastUsedAt = timestamp("last_used_at").nullable()
    val createdAt = timestamp("created_at")
    val expiresAt = timestamp("expires_at")
}

class SessionDao(
    id: EntityID<UUID>,
) : UUIDEntity(id) {
    companion object : UUIDEntityClass<SessionDao>(SessionTable)

    var userId by SessionTable.userId
    var refreshToken by SessionTable.refreshToken
    var userAgent by SessionTable.userAgent
    var ipAddress by SessionTable.ipAddress
    var revoked by SessionTable.revoked
    var lastUsedAt by SessionTable.lastUsedAt
    var createdAt by SessionTable.createdAt
    var expiresAt by SessionTable.expiresAt
}

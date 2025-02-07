package com.example.app.session.domain.entity

import java.util.UUID

data class SessionEntity(
    val id: UUID,
    val userId: UUID,
    val refreshToken: String,
    val userAgent: String?,
    val ipAddress: String?,
    val revoked: Boolean,
    val lastUsedAt: Long?,
    val createdAt: Long,
    val expiresAt: Long,
)

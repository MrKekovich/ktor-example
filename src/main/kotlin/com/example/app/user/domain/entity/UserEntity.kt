package com.example.app.user.domain.entity

import com.example.shared.util.serialization.SerializedUUID
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class UserEntity(
    val id: SerializedUUID,
    val phoneNumber: String,
    val displayName: String?,
    val createdAt: Instant,
)

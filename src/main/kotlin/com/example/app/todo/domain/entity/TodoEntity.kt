package com.example.app.todo.domain.entity

import com.example.shared.util.serialization.SerializedUUID
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class TodoEntity(
    val id: SerializedUUID,
    val userId: SerializedUUID,
    val title: String,
    val isComplete: Boolean,
    val startDate: Instant? = null,
    val endDate: Instant? = null,
)

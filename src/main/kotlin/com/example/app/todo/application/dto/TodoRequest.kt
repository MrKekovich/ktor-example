package com.example.app.todo.application.dto

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class TodoRequest(
    val title: String,
    val isComplete: Boolean,
    val startDate: Instant? = null,
    val endDate: Instant? = null,
)

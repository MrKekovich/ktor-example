package com.example.shared.application.dto

import kotlinx.serialization.Serializable

@Serializable
data class RequestWrapper<T>(
    val body: T,
)

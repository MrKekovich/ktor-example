package com.example.shared.application.dto

import kotlinx.serialization.Serializable

@Serializable
data class ResponseWrapper<T>(
    val data: T,
)

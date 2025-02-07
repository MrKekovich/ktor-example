package com.example.app.session.application.dto

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val accessToken: String,
    val accessTokenLifetime: Long,
    val refreshToken: String,
    val refreshTokenLifetime: Long,
)

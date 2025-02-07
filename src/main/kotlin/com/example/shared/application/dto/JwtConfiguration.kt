package com.example.shared.application.dto

import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm

data class JwtConfiguration(
    val subject: String,
    val issuer: String,
    val algorithm: Algorithm,
    val accessTokenLifetime: Long,
    val refreshTokenLifetime: Long,
    val verifier: JWTVerifier,
)

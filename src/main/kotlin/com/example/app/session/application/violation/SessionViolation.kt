package com.example.app.session.application.violation

import com.example.shared.util.validation.violation.PropertyViolation
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement

@Serializable
sealed class SessionViolation(
    override val code: String,
) : PropertyViolation {
    constructor(
        sessionCode: SessionCode,
    ) : this(sessionCode.name)

    override fun toJsonElement(json: Json): JsonElement = json.encodeToJsonElement(this)

    @Serializable
    data class InvalidToken(
        override val message: String = "Token is invalid or expired",
    ) : SessionViolation(SessionCode.INVALID_TOKEN)

    @Serializable
    data class Unauthorized(
        override val message: String = "Unauthorized",
    ) : SessionViolation(SessionCode.UNAUTHORIZED)

    enum class SessionCode {
        INVALID_TOKEN,
        UNAUTHORIZED,
    }
}

package com.example.shared.util.validation.violation

import io.github.kverify.core.violation.Violation
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

interface PropertyViolation : Violation {
    val code: String

    fun toJsonElement(json: Json = Json.Default): JsonElement
}

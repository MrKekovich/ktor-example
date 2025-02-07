package com.example.shared.util.validation.violation

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement

@Serializable
class AnyPropertyViolation(
    override val message: String,
) : PropertyViolation {
    override val code = "UNKNOWN"

    override fun toJsonElement(json: Json): JsonElement = json.encodeToJsonElement(this)
}

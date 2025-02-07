package com.example.app.todo.application.violation

import com.example.shared.util.serialization.SerializedUUID
import com.example.shared.util.validation.violation.PropertyViolation
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement

@Serializable
sealed class TodoViolation(
    override val code: String,
) : PropertyViolation {
    constructor(
        todoViolationCode: TodoViolationCode,
    ) : this(
        todoViolationCode.name,
    )

    override fun toJsonElement(json: Json): JsonElement = json.encodeToJsonElement(this)

    @Serializable
    data class TodoNotFound(
        val id: SerializedUUID,
        override val message: String = "Todo with id='$id' was not found",
    ) : TodoViolation(TodoViolationCode.TODO_NOT_FOUND)

    enum class TodoViolationCode {
        TODO_NOT_FOUND,
    }
}

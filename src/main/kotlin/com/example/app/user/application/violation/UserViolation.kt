package com.example.app.user.application.violation

import com.example.shared.util.serialization.SerializedUUID
import com.example.shared.util.validation.violation.PropertyViolation
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement

@Serializable
sealed class UserViolation(
    override val code: String,
) : PropertyViolation {
    constructor(
        userCode: UserCode,
    ) : this(userCode.name)

    override fun toJsonElement(json: Json): JsonElement = json.encodeToJsonElement(this)

    @Serializable
    data class UserNotFound(
        val id: SerializedUUID,
        override val message: String = "User with id='$id' was not found",
    ) : UserViolation(UserCode.USER_NOT_FOUND)

    enum class UserCode {
        USER_NOT_FOUND,
    }
}

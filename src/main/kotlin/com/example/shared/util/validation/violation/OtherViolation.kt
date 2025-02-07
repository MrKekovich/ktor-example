package com.example.shared.util.validation.violation

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement

@Serializable
sealed class OtherViolation(
    override val code: String,
) : PropertyViolation {
    constructor(
        otherCode: OtherCode,
    ) : this(otherCode.name)

    override fun toJsonElement(json: Json): JsonElement = json.encodeToJsonElement(this)

    @Serializable
    data class ExactlyOneFieldNotNull(
        override val message: String,
    ) : OtherViolation(OtherCode.EXACTLY_ONE_FIELD_NOT_NULL) {
        constructor(
            fieldNames: List<String>? = null,
        ) : this(
            buildString {
                append("Fill exactly one field.")
                if (fieldNames != null) appendFieldNames(fieldNames)
            },
        )
    }

    @Serializable
    data class AtLeastOneFieldNotNull(
        override val message: String,
    ) : OtherViolation(OtherCode.AT_LEAST_ONE_FIELD_NOT_NULL) {
        constructor(
            fieldNames: List<String>? = null,
        ) : this(
            buildString {
                append("Fill at least one field.")
                if (fieldNames != null) appendFieldNames(fieldNames)
            },
        )
    }

    enum class OtherCode {
        EXACTLY_ONE_FIELD_NOT_NULL,
        AT_LEAST_ONE_FIELD_NOT_NULL,
    }
}

private fun StringBuilder.appendFieldNames(
    fieldNames: List<String>,
    prefix: String = " ",
) {
    append(prefix + "Available fields: " + fieldNames.joinToString(", "))
}

package com.example.shared.util.validation.violation

import io.github.kverify.core.model.NamedValue
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement

@Serializable
sealed class StringViolation(
    override val code: String,
) : PropertyViolation {
    constructor(
        stringCode: StringCode,
    ) : this(stringCode.name)

    override fun toJsonElement(json: Json): JsonElement = json.encodeToJsonElement(this)

    @Serializable
    data class NotBlank(
        override val message: String,
    ) : StringViolation(StringCode.NOT_BLANK) {
        constructor(
            namedValue: NamedValue<String>,
        ) : this(
            "'${namedValue.name}' must not be blank",
        )
    }

    @Serializable
    data class Numeric(
        override val message: String,
    ) : StringViolation(StringCode.NUMERIC) {
        constructor(
            namedValue: NamedValue<String>,
        ) : this(
            "'${namedValue.name}' must be numeric",
        )
    }

    @Serializable
    data class OfLength(
        override val message: String,
        val length: Int,
    ) : StringViolation(StringCode.OF_LENGTH) {
        constructor(
            namedValue: NamedValue<String>,
            length: Int,
        ) : this(
            "'${namedValue.name}' must be of length $length",
            length,
        )
    }

    @Serializable
    data class LengthBetween(
        override val message: String,
        val min: Int,
        val max: Int,
    ) : StringViolation(StringCode.LENGTH_BETWEEN) {
        constructor(
            namedValue: NamedValue<String>,
            range: IntRange,
        ) : this(
            "'${namedValue.name}' must be of length in range $range",
            range.first,
            range.last,
        )
    }

    @Serializable
    data class MatchesPhoneNumber(
        override val message: String,
    ) : StringViolation(StringCode.NUMERIC) {
        constructor(
            namedValue: NamedValue<String>,
        ) : this(
            "'${namedValue.name}' must be a number",
        )
    }

    enum class StringCode {
        NOT_BLANK,
        NUMERIC,
        OF_LENGTH,
        LENGTH_BETWEEN,
    }
}

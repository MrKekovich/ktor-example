package com.example.shared.util.validation.violation

import io.github.kverify.core.model.NamedValue
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement

@Serializable
sealed class ComparableViolation(
    override val code: String,
) : PropertyViolation {
    constructor(
        comparableCode: ComparableCode,
    ) : this(comparableCode.name)

    override fun toJsonElement(json: Json): JsonElement = json.encodeToJsonElement(this)

    @Serializable
    data class EqualTo<C : Comparable<C>>(
        override val message: String,
        val other: C,
    ) : ComparableViolation(ComparableCode.EQUAL_TO) {
        constructor(
            namedValue: NamedValue<C>,
            other: C,
        ) : this(
            "'${namedValue.name}' must be equal to $other",
            other,
        )
    }

    @Serializable
    data class LessThanOrEqualTo<C : Comparable<C>>(
        override val message: String,
        val other: C,
    ) : ComparableViolation(ComparableCode.EQUAL_TO) {
        constructor(
            namedValue: NamedValue<C>,
            other: C,
        ) : this(
            "'${namedValue.name}' must be less than or equal to $other",
            other,
        )
    }

    @Serializable
    data class LessThan<C : Comparable<C>>(
        val other: C,
        override val message: String,
    ) : ComparableViolation(ComparableCode.EQUAL_TO) {
        constructor(
            namedValue: NamedValue<C>,
            other: C,
        ) : this(
            other,
            "'${namedValue.name}' must be less than $other",
        )
    }

    @Serializable
    data class GreaterThan<C : Comparable<C>>(
        val other: C,
        override val message: String,
    ) : ComparableViolation(ComparableCode.GREATER_THAN) {
        constructor(
            namedValue: NamedValue<C>,
            other: C,
        ) : this(
            other,
            "'${namedValue.name}' must be greater than $other",
        )
    }

    @Serializable
    data class GreaterThanOrEqualTo<C : Comparable<C>>(
        override val message: String,
        val other: C,
    ) : ComparableViolation(ComparableCode.GREATER_THAN_EQUAL_TO) {
        constructor(
            namedValue: NamedValue<C>,
            other: C,
        ) : this(
            "'${namedValue.name}' must be greater than or equal to $other",
            other,
        )
    }

    enum class ComparableCode {
        EQUAL_TO,
        GREATER_THAN_EQUAL_TO,
        GREATER_THAN,
    }
}

package com.example.shared.util.validation.violation

import io.github.kverify.core.model.NamedValue
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement

@Serializable
sealed class CollectionViolation(
    override val code: String,
) : PropertyViolation {
    constructor(
        collectionCode: CollectionCode,
    ) : this(collectionCode.name)

    override fun toJsonElement(json: Json): JsonElement = json.encodeToJsonElement(this)

    @Serializable
    data class NotEmpty(
        override val message: String,
    ) : CollectionViolation(CollectionCode.NOT_EMPTY)

    @Serializable
    data class Distinct(
        override val message: String,
    ) : CollectionViolation(CollectionCode.DISTINCT)

    enum class CollectionCode {
        NOT_EMPTY,
        DISTINCT,
    }

    // Companion object methods were added to avoid using generics in the primary sealed class
    // and data classes, as they're not always necessary. Generics are handled only when needed.
    companion object {
        fun <C : Collection<*>> NotEmpty(namedValue: NamedValue<C>): NotEmpty =
            NotEmpty(
                "'${namedValue.name}' must not be empty",
            )

        fun <C : Collection<*>> Distinct(namedValue: NamedValue<C>): Distinct =
            Distinct(
                "'${namedValue.name}' must be distinct",
            )
    }
}

package com.example.shared.util.serialization

import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.util.UUID

typealias SerializedUUID =
    @Serializable(UUIDSerializer::class)
    UUID

typealias SerializedBigDecimal =
    @Serializable(BigDecimalSerializer::class)
    BigDecimal

typealias SerializedBooleanString =
    @Serializable(BooleanStringSerializer::class)
    Boolean

package com.example.shared.util.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object BooleanStringSerializer : KSerializer<Boolean> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("BooleanString", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Boolean {
        // Decode the value as a String, then convert to Boolean
        return when (val value = decoder.decodeString().lowercase()) {
            "true" -> true
            "false" -> false
            else -> throw SerializationException("Invalid value for Boolean: $value")
        }
    }

    override fun serialize(
        encoder: Encoder,
        value: Boolean,
    ) {
        // Encode the Boolean value as a String
        encoder.encodeString(value.toString())
    }
}

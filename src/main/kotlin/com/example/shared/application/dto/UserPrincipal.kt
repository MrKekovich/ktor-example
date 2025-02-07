package com.example.shared.application.dto

import com.example.shared.util.serialization.SerializedUUID
import io.ktor.server.auth.Principal
import kotlinx.serialization.Serializable

@Serializable
data class UserPrincipal(
    val id: SerializedUUID,
) : Principal {
    enum class FieldNames {
        ID,
    }
}

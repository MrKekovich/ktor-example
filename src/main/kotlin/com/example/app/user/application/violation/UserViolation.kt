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

    @Serializable
    data class NotEnoughEnergy(
        val requiredEnergy: Int,
        val userId: SerializedUUID? = null,
        override val message: String = notEnoughEnergyMessage(requiredEnergy, userId),
    ) : UserViolation(UserCode.NOT_ENOUGH_ENERGY)

    @Serializable
    data class NotEnoughBusinessBalance(
        val requiredBalance: Int,
        val userId: SerializedUUID? = null,
        override val message: String = notEnoughBusinessBalanceMessage(requiredBalance, userId),
    ) : UserViolation(UserCode.NOT_ENOUGH_BUSINESS_BALANCE)

    @Serializable
    data class NotEnoughPersonalBalance(
        val requiredBalance: Int,
        val id: SerializedUUID? = null,
        override val message: String = notEnoughPersonalBalanceMessage(requiredBalance, id),
    ) : UserViolation(UserCode.NOT_ENOUGH_PERSONAL_BALANCE)

    enum class UserCode {
        NOT_ENOUGH_ENERGY,
        NOT_ENOUGH_BUSINESS_BALANCE,
        USER_NOT_FOUND,
        NOT_ENOUGH_PERSONAL_BALANCE,
    }
}

private fun notEnoughEnergyMessage(
    requiredEnergy: Int,
    userId: SerializedUUID?,
): String =
    if (userId != null) {
        "User with id '$userId' has not enough energy ($requiredEnergy)"
    } else {
        "User has not enough energy ($requiredEnergy)"
    }

private fun notEnoughBusinessBalanceMessage(
    requiredBalance: Int,
    userId: SerializedUUID?,
): String =
    if (userId != null) {
        "User with id '$userId' has not enough business balance ($requiredBalance)"
    } else {
        "User has not enough business balance ($requiredBalance)"
    }

private fun notEnoughPersonalBalanceMessage(
    requiredBalance: Int,
    userId: SerializedUUID?,
): String =
    if (userId != null) {
        "User with id '$userId' has not enough personal balance ($requiredBalance)"
    } else {
        "User has not enough personal balance ($requiredBalance)"
    }

package com.example.app.user.domain.entity

import com.example.shared.application.dto.CashAndEnergy
import com.example.shared.util.serialization.SerializedUUID
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class UserEntity(
    val id: SerializedUUID,
    val phoneNumber: String,
    val displayName: String?,
    val energyLevel: Int,
    val businessBalance: Int,
    val personalBalance: Int,
    val createdAt: Instant,
)

fun UserEntity.minusCashAndEnergy(cashAndEnergy: CashAndEnergy): UserEntity =
    this.copy(
        businessBalance = this.businessBalance - cashAndEnergy.cash,
        energyLevel = this.energyLevel - cashAndEnergy.energy,
    )

package com.example.shared.application.dto

import kotlinx.serialization.Serializable

@Serializable
data class CashAndEnergy(
    val cash: Int = 0,
    val energy: Int = 0,
)

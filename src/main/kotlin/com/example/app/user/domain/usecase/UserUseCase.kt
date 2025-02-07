package com.example.app.user.domain.usecase

import com.example.app.user.application.dto.UpdateUserRequest
import com.example.app.user.domain.entity.UserEntity
import java.util.UUID

interface UserUseCase {
    fun findById(id: UUID): UserEntity

    fun update(
        userId: UUID,
        request: UpdateUserRequest,
    ): UserEntity

    fun addCash(userId: UUID): UserEntity

    fun addEnergy(userId: UUID): UserEntity
}

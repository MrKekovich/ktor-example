package com.example.app.user.domain.repository

import com.example.app.user.domain.entity.UserEntity
import java.util.UUID

interface UserRepository {
    fun findById(id: UUID): UserEntity?

    fun findByPhoneNumber(phoneNumber: String): UserEntity?

    fun save(entity: UserEntity): UserEntity

    fun update(
        id: UUID,
        update: (UserEntity) -> UserEntity,
    ): UserEntity?

    fun update(entity: UserEntity): UserEntity?

    fun delete(id: UUID): Boolean

    fun exists(id: UUID): Boolean
}

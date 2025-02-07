package com.example.app.user.infrastructure.repository

import com.example.app.user.domain.entity.UserEntity
import com.example.app.user.domain.repository.UserRepository
import com.example.app.user.infrastructure.persistence.UserDao
import com.example.app.user.infrastructure.persistence.UserTable
import com.example.shared.util.ext.exists
import com.example.shared.util.ext.firstOrNull
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class UserRepositoryImpl : UserRepository {
    override fun findById(id: UUID): UserEntity? =
        transaction {
            UserDao.findById(id)?.toEntity()
        }

    override fun findByPhoneNumber(phoneNumber: String): UserEntity? =
        transaction {
            UserDao
                .firstOrNull {
                    UserTable.phoneNumber eq phoneNumber
                }?.toEntity()
        }

    override fun save(entity: UserEntity): UserEntity =
        transaction {
            UserDao
                .new(entity.id) {
                    apply(entityToDaoClosure(entity))
                    createdAt = entity.createdAt
                }.toEntity()
        }

    override fun update(
        id: UUID,
        update: (UserEntity) -> UserEntity,
    ): UserEntity? =
        transaction {
            UserDao
                .findByIdAndUpdate(id) {
                    val newEntity = update(it.toEntity())
                    it.apply(entityToDaoClosure(newEntity))
                }?.toEntity()
        }

    override fun update(entity: UserEntity): UserEntity? =
        transaction {
            UserDao
                .findByIdAndUpdate(
                    entity.id,
                    entityToDaoClosure(entity),
                )?.toEntity()
        }

    override fun delete(id: UUID): Boolean =
        transaction {
            UserDao.findById(id)?.delete() != null
        }

    override fun exists(id: UUID): Boolean =
        transaction {
            UserDao.exists(id)
        }
}

private fun entityToDaoClosure(entity: UserEntity): UserDao.() -> Unit =
    {
        phoneNumber = entity.phoneNumber
        displayName = entity.displayName
        // createdAt must be set explicitly
    }

private fun UserDao.toEntity(): UserEntity =
    UserEntity(
        id = id.value,
        phoneNumber = phoneNumber,
        displayName = displayName,
        createdAt = createdAt,
    )

package com.example.app.user.application.usecase

import com.example.app.user.application.dto.UpdateUserRequest
import com.example.app.user.application.violation.UserViolation
import com.example.app.user.domain.entity.UserEntity
import com.example.app.user.domain.repository.UserRepository
import com.example.app.user.domain.usecase.UserUseCase
import com.example.shared.util.exception.notFound
import java.util.UUID

class UserUseCaseImpl(
    private val userRepository: UserRepository,
) : UserUseCase {
    override fun findById(id: UUID): UserEntity =
        userRepository.findById(id)
            ?: notFound(UserViolation.UserNotFound(id))

    override fun update(
        userId: UUID,
        request: UpdateUserRequest,
    ): UserEntity =
        userRepository.update(userId) {
            it.copy(
                // TODO: Add safety restrictions for phone number update.
                phoneNumber = request.phoneNumber ?: it.phoneNumber,
                displayName = request.displayName ?: it.displayName,
            )
        } ?: notFound(UserViolation.UserNotFound(userId))
}

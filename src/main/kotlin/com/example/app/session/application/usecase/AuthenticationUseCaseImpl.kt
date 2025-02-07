package com.example.app.session.application.usecase

import com.auth0.jwt.JWT
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import com.example.app.session.application.dto.LoginRequest
import com.example.app.session.application.dto.RefreshRequest
import com.example.app.session.application.dto.TokenResponse
import com.example.app.session.application.violation.SessionViolation
import com.example.app.session.domain.usecase.AuthenticationUseCase
import com.example.app.user.application.violation.UserViolation
import com.example.app.user.domain.entity.UserEntity
import com.example.app.user.domain.repository.UserRepository
import com.example.shared.application.dto.JwtConfiguration
import com.example.shared.application.dto.UserPrincipal
import com.example.shared.domain.valueobject.TokenType
import com.example.shared.util.exception.forbidden
import com.example.shared.util.ext.UserPrincipal
import com.example.shared.util.ext.getClaim
import com.example.shared.util.ext.signWithJwtConfiguration
import com.example.shared.util.ext.toUuidOrNull
import com.example.shared.util.ext.withUserId
import com.example.shared.util.ext.withUserPrincipal
import kotlinx.datetime.Clock
import java.util.UUID

// TODO: Add code checks, sessions.
class AuthenticationUseCaseImpl(
    private val userRepository: UserRepository,
    private val jwtConfiguration: JwtConfiguration,
) : AuthenticationUseCase {
    override fun login(rq: LoginRequest): TokenResponse {
        val userEntity =
            userRepository.findByPhoneNumber(rq.phoneNumber)
                ?: createDefaultUser(rq.phoneNumber)

        return generateTokensFromEntity(userEntity)
    }

    override fun refreshToken(rq: RefreshRequest): TokenResponse =
        try {
            val decodedRefreshToken: DecodedJWT = jwtConfiguration.verifier.verify(rq.refreshToken)

            val userId =
                decodedRefreshToken.getClaim(UserPrincipal.FieldNames.ID)?.toUuidOrNull()
                    ?: error(SessionViolation.InvalidToken())

            val userEntity =
                userRepository.findById(userId)
                    ?: forbidden(UserViolation.UserNotFound(userId))

            generateTokensFromEntity(userEntity)
        } catch (_: JWTVerificationException) {
            forbidden(SessionViolation.InvalidToken())
        }

    private fun generateTokensFromEntity(userEntity: UserEntity): TokenResponse {
        val accessToken = createToken(userEntity, TokenType.ACCESS)
        val refreshToken = createToken(userEntity, TokenType.REFRESH)

        return TokenResponse(
            accessToken = accessToken,
            accessTokenLifetime = jwtConfiguration.accessTokenLifetime,
            refreshToken = refreshToken,
            refreshTokenLifetime = jwtConfiguration.refreshTokenLifetime,
        )
    }

    private fun createToken(
        userEntity: UserEntity,
        tokenType: TokenType,
    ): String =
        JWT
            .create()
            .apply {
                when (tokenType) {
                    TokenType.ACCESS -> withUserPrincipal(UserPrincipal(userEntity))
                    TokenType.REFRESH -> withUserId(userEntity.id)
                }
            }.signWithJwtConfiguration(jwtConfiguration, tokenType)

    private fun createDefaultUser(phoneNumber: String): UserEntity =
        userRepository
            .save(
                UserEntity(phoneNumber = phoneNumber),
            )
}

private fun UserEntity(phoneNumber: String): UserEntity =
    UserEntity(
        id = UUID.randomUUID(),
        phoneNumber = phoneNumber,
        displayName = null,
        createdAt = Clock.System.now(),
    )

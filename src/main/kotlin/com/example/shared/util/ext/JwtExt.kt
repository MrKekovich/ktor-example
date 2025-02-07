package com.example.shared.util.ext

import com.auth0.jwt.JWTCreator
import com.example.shared.application.dto.JwtConfiguration
import com.example.shared.application.dto.UserPrincipal
import com.example.shared.domain.valueobject.TokenType
import kotlinx.datetime.Clock
import kotlinx.datetime.toJavaInstant
import java.util.UUID
import kotlin.time.Duration.Companion.milliseconds

fun JWTCreator.Builder.withUserPrincipal(userPrincipal: UserPrincipal): JWTCreator.Builder =
    this
        .withClaim(UserPrincipal.FieldNames.ID, userPrincipal.id)
//        .withClaim(TODO("Add more claims") as String, "")

fun JWTCreator.Builder.withUserId(id: UUID): JWTCreator.Builder =
    this
        .withClaim(UserPrincipal.FieldNames.ID, id)

fun JWTCreator.Builder.signWithJwtConfiguration(
    jwtConfiguration: JwtConfiguration,
    tokenType: TokenType = TokenType.ACCESS,
): String {
    val expirationMs =
        when (tokenType) {
            TokenType.REFRESH -> jwtConfiguration.refreshTokenLifetime
            TokenType.ACCESS -> jwtConfiguration.accessTokenLifetime
        }.milliseconds
    val expirationDate = Clock.System.now() + expirationMs

    return this
        .withSubject(jwtConfiguration.subject)
        .withIssuer(jwtConfiguration.issuer)
        .withExpiresAt(expirationDate.toJavaInstant())
        .sign(jwtConfiguration.algorithm)
}

private fun JWTCreator.Builder.withClaim(
    key: UserPrincipal.FieldNames,
    value: Any,
): JWTCreator.Builder =
    this
        .withClaim(key.name, value.toString())

package com.example.shared.util.koin

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.app.session.application.dto.DefaultUserConfiguration
import com.example.shared.application.dto.JwtConfiguration
import org.koin.core.Koin

fun Koin.injectJwtConfiguration(): JwtConfiguration {
    val algorithm =
        getProperty<String>(ApplicationConfiguration.JWT_SECRET)
            .let { Algorithm.HMAC256(it) }

    val verifier =
        JWT
            .require(algorithm)
            .withIssuer(getProperty(ApplicationConfiguration.JWT_ISSUER))
            .build()

    return JwtConfiguration(
        subject = getProperty(ApplicationConfiguration.JWT_SUBJECT),
        issuer = getProperty(ApplicationConfiguration.JWT_ISSUER),
        algorithm = algorithm,
        accessTokenLifetime = getProperty(ApplicationConfiguration.JWT_ACCESS_TOKEN_LIFETIME),
        refreshTokenLifetime = getProperty(ApplicationConfiguration.JWT_REFRESH_TOKEN_LIFETIME),
        verifier = verifier,
    )
}

// TODO: Remove hardcoded values
fun Koin.injectDefaultUserConfiguration(): DefaultUserConfiguration =
    DefaultUserConfiguration(
        energyLevel = 1000, // TODO: set to 100
        businessBalance = 60000,
        personalBalance = 10000,
    )

fun <T> Koin.getProperty(key: ApplicationConfiguration): T =
    getProperty(key.name)
        ?: error("Property ${key.name} was not found")

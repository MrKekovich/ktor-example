package com.example.configuration

import com.example.shared.application.dto.Validatable
import io.github.kverify.core.model.throwOnFailure
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun Application.configureValidation() {
    install(RequestValidation) {
        validate<Validatable> { request ->
            request.validate().throwOnFailure()
            ValidationResult.Valid
        }
    }
}

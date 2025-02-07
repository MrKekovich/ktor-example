package com.example.shared.util.ext

import com.example.shared.application.dto.Validatable
import io.ktor.server.plugins.requestvalidation.ValidationResult

/**
 * Extract [ValidationResult] from [Validatable]
 *
 * @return [ValidationResult]
 */
fun Validatable.validationResult(): ValidationResult {
    val violationMessages = validate().violations

    return if (violationMessages.isEmpty()) {
        ValidationResult.Valid
    } else {
        ValidationResult.Invalid(violationMessages.map { it.message })
    }
}

package com.example.shared.application.dto

import io.github.kverify.core.model.ValidationResult

/**
 * If request implements this interface, it will be automatically validated, using [validate] method
 *
 * If [validate] returns empty list, it means that request is valid
 */
interface Validatable {
    fun validate(): ValidationResult
}

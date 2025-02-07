package com.example.shared.exception

import com.example.shared.util.validation.violation.PropertyViolation

/**
 * Unauthorized exception
 *
 * Must be thrown when user is not authenticated
 * or authentication is invalid.
 *
 * @property message The message of the exception.
 */
class UnauthorizedException(
    violation: PropertyViolation,
) : TypedException(violation)

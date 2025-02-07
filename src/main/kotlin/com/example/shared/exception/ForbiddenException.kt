package com.example.shared.exception

import com.example.shared.util.validation.violation.PropertyViolation

/**
 * Forbidden exception
 *
 * Must be thrown when user is authenticated but does not have
 * the required permissions.
 *
 * @property message The message of the exception.
 */
class ForbiddenException(
    violation: PropertyViolation,
) : TypedException(violation)

package com.example.shared.util.exception

import com.example.shared.exception.BadRequestException
import com.example.shared.exception.ForbiddenException
import com.example.shared.exception.NotFoundException
import com.example.shared.exception.UnauthorizedException
import com.example.shared.util.validation.violation.PropertyViolation
import io.github.kverify.core.exception.ValidationException
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Throws [BadRequestException]
 *
 * @param violation the [PropertyViolation] type
 * @throws BadRequestException
 */
fun badRequest(violation: PropertyViolation): Nothing = throw BadRequestException(violation)

/**
 * Throws [NotFoundException]
 *
 * @param violation the [PropertyViolation] type
 * @throws NotFoundException
 */
fun notFound(violation: PropertyViolation): Nothing = throw NotFoundException(violation)

/**
 * Throws [ForbiddenException]
 *
 * @param violation the [PropertyViolation] type
 * @throws ForbiddenException
 */
fun forbidden(violation: PropertyViolation): Nothing = throw ForbiddenException(violation)

/**
 * Throws [UnauthorizedException]
 *
 * @param violation the [PropertyViolation] type
 * @throws UnauthorizedException
 */
fun unauthorized(violation: PropertyViolation): Nothing = throw UnauthorizedException(violation)

// Contracts allow us to communicate with the compiler,
// specifying that the given boolean expression has been evaluated correctly.
// This enables the use of smart casts with these functions.

/**
 * Throws [NotFoundException] if [value] is `false`
 *
 * @param value The condition.
 * @param lazyViolation The message of the exception, that will be evaluated lazily.
 * @throws NotFoundException
 */
@OptIn(ExperimentalContracts::class)
inline fun requireOrNotFound(
    value: Boolean,
    lazyViolation: () -> PropertyViolation,
) {
    contract {
        returns() implies (value)
    }

    if (!value) {
        val codeViolation = lazyViolation()
        throw NotFoundException(codeViolation)
    }
}

/**
 * Throws [BadRequestException] if [value] is `false`
 *
 * @param value The condition.
 * @param lazyViolation The message of the exception, that will be evaluated lazily.
 * @throws BadRequestException
 */
@OptIn(ExperimentalContracts::class)
inline fun requireOrBadRequest(
    value: Boolean,
    lazyViolation: () -> PropertyViolation,
) {
    contract {
        returns() implies (value)
    }

    if (!value) {
        val codeViolation = lazyViolation()
        throw ValidationException(codeViolation)
    }
}

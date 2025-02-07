package com.example.shared.util.ext

import com.example.shared.util.exception.badRequest
import com.example.shared.util.validation.violation.AnyPropertyViolation
import io.ktor.http.Parameters
import java.util.UUID

fun Parameters.getUuidOrThrow(parameterName: String): UUID? =
    getUuid(parameterName)?.getOrElse {
        badRequest(AnyPropertyViolation("$parameterName must be UUID"))
    }

fun Parameters.getUuid(parameterName: String): Result<UUID>? =
    get(parameterName)?.let {
        runCatching { UUID.fromString(it) }
    }

fun Parameters.getLong(parameterName: String): Result<Long>? =
    get(parameterName)?.let {
        runCatching { it.toLong() }
    }

fun Parameters.getLongOrThrow(parameterName: String): Long? =
    getLong(parameterName)?.getOrElse {
        badRequest(AnyPropertyViolation("$parameterName must be Long"))
    }

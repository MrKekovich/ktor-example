package com.example.configuration

import com.example.shared.application.dto.ErrorResponse
import com.example.shared.exception.BadRequestException
import com.example.shared.exception.ForbiddenException
import com.example.shared.exception.NotFoundException
import com.example.shared.exception.UnauthorizedException
import com.example.shared.util.validation.violation.AnyPropertyViolation
import com.example.shared.util.validation.violation.PropertyViolation
import io.github.kverify.core.exception.ValidationException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import org.apache.http.auth.AuthenticationException
import org.slf4j.LoggerFactory
import io.ktor.server.plugins.BadRequestException as KtorBadRequestException

private val statusPagesLogger = LoggerFactory.getLogger("StatusPages")

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            cause.printStackTrace()

            call.respondError(
                ErrorResponse<Unit>(
                    status = HttpStatusCode.InternalServerError.value,
                    message = cause.message,
                ),
            )
        }

        exception<BadRequestException> { call, cause ->
            call.respondError(
                ErrorResponse(
                    HttpStatusCode.BadRequest.value,
                    info = cause.violation.toJsonElement(),
                ),
            )
        }

        exception<KtorBadRequestException> { call, cause ->
            call.respondError(
                ErrorResponse<Unit>(
                    HttpStatusCode.BadRequest.value,
                    message = cause.message,
                ),
            )
        }

//  TODO: Enable when validation will be implemented
//        exception<RequestValidationException> { call, cause ->
//            call.respondError(
//                ValidationErrorResponse(
//
//                ),
//            )
//        }

        exception<NotFoundException> { call, cause ->
            call.respondError(
                ErrorResponse(
                    status = HttpStatusCode.NotFound.value,
                    info = cause.violation.toJsonElement(),
                ),
            )
        }

        exception<AuthenticationException> { call, cause ->
            call.respondError(
                ErrorResponse<Unit>(
                    HttpStatusCode.Unauthorized.value,
                    message = cause.message,
                ),
            )
        }

        exception<UnauthorizedException> { call, cause ->
            call.respondError(
                ErrorResponse(
                    status = HttpStatusCode.NotFound.value,
                    info = cause.violation.toJsonElement(),
                ),
            )
        }

        exception<ForbiddenException> { call, cause ->
            call.respondError(
                ErrorResponse(
                    status = HttpStatusCode.NotFound.value,
                    info = cause.violation.toJsonElement(),
                ),
            )
        }

        @Suppress("UNCHECKED_CAST")
        exception<ValidationException> { call, cause ->
            val (codeViolations, unknownViolations) =
                cause.violations.partition {
                    it is PropertyViolation
                }

            val violations =
                if (unknownViolations.isNotEmpty()) {
                    statusPagesLogger.atWarn().log("Some violations are not CodeViolation")
                    codeViolations + unknownViolations.map { AnyPropertyViolation(it.message) }
                } else {
                    codeViolations
                } as List<PropertyViolation>

            call.respondError(
                ErrorResponse(
                    status = HttpStatusCode.BadRequest.value,
                    info = violations.map { it.toJsonElement() },
                ),
            )
        }
    }
}

private suspend inline fun <reified T> ApplicationCall.respondError(errorResponse: ErrorResponse<T>): Unit =
    respond(
        errorResponse.httpStatus,
        errorResponse,
    )

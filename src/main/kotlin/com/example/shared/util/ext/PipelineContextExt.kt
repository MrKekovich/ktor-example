package com.example.shared.util.ext

import com.example.app.session.application.violation.SessionViolation
import com.example.shared.application.dto.UserPrincipal
import com.example.shared.exception.UnauthorizedException
import com.example.shared.util.exception.unauthorized
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.auth.principal
import io.ktor.server.plugins.BadRequestException
import io.ktor.util.pipeline.PipelineContext
import java.util.UUID

private typealias PipelineContextWithCall = PipelineContext<Unit, ApplicationCall>

/**
 * Get [UserPrincipal] from the [ApplicationCall] or throw [UnauthorizedException]
 *
 * @return [UserPrincipal] if present
 * @throws [UnauthorizedException] if principal is not present
 */
fun PipelineContextWithCall.getUserPrincipalOrThrow(): UserPrincipal =
    getUserPrincipal()
        ?: unauthorized(SessionViolation.Unauthorized())

/**
 * Get [UserPrincipal] from the [ApplicationCall]
 *
 * @return [UserPrincipal] or `null` if principal is not present
 */
fun PipelineContextWithCall.getUserPrincipal(): UserPrincipal? = call.principal<UserPrincipal>()

/**
 * Get [UUID] from the path parameters.
 *
 * @param parameterName the name of the path parameter
 * @return [UUID] or `null` if parameter is not present, wrapped in [Result]
 */
fun PipelineContextWithCall.getUuid(parameterName: String): Result<UUID>? = call.parameters.getUuid(parameterName)

/**
 * Get [UUID] from the path parameters or throw [BadRequestException]
 *
 * @param parameterName the name of the path parameter
 * @return [UUID] or `null` if parameter is not present
 * @throws [BadRequestException] if parameter is not of [UUID] type
 */
fun PipelineContextWithCall.getUuidOrThrow(parameterName: String): UUID? = call.parameters.getUuidOrThrow(parameterName)

/**
 * Get [Long] from the path parameters or throw [BadRequestException]
 *
 * @param parameterName the name of the path parameter
 * @return [Long] or `null` if parameter is not present
 * @throws [BadRequestException] if parameter is not of [Long] type
 */
fun PipelineContextWithCall.getLongOrThrow(parameterName: String): Long? = call.parameters.getLongOrThrow(parameterName)

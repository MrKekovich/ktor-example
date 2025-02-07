package com.example.app.session.application.route

import com.example.app.session.application.dto.LoginRequest
import com.example.app.session.application.dto.RefreshRequest
import com.example.app.session.application.dto.TokenResponse
import com.example.app.session.domain.usecase.AuthenticationUseCase
import com.example.shared.util.ext.requestBody
import com.example.shared.util.ext.successResponse
import io.github.smiley4.ktorswaggerui.dsl.routing.post
import io.github.smiley4.ktorswaggerui.dsl.routing.route
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject

const val SESSION_TAG = "Session"

fun Route.sessionRoutes() {
    val authenticationUseCase by inject<AuthenticationUseCase>()

    route("/sessions", {
        tags(SESSION_TAG)
        description = "Session routes"
    }) {
        post("/login", {
            description = "Log in with phone number and code"
            requestBody<LoginRequest>()
            successResponse<TokenResponse>()
        }) {
            val request = call.receive<LoginRequest>()

            val response = authenticationUseCase.login(request)

            call.respond(response)
        }

        post("/refresh-token", {
            description = "Refresh access token"
            requestBody<RefreshRequest>()
            successResponse<TokenResponse>()
        }) {
            val request = call.receive<RefreshRequest>()

            val response = authenticationUseCase.refreshToken(request)

            call.respond(response)
        }
    }
}

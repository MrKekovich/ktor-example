package com.example.app.user.application.route

import com.example.app.user.application.dto.UpdateUserRequest
import com.example.app.user.domain.entity.UserEntity
import com.example.app.user.domain.usecase.UserUseCase
import com.example.shared.util.ext.getUserPrincipalOrThrow
import com.example.shared.util.ext.requestBody
import com.example.shared.util.ext.successResponse
import io.github.smiley4.ktorswaggerui.dsl.routing.get
import io.github.smiley4.ktorswaggerui.dsl.routing.patch
import io.github.smiley4.ktorswaggerui.dsl.routing.post
import io.github.smiley4.ktorswaggerui.dsl.routing.route
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject

const val USER_TAG = "User"

fun Route.userRoutes() {
    val userUseCase by inject<UserUseCase>()

    route("/users", {
        tags(USER_TAG)
        description = "User routes"
    }) {
        route("/me", {
            description = "Current user routes"
        }) {
            get({
                description = "Get current user"
                successResponse<UserEntity>()
            }) {
                val userId = getUserPrincipalOrThrow().id

                val response = userUseCase.findById(userId)

                call.respond(response)
            }

            patch({
                description = "Update current user"
                requestBody<UpdateUserRequest>()
                successResponse<UserEntity>()
            }) {
                val userId = getUserPrincipalOrThrow().id

                val request = call.receive<UpdateUserRequest>()

                val response = userUseCase.update(userId, request)

                call.respond(response)
            }

            post("/add-cash", {
                description = "Add 5000 cash (FOR TESTS ONLY)"
                successResponse<UserEntity>()
            }) {
                val userId = getUserPrincipalOrThrow().id

                val response = userUseCase.addCash(userId)

                call.respond(response)
            }

            post("/add-energy", {
                description = "Add 10 energy (FOR TESTS ONLY)"
                successResponse<UserEntity>()
            }) {
                val userId = getUserPrincipalOrThrow().id

                val response = userUseCase.addEnergy(userId)

                call.respond(response)
            }
        }
    }
}

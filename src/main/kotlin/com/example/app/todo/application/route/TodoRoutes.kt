package com.example.app.todo.application.route

import com.example.app.todo.application.dto.TodoRequest
import com.example.app.todo.domain.entity.TodoEntity
import com.example.app.todo.domain.usecase.TodoUseCase
import com.example.shared.util.ext.getUserPrincipalOrThrow
import com.example.shared.util.ext.getUuidOrThrow
import com.example.shared.util.ext.notFoundResponse
import com.example.shared.util.ext.pathParameter
import com.example.shared.util.ext.queryParameter
import com.example.shared.util.ext.requestBody
import com.example.shared.util.ext.successResponse
import io.github.smiley4.ktorswaggerui.dsl.routing.delete
import io.github.smiley4.ktorswaggerui.dsl.routing.get
import io.github.smiley4.ktorswaggerui.dsl.routing.post
import io.github.smiley4.ktorswaggerui.dsl.routing.put
import io.github.smiley4.ktorswaggerui.dsl.routing.route
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject
import java.util.UUID

const val TODO_TAG = "Todo"

fun Route.todoRoutes() {
    val todoUseCase by inject<TodoUseCase>()

    route("/todos", {
        tags(TODO_TAG)
    }) {
        get({
            summary = "Get all TODOs for the current user"
            queryParameter<Boolean>("isComplete")
            successResponse<List<TodoEntity>>()
        }) {
            val userId = getUserPrincipalOrThrow().id
            val isComplete = call.parameters["isComplete"]?.toBooleanStrictOrNull()

            val response = todoUseCase.findByUser(userId, isComplete)

            call.respond(response)
        }

        post({
            summary = "Create a new TODO for the current user"
            requestBody<TodoRequest>()
            successResponse<TodoEntity>()
        }) {
            val userId = getUserPrincipalOrThrow().id

            val request = call.receive<TodoRequest>()

            val response = todoUseCase.save(userId, request)

            call.respond(response)
        }

        route("/{todoId}", {
            pathParameter<UUID>("todoId")
        }) {
            put({
                summary = "Update existing TODO by its id"
                requestBody<TodoRequest>()
                successResponse<TodoEntity>()
                notFoundResponse()
            }) {
                val userId = getUserPrincipalOrThrow().id

                val id = getUuidOrThrow("todoId")!!
                val request = call.receive<TodoRequest>()

                val response =
                    todoUseCase.update(
                        id = id,
                        userId = userId,
                        request = request,
                    )

                call.respond(response)
            }

            delete({
                summary = "Delete TODO by its id"
                successResponse<Unit>()
                notFoundResponse()
            }) {
                val userId = getUserPrincipalOrThrow().id

                val id = getUuidOrThrow("todoId")!!

                val response =
                    todoUseCase.delete(
                        id = id,
                        userId = userId,
                    )

                call.respond(response)
            }
        }
    }
}

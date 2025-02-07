package com.example.configuration

import com.example.app.session.application.route.sessionRoutes
import com.example.app.user.application.route.userRoutes
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
        sessionRoutes()
        authenticate {
            userRoutes()
        }
    }
}

package com.example.configuration

import com.example.app.session.application.violation.SessionViolation
import com.example.shared.util.koin.ApplicationConfiguration
import com.example.shared.util.koin.getProperty
import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.github.smiley4.ktorswaggerui.data.AuthScheme
import io.github.smiley4.ktorswaggerui.data.AuthType
import io.github.smiley4.ktorswaggerui.dsl.routing.get
import io.github.smiley4.ktorswaggerui.routing.openApiSpec
import io.github.smiley4.ktorswaggerui.routing.swaggerUI
import io.github.smiley4.schemakenerator.reflection.processReflection
import io.github.smiley4.schemakenerator.swagger.compileReferencingRoot
import io.github.smiley4.schemakenerator.swagger.data.TitleType
import io.github.smiley4.schemakenerator.swagger.generateSwaggerSchema
import io.github.smiley4.schemakenerator.swagger.withTitle
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import io.ktor.server.webjars.Webjars
import io.swagger.v3.oas.models.media.Schema
import kotlinx.datetime.Instant
import org.koin.ktor.ext.getKoin
import java.io.File
import java.util.UUID

const val SECURITY_SCHEME = "Auth"

fun Application.configureDocumentation() =
    with(getKoin()) {
        val swaggerUrls = getProperty<List<String>>(ApplicationConfiguration.SWAGGER_URLS)

        install(Webjars) {
            path = "/webjars"
        }

        install(SwaggerUI) {
            info {
                title = "Coffee shop API"
                version = "0.0.1"
                description = "Coffee shop API"
            }

            swagger {
                showTagFilterInput = true
                withCredentials = true
            }

            security {
                securityScheme(SECURITY_SCHEME) {
                    type = AuthType.HTTP
                    scheme = AuthScheme.BEARER
                    bearerFormat = "JWT"
                }

                defaultSecuritySchemeNames(SECURITY_SCHEME)

                defaultUnauthorizedResponse {
                    description = SessionViolation.InvalidToken().message
                }
            }

            schemas {
                generator = {
                    it
                        .processReflection {
                            redirect<UUID, String>()
                            redirect<Instant, String>()
                        }.generateSwaggerSchema()
                        .withTitle(TitleType.SIMPLE)
                        .compileReferencingRoot()
                }

                overwrite<File>(
                    Schema<Any>().apply {
                        type = "string"
                        format = "binary"
                    },
                )

                overwrite<UUID>(
                    Schema<Any>().apply {
                        type = "string"
                        format = "uuid"
                    },
                )
            }

            swaggerUrls.forEach {
                server { url = it }
            }
        }

        routing {
            route("/") {
                get({
                    hidden = true
                }) {
                    call.respondRedirect("/swagger-ui")
                }
            }

            // Will redirect to http://index.html if '/' route is used.
            route("/swagger-ui") {
                swaggerUI("/api.json")
            }
            route("api.json") {
                openApiSpec()
            }
        }
    }

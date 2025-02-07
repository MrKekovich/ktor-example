package com.example

import com.example.configuration.configureDatabase
import com.example.configuration.configureDocumentation
import com.example.configuration.configureHTTP
import com.example.configuration.configureKoin
import com.example.configuration.configureMonitoring
import com.example.configuration.configureRouting
import com.example.configuration.configureSecurity
import com.example.configuration.configureSerialization
import com.example.configuration.configureStatusPages
import com.example.configuration.configureValidation
import com.example.configuration.configureWebsockets
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("Unused")
fun Application.module() {
    configureKoin()
    configureDatabase()
    configureHTTP()
    configureSerialization()
    configureMonitoring()
    configureStatusPages()
    configureValidation()
    configureSecurity()
    configureWebsockets()
    configureRouting()
    configureDocumentation()
}

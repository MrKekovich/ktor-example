package com.example.configuration

import com.example.app.todo.infrastructure.persistence.TodoTable
import com.example.app.user.infrastructure.persistence.UserTable
import com.example.shared.util.koin.ApplicationConfiguration
import com.example.shared.util.koin.getProperty
import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.ktor.ext.getKoin

val databaseTableRegistry =
    arrayOf<Table>(
        UserTable,
        TodoTable,
    )

fun Application.configureDatabase(): Unit =
    with(getKoin()) {
        val url = getProperty<String>(ApplicationConfiguration.DATABASE_URL)
        val user = getProperty<String>(ApplicationConfiguration.DATABASE_USER)
        val password = getProperty<String>(ApplicationConfiguration.DATABASE_PASSWORD)
        val driver = getProperty<String>(ApplicationConfiguration.DATABASE_DRIVER)

        val connection =
            Database.connect(
                url = url,
                user = user,
                password = password,
                driver = driver,
            )

//  TODO: Remove in production. Use migrations instead.
        transaction(connection) {
            @Suppress("SpreadOperator")
            SchemaUtils.createMissingTablesAndColumns(*databaseTableRegistry)
        }

//  TODO: Uncomment in the production.
//        Flyway
//            .configure()
//            .dataSource(url, user, password)
//            .locations("classpath:db/migration")
//            .load()
//            .migrate()
    }

package com.example.configuration

import com.example.app.session.application.usecase.AuthenticationUseCaseImpl
import com.example.app.session.domain.usecase.AuthenticationUseCase
import com.example.app.user.application.usecase.UserUseCaseImpl
import com.example.app.user.domain.repository.UserRepository
import com.example.app.user.domain.usecase.UserUseCase
import com.example.app.user.infrastructure.repository.UserRepositoryImpl
import com.example.shared.infrastructure.persistence.ExposedTransactionManager
import com.example.shared.infrastructure.persistence.TransactionManager
import com.example.shared.util.koin.ApplicationConfiguration
import com.example.shared.util.koin.injectDefaultUserConfiguration
import com.example.shared.util.koin.injectJwtConfiguration
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.core.Koin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
    install(Koin) {
        properties(
            ApplicationConfiguration { configurationProperties },
        )

        modules(
            koin.getRepositoryModule(),
            koin.getUseCaseModule(),
        )
    }
}

fun Koin.getRepositoryModule(): Module =
    module {
        single<TransactionManager> { ExposedTransactionManager }

        single<UserRepository> { UserRepositoryImpl() }
    }

fun Koin.getUseCaseModule(): Module =
    module {
        single<AuthenticationUseCase> {
            AuthenticationUseCaseImpl(
                get(),
                injectJwtConfiguration(),
                injectDefaultUserConfiguration(),
            )
        }

        single<UserUseCase> { UserUseCaseImpl(get()) }
    }

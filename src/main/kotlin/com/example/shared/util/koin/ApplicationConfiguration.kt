package com.example.shared.util.koin

import com.example.shared.util.ext.getEnumConfigurationValue
import com.example.shared.util.ext.getIntConfigurationValue
import com.example.shared.util.ext.getListConfigurationValue
import com.example.shared.util.ext.getLongConfigurationValue
import com.example.shared.util.ext.getStringConfigurationValue
import io.ktor.server.application.Application
import io.ktor.server.config.ApplicationConfig
import kotlin.reflect.KType
import kotlin.reflect.typeOf

/**
 * Application configuration enum.
 *
 * @property path Path to the property.
 * @property type Type of the property.
 */
enum class ApplicationConfiguration(
    private val path: String,
    private val type: KType = typeOf<String>(),
) {
    DATABASE_URL("database.url"),
    DATABASE_USER("database.user"),
    DATABASE_PASSWORD("database.password"),
    DATABASE_DRIVER("database.driver"),

    SWAGGER_URLS("swagger.urls", typeOf<List<String>>()),

    JWT_SECRET("jwt.secret"),
    JWT_ISSUER("jwt.issuer"),
    JWT_SUBJECT("jwt.subject"),
    JWT_ACCESS_TOKEN_LIFETIME("jwt.accessTokenLifetimeMs", typeOf<Long>()),
    JWT_REFRESH_TOKEN_LIFETIME("jwt.refreshTokenLifetimeMs", typeOf<Long>()),
    ;

    /**
     * Gets value from the [ApplicationConfig] by the [path]
     *
     * @param applicationConfig [ApplicationConfig]
     * @return `value` from the [ApplicationConfig]
     */
    fun getValue(applicationConfig: ApplicationConfig): Any =
        with(applicationConfig) {
            when (type) {
                typeOf<String>() -> getStringConfigurationValue(path)
                typeOf<Int>() -> getIntConfigurationValue(path)
                typeOf<Long>() -> getLongConfigurationValue(path)
                typeOf<List<String>>() -> getListConfigurationValue(path)
                typeOf<Enum<*>>() -> getEnumConfigurationValue(path, type)
                else -> error("Unsupported type: $type")
            }
        }

    /**
     * Application configuration context.
     * Used to get values from the [ApplicationConfig] in a [Map] format.
     */
    companion object ConfigurationContext {
        /**
         * Provides the context to use helper extension functions from [ConfigurationContext]
         *
         * @param T Return type
         * @param block Lambda with the context.
         * @return The result of [block]
         */
        operator fun <T> invoke(block: ConfigurationContext.() -> T): T = block()

        /**
         * Provides a structured way of accessing the [ApplicationConfig] properties.
         * Mainly was created for using in the `Koin` configuration.
         */
        val Application.configurationProperties: Map<String, Any>
            get() =
                entries.associate {
                    it.name to it.getValue(environment.config)
                }
    }
}

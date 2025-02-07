package com.example.shared.util.ext

import io.ktor.server.config.ApplicationConfig
import io.ktor.server.config.ApplicationConfigurationException
import kotlin.reflect.KType
import kotlin.reflect.jvm.jvmErasure

/**
 * Gets value from the [ApplicationConfig], removes all `_` and transforms it to [Long]
 *
 * @param path Path to the property.
 * @return [Long] value from the [ApplicationConfig]
 * @throws [IllegalStateException] if the property is not a [Long]
 * @throws [ApplicationConfigurationException] if property could not be found
 */
fun ApplicationConfig.getLongConfigurationValue(path: String): Long =
    property(path)
        .getString()
        .replace("_", "")
        .toLongOrNull()
        ?: error("Configuration value $path must be Long")

/**
 * Gets value from the [ApplicationConfig], removes all `_` and transforms it to [Int]
 *
 * @param path Path to the property.
 * @return [Int] value from the [ApplicationConfig]
 * @throws [IllegalStateException] if the property is not a [Int]
 * @throws [ApplicationConfigurationException] if property could not be found
 */
fun ApplicationConfig.getIntConfigurationValue(path: String): Int =
    property(path)
        .getString()
        .replace("_", "")
        .toIntOrNull()
        ?: error("Configuration value $path must be Int")

/**
 * Gets value from the [ApplicationConfig]
 *
 * @param path Path to the property.
 * @return [String] value from the [ApplicationConfig]
 * @throws [ApplicationConfigurationException] if property could not be found
 */
fun ApplicationConfig.getStringConfigurationValue(path: String): String = property(path).getString()

/**
 * Gets value from the [ApplicationConfig]
 *
 * @param path Path to the property.
 * @return [List] value from the [ApplicationConfig]
 * @throws [ApplicationConfigurationException] if property could not be found
 */
fun ApplicationConfig.getListConfigurationValue(path: String): List<String> = property(path).getList()

/**
 * Resolves an enum value from a string, case-insensitively, using KType.
 *
 * @param path Path to the property.
 * @param type the KType of the enum
 * @return the matching enum value
 * @throws IllegalArgumentException provided type is not an enum
 * @throws ApplicationConfigurationException if property could not be found / value is not an enum entry
 */
fun ApplicationConfig.getEnumConfigurationValue(
    path: String,
    type: KType,
): Any {
    val enumClass = type.jvmErasure
    require(enumClass.java.isEnum) {
        "Provided type $type is not an enum."
    }

    val value = getStringConfigurationValue(path)

    val matchingEntry =
        enumClass.java.enumConstants
            ?.firstOrNull { (it as Enum<*>).name.equals(value, ignoreCase = true) }
    return matchingEntry ?: throw ApplicationConfigurationException(
        "Invalid value '$value' for enum ${enumClass.simpleName}. " +
            "Must be one of: ${enumClass.java.enumConstants?.joinToString { (it as Enum<*>).name }}.",
    )
}

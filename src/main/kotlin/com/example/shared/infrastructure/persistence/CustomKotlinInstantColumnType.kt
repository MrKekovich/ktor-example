package com.example.shared.infrastructure.persistence

import kotlinx.datetime.Instant
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.IDateColumnType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.exposedLogger
import org.jetbrains.exposed.sql.kotlin.datetime.KotlinInstantColumnType
import org.jetbrains.exposed.sql.vendors.H2Dialect
import org.jetbrains.exposed.sql.vendors.MariaDBDialect
import org.jetbrains.exposed.sql.vendors.MysqlDialect
import org.jetbrains.exposed.sql.vendors.OracleDialect
import org.jetbrains.exposed.sql.vendors.PostgreSQLDialect
import org.jetbrains.exposed.sql.vendors.currentDialect
import java.sql.ResultSet

/**
 * Decorator for [KotlinInstantColumnType] that adds precision modifier.
 *
 * @property precision
 * @constructor Create empty Custom kotlin instant column type
 */
class CustomKotlinInstantColumnType(
    private val precision: Int = 9,
    private val kotlinInstantColumnType: KotlinInstantColumnType = KotlinInstantColumnType(),
) : ColumnType<Instant>(),
    IDateColumnType by kotlinInstantColumnType {
    override fun sqlType(): String {
        val baseType = kotlinInstantColumnType.sqlType()

        return if (currentDialect.isCompatible(supportedDialects)) {
            "$baseType($precision)"
        } else {
            exposedLogger.warn("CustomKotlinInstantColumnType is not supported by '$currentDialect'")
            baseType
        }
    }

    override fun nonNullValueToString(value: Instant): String = kotlinInstantColumnType.nonNullValueToString(value)

    override fun valueFromDB(value: Any): Instant = kotlinInstantColumnType.valueFromDB(value)

    override fun readObject(
        rs: ResultSet,
        index: Int,
    ): Any? = kotlinInstantColumnType.readObject(rs, index)

    override fun notNullValueToDB(value: Instant): Any = kotlinInstantColumnType.notNullValueToDB(value)

    override fun nonNullValueAsDefaultString(value: Instant): String =
        kotlinInstantColumnType.nonNullValueAsDefaultString(
            value,
        )
}

fun Table.timestamp(
    name: String,
    precision: Int = 9,
): Column<Instant> = registerColumn(name, CustomKotlinInstantColumnType(precision))

private val supportedDialects by lazy {
    listOf(
        PostgreSQLDialect,
        MysqlDialect,
        MariaDBDialect,
        H2Dialect,
        OracleDialect,
    )
}

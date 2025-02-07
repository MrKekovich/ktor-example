package com.example.shared.infrastructure.persistence

import org.jetbrains.exposed.sql.vendors.DatabaseDialect
import org.jetbrains.exposed.sql.vendors.H2Dialect
import org.jetbrains.exposed.sql.vendors.VendorDialect
import org.jetbrains.exposed.sql.vendors.currentDialect

fun DatabaseDialect.checkCompatibility(supportedDialects: List<VendorDialect.DialectNameProvider>) {
    check(this.isCompatible(supportedDialects)) {
        "Natural sorting is not supported by dialect '$currentDialect'"
    }
}

fun DatabaseDialect.isCompatible(supportedDialects: List<VendorDialect.DialectNameProvider>): Boolean {
    val h2Dialect = this as? H2Dialect

    return h2Dialect?.isModeCompatible(supportedDialects)
        ?: currentDialect.isCompatibleByName(supportedDialects)
}

fun DatabaseDialect.isCompatibleByName(supportedDialects: List<VendorDialect.DialectNameProvider>): Boolean =
    supportedDialects.any { it.dialectName.equals(this.name, ignoreCase = true) }

fun H2Dialect.isModeCompatible(supportedDialects: List<VendorDialect.DialectNameProvider>): Boolean =
    delegatedDialectNameProvider in supportedDialects

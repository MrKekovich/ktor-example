package com.example.shared.infrastructure.persistence

import org.jetbrains.exposed.sql.transactions.transaction

object ExposedTransactionManager : TransactionManager {
    override fun <T> invoke(block: () -> T): T = transaction { block() }
}

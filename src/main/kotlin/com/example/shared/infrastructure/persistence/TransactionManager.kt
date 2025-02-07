package com.example.shared.infrastructure.persistence

interface TransactionManager {
    operator fun <T> invoke(block: () -> T): T
}

package com.example.shared.util.ext

import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.intLiteral

/**
 * Check if table contains record with specified [id]
 *
 * @param T The type of the table
 * @param id The value of the primary key
 * @return `true` if table contains record with specified [id], `false` otherwise
 */
fun <T : Comparable<T>> IdTable<T>.exists(id: T): Boolean =
    select(intLiteral(1))
        .where { this@exists.id eq id }
        .empty()
        .not()

/**
 * Check if table contains records, matching the [where] expression
 *
 * @param where The search expression
 * @return `true` if table contains records, matching the [where] expression, `false` otherwise
 */
fun Table.existsWhere(where: SqlExpressionBuilder.() -> Op<Boolean>): Boolean =
    select(intLiteral(1))
        .where(where)
        .empty()
        .not()

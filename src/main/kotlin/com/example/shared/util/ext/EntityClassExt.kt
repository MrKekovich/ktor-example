package com.example.shared.util.ext

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.intLiteral

/**
 * Check if entity exists by its [id]
 *
 * @param ID The type of the [id]
 * @param T The type of the entity
 * @param id The id of the entity to check
 * @return `true` if exists, `false` otherwise
 */
fun <ID : Comparable<ID>, T : Entity<ID>> EntityClass<ID, T>.exists(id: ID): Boolean = table.exists(id)

/**
 * Check if entity exists, matching the [where] expression
 *
 * @param ID The type of the entity `id`
 * @param T The type of the entity
 * @param where The search expression.
 * @return `true` if exists, `false` otherwise
 */
fun <ID : Comparable<ID>, T : Entity<ID>> EntityClass<ID, T>.existsWhere(where: SqlExpressionBuilder.() -> Op<Boolean>): Boolean =
    table.existsWhere(where)

/**
 * Find entity, matching the [where] expression or return `null`
 *
 * @param ID The type of the `id`
 * @param T The type of the entity
 * @param where The search expression.
 * @return The first entity, matching the [where] expression or `null`
 */
fun <ID : Comparable<ID>, T : Entity<ID>> EntityClass<ID, T>.firstOrNull(where: SqlExpressionBuilder.() -> Op<Boolean>): T? =
    find(where).firstOrNull()

/**
 * Count entities, matching the [where] expression
 *
 * @param ID The type of the `id`
 * @param T The type of the entity
 * @param where The search expression.
 * @return The number of entities, matching the [where] expression
 */
fun <ID : Comparable<ID>, T : Entity<ID>> EntityClass<ID, T>.count(where: SqlExpressionBuilder.() -> Op<Boolean>): Long {
    val expression = where(SqlExpressionBuilder)
    return count(expression)
}

/**
 * Count all entities
 *
 * @param ID The type of the `id`
 * @param T The type of the entity
 * @return The number of all entities
 */
fun <ID : Comparable<ID>, T : Entity<ID>> EntityClass<ID, T>.count(): Long = table.select(intLiteral(1)).count()

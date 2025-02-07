package com.example.shared.util.ext

fun Boolean.Companion.random(): Boolean = (0..1).random() == 1

fun Long.Companion.randomPositive(): Long = (1..MAX_VALUE).random()

fun Int.Companion.randomPositive(): Int = (1..MAX_VALUE).random()

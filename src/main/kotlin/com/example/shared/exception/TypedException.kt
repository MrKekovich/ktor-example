package com.example.shared.exception

import com.example.shared.util.validation.violation.PropertyViolation

open class TypedException(
    val violation: PropertyViolation,
    cause: Throwable? = null,
) : Throwable(
        violation.message,
        cause,
    )

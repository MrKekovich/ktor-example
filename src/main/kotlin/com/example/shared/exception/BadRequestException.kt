package com.example.shared.exception

import com.example.shared.util.validation.violation.PropertyViolation

open class BadRequestException(
    violation: PropertyViolation,
) : TypedException(violation)

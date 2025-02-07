package com.example.shared.exception

import com.example.shared.util.validation.violation.PropertyViolation

open class NotFoundException(
    violation: PropertyViolation,
) : TypedException(violation)

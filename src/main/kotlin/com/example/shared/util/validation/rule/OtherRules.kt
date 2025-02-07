package com.example.shared.util.validation.rule

import com.example.shared.util.validation.violation.OtherViolation
import io.github.kverify.core.context.validate
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.model.Rule

object OtherRules {
    fun exactlyOneFieldNotNull(vararg fields: NamedValue<Any?>): Rule<Unit> =
        Rule {
            validate(fields.count { it.value != null } == 1) {
                val fieldNames = fields.map { it.name }
                OtherViolation.ExactlyOneFieldNotNull(fieldNames)
            }
        }

    fun atLeastOneFieldNotNull(vararg fields: NamedValue<Any?>): Rule<Unit> =
        Rule {
            validate(fields.any { it.value != null }) {
                val fieldNames = fields.map { it.name }
                OtherViolation.AtLeastOneFieldNotNull(fieldNames)
            }
        }
}

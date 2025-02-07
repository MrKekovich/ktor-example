package com.example.shared.util.validation.rule

import com.example.shared.util.validation.violation.StringViolation
import io.github.kverify.core.context.validate
import io.github.kverify.core.model.NamedRule
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.model.Rule
import io.github.kverify.rule.set.StringRules

fun StringRules.Companion.namedMatchesPhoneNumber(): Rule<NamedValue<String>> =
    NamedRule { namedValue ->
        validate(
            namedValue.value.matches(
                Regex(
                    "\\+?\\d{1,3}?[-.\\s]?\\(?\\d{1,4}?\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}",
                ),
            ),
        ) {
            StringViolation.MatchesPhoneNumber(namedValue)
        }
    }

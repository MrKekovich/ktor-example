package com.example.app.session.application.dto

import com.example.shared.application.dto.Validatable
import com.example.shared.util.validation.rule.namedMatchesPhoneNumber
import io.github.kverify.core.jvm.model.toNamed
import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.validator.validateAll
import io.github.kverify.rule.set.StringRules
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val phoneNumber: String,
    val code: String,
) : Validatable {
    override fun validate(): ValidationResult =
        validateAll {
            ::phoneNumber.toNamed().applyRules(
                StringRules.namedMatchesPhoneNumber(),
            )

            ::code.toNamed().applyRules(
                StringRules.namedNotBlank(),
                StringRules.namedNumeric(),
                StringRules.namedOfLength(4),
            )
        }
}

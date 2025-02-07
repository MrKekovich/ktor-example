package com.example.app.user.application.dto

import com.example.shared.application.dto.Validatable
import com.example.shared.util.validation.rule.namedMatchesRussianPhoneNumber
import io.github.kverify.core.jvm.model.toNamed
import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.model.unwrapOrNull
import io.github.kverify.core.validator.validateAll
import io.github.kverify.rule.set.StringRules
import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserRequest(
    val phoneNumber: String? = null,
    val displayName: String? = null,
) : Validatable {
    override fun validate(): ValidationResult =
        validateAll {
            ::phoneNumber.toNamed().unwrapOrNull()?.applyRules(
                StringRules.namedMatchesRussianPhoneNumber(),
            )

            ::displayName.toNamed().unwrapOrNull()?.applyRules(
                StringRules.namedNotBlank(),
                StringRules.namedLengthBetween(2..255),
            )
        }
}

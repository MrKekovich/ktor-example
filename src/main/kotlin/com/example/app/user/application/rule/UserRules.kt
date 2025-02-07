package com.example.app.user.application.rule

import com.example.app.user.application.violation.UserViolation
import com.example.app.user.domain.entity.UserEntity
import com.example.shared.application.dto.CashAndEnergy
import io.github.kverify.core.model.Rule
import io.github.kverify.core.model.plus

object UserRules {
    fun sufficientPersonalBalance(requiredBalance: Int): Rule<UserEntity> =
        Rule(
            predicate = { it.personalBalance >= requiredBalance },
            violationGenerator = { UserViolation.NotEnoughPersonalBalance(requiredBalance, it.id) },
        )

    fun sufficientBusinessBalance(requiredBalance: Int): Rule<UserEntity> =
        Rule(
            predicate = { it.businessBalance >= requiredBalance },
            violationGenerator = { UserViolation.NotEnoughBusinessBalance(requiredBalance, it.id) },
        )

    fun sufficientEnergy(requiredEnergy: Int): Rule<UserEntity> =
        Rule(
            predicate = { it.energyLevel >= requiredEnergy },
            violationGenerator = { UserViolation.NotEnoughEnergy(requiredEnergy, it.id) },
        )

    fun sufficientBusinessBalanceAndEnergy(cashAndEnergy: CashAndEnergy): Rule<UserEntity> =
        sufficientBusinessBalance(cashAndEnergy.cash) + sufficientEnergy(cashAndEnergy.energy)
}

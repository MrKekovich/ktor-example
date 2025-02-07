package com.example.shared.util.ext

import com.example.app.user.domain.entity.UserEntity
import com.example.shared.application.dto.UserPrincipal

fun UserPrincipal(userEntity: UserEntity): UserPrincipal =
    UserPrincipal(
        id = userEntity.id,
    )

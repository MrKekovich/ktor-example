package com.example.shared.util.ext

import com.auth0.jwt.interfaces.Payload
import com.example.shared.application.dto.UserPrincipal

fun Payload.getClaim(key: UserPrincipal.FieldNames): String? = getClaim(key.name).asString()

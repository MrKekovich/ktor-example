package com.example.app.session.domain.usecase

import com.example.app.session.application.dto.LoginRequest
import com.example.app.session.application.dto.RefreshRequest
import com.example.app.session.application.dto.TokenResponse

interface AuthenticationUseCase {
    fun login(rq: LoginRequest): TokenResponse

    fun refreshToken(rq: RefreshRequest): TokenResponse
}

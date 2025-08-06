package com.example.vknews.presentation.authScreen

import com.vk.id.AccessToken

sealed class AuthState {
    class Authorized(val token: AccessToken): AuthState()
    object NotAuthorized: AuthState()
    object Initial: AuthState()
}
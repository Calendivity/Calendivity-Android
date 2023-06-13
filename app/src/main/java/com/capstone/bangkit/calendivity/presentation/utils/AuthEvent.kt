package com.capstone.bangkit.calendivity.presentation.utils

sealed class AuthEvent {
    object AccessTokenCachedSuccess : AuthEvent()
    class CachedAccessTokenSuccess(val accessToken: String) : AuthEvent()
    object RefreshTokenCachedSuccess : AuthEvent()
    class CachedRefreshTokenSuccess(val refreshToken: String) : AuthEvent()
}
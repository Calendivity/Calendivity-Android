package com.capstone.bangkit.calendivity.presentation.utils

sealed class LoginEvent {
    object LoginCachedSuccess : LoginEvent()
    class CachedLoginSuccess(val isLogin: Boolean) : LoginEvent()
}
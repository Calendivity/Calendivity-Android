package com.capstone.bangkit.calendivity.domain.usecases

interface UserPreferencesRepository {
    suspend fun setUserOnboarding(isUserOnboarding: Boolean)

    suspend fun getUserOnboarding(): Result<Boolean>

    suspend fun setUserLogin(isLogin: Boolean)

    suspend fun getUserLogin(): Result<Boolean>

    suspend fun setAccessToken(accessToken: String)

    suspend fun getAccessToken(): Result<String>

    suspend fun setRefreshToken(refreshToken: String)

    suspend fun getRefreshToken(): Result<String>
}
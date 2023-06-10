package com.capstone.bangkit.calendivity.domain.usecases

interface UserPreferencesRepository {
    suspend fun setUserOnboarding(isUserOnboarding: Boolean)

    suspend fun getUserOnboarding(): Result<Boolean>

    suspend fun setUserLogin(isLogin: Boolean)

    suspend fun getUserLogin(): Result<Boolean>
}
package com.capstone.bangkit.calendivity.presentation.di

import androidx.lifecycle.ViewModel
import com.capstone.bangkit.calendivity.domain.usecases.UserPreferencesRepository
import com.capstone.bangkit.calendivity.presentation.utils.AuthEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class AccessRefreshTokenViewModel @Inject constructor(private val userPreferencesRepository: UserPreferencesRepository) :
    ViewModel() {
    fun cacheAccessToken(accessToken: String) = flow {
        userPreferencesRepository.setAccessToken(accessToken)
        emit(AuthEvent.AccessTokenCachedSuccess)
    }

    fun getCachedAccessToken() = flow {
        val result = userPreferencesRepository.getAccessToken()
        val accessToken = result.getOrNull()
        emit(AuthEvent.CachedAccessTokenSuccess(accessToken!!))
    }

    fun cacheRefreshToken(refreshToken: String) = flow {
        userPreferencesRepository.setRefreshToken(refreshToken)
        emit(AuthEvent.RefreshTokenCachedSuccess)
    }

    fun getCachedRefreshToken() = flow {
        val result = userPreferencesRepository.getRefreshToken()
        val refreshToken = result.getOrNull()
        emit(AuthEvent.CachedRefreshTokenSuccess(refreshToken!!))
    }
}
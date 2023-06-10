package com.capstone.bangkit.calendivity.presentation.di

import androidx.lifecycle.ViewModel
import com.capstone.bangkit.calendivity.domain.usecases.UserPreferencesRepository
import com.capstone.bangkit.calendivity.presentation.utils.LoginEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userPreferencesRepository: UserPreferencesRepository) :
    ViewModel() {

    fun cacheLogin(isLogin: Boolean) = flow {
        userPreferencesRepository.setUserLogin(isLogin)
        emit(LoginEvent.LoginCachedSuccess)
    }

    fun getCachedLogin() = flow {
        val result = userPreferencesRepository.getUserLogin()
        val isLogin = result.getOrNull()
        emit(LoginEvent.CachedLoginSuccess(isLogin!!))
    }
}
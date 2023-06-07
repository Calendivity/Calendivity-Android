package com.capstone.bangkit.calendivity.presentation.di

import androidx.lifecycle.ViewModel
import com.capstone.bangkit.calendivity.domain.usecases.UserPreferencesRepository
import com.capstone.bangkit.calendivity.presentation.utils.OnboardingEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

// view model
@HiltViewModel
class OnboardingViewModel @Inject constructor(private val userPreferencesRepository: UserPreferencesRepository) :
    ViewModel() {

    fun cacheOnboarding(isOnboarding: Boolean) = flow {
        userPreferencesRepository.setUserOnboarding(isOnboarding)
        emit(OnboardingEvent.OnboardingCachedSuccess)
    }

    fun getCachedOnboarding() = flow {
        val result = userPreferencesRepository.getUserOnboarding()
        val isOnboarding = result.getOrNull()
        emit(OnboardingEvent.CachedonboardingFetchSuccess(isOnboarding!!))
    }
}
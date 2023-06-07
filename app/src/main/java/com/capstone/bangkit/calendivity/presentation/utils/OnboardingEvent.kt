package com.capstone.bangkit.calendivity.presentation.utils

sealed class OnboardingEvent {
    object OnboardingCachedSuccess : OnboardingEvent()
    class CachedonboardingFetchSuccess(
        val isOnboarding: Boolean
    ) : OnboardingEvent()
}
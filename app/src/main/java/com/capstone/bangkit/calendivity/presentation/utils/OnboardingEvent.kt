package com.capstone.bangkit.calendivity.presentation.utils

sealed class OnboardingEvent {
    object OnboardingCachedSuccess : OnboardingEvent()
    class CachedOnboardingFetchSuccess(
        val isOnboarding: Boolean
    ) : OnboardingEvent()
}
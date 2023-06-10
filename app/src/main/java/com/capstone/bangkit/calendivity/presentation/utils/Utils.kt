package com.capstone.bangkit.calendivity.presentation.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.capstone.bangkit.calendivity.BuildConfig

object Utils {
    const val APPLICATION_ID = BuildConfig.APPLICATION_ID
    const val SERVER_AUTH = BuildConfig.SERVER_AUTH
    const val SCOPE_URI_CALENDAR = "https://www.googleapis.com/auth/calendar"
    const val BASE_URL = BuildConfig.BASE_URL

    val KEY_USER_PREFERENCES_ONBOARDING = booleanPreferencesKey(
        name = "user_preferences_onboarding"
    )

    val KEY_USER_PREFERENCES_LOGIN = booleanPreferencesKey(
        name = "user_preferences_login"
    )
}
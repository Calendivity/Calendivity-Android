package com.capstone.bangkit.calendivity.presentation.utils

import androidx.datastore.preferences.core.booleanPreferencesKey

object Utils {
    val KEY_USER_PREFERENCES = booleanPreferencesKey(
        name = "user_preferences_onboarding"
    )
}
package com.capstone.bangkit.calendivity.presentation.utils

import android.view.View
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.capstone.bangkit.calendivity.BuildConfig

object Utils {
    const val APPLICATION_ID = BuildConfig.APPLICATION_ID
    const val SERVER_AUTH = BuildConfig.SERVER_AUTH
    const val SCOPE_URI_CALENDAR = BuildConfig.SCOPE_CALENDAR
    const val BASE_URL = BuildConfig.BASE_URL
    const val IS_DEBUG = BuildConfig.BUILD_TYPE == "debug"
    const val SHARED_PREF = "SHARED_PREF"

    val KEY_USER_PREFERENCES_ONBOARDING = booleanPreferencesKey(
        name = "user_preferences_onboarding"
    )

    val KEY_USER_PREFERENCES_LOGIN = booleanPreferencesKey(
        name = "user_preferences_login"
    )

    val KEY_USER_PREFERENCES_ACCESSTOKEN = stringPreferencesKey(
        name = "user_preferences_accesstoken"
    )

    val KEY_USER_PREFERENCES_REFRESHTOKEN = stringPreferencesKey(
        name = "user_preferences_refreshtoken"
    )

    fun showLoading(view: View, isLoading: Boolean) =
        if (isLoading) view.visibility = View.VISIBLE
        else view.visibility = View.INVISIBLE

}
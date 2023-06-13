package com.capstone.bangkit.calendivity.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.capstone.bangkit.calendivity.domain.usecases.UserPreferencesRepository
import com.capstone.bangkit.calendivity.presentation.utils.Utils
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPreferencesRepositoryImplementation @Inject constructor(private val userDataStorePreferences: DataStore<Preferences>) :
    UserPreferencesRepository {

    override suspend fun setUserOnboarding(isUserOnboarding: Boolean) {
        Result.runCatching {
            userDataStorePreferences.edit { preferences ->
                preferences[Utils.KEY_USER_PREFERENCES_ONBOARDING] = isUserOnboarding
            }
        }
    }

    override suspend fun getUserOnboarding(): Result<Boolean> {
        return Result.runCatching {
            val flow = userDataStorePreferences.data.catch { exception ->
                // dataStore.data throws an IOException when an error is encountered when reading data
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[Utils.KEY_USER_PREFERENCES_ONBOARDING]
            }
            val value = flow.firstOrNull() ?: false
            value
        }
    }

    override suspend fun setUserLogin(isLogin: Boolean) {
        Result.runCatching {
            userDataStorePreferences.edit { preferences ->
                preferences[Utils.KEY_USER_PREFERENCES_LOGIN] = isLogin
            }
        }
    }

    override suspend fun getUserLogin(): Result<Boolean> {
        return Result.runCatching {
            val flow = userDataStorePreferences.data.catch { exception ->
                // dataStore.data throws an IOException when an error is encountered when reading data
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[Utils.KEY_USER_PREFERENCES_LOGIN]
            }
            val value = flow.firstOrNull() ?: false
            value
        }
    }

    override suspend fun setAccessToken(accessToken: String) {
        Result.runCatching {
            userDataStorePreferences.edit { preferences ->
                preferences[Utils.KEY_USER_PREFERENCES_ACCESSTOKEN] = accessToken
            }
        }
    }

    override suspend fun getAccessToken(): Result<String> {
        return Result.runCatching {
            val flow = userDataStorePreferences.data.catch { exception ->
                // dataStore.data throws an IOException when an error is encountered when reading data
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[Utils.KEY_USER_PREFERENCES_ACCESSTOKEN]
            }
            val value = flow.firstOrNull() ?: "nothing"
            value
        }
    }

    override suspend fun setRefreshToken(refreshToken: String) {
        Result.runCatching {
            userDataStorePreferences.edit { preferences ->
                preferences[Utils.KEY_USER_PREFERENCES_REFRESHTOKEN] = refreshToken
            }
        }
    }

    override suspend fun getRefreshToken(): Result<String> {
        return Result.runCatching {
            val flow = userDataStorePreferences.data.catch { exception ->
                // dataStore.data throws an IOException when an error is encountered when reading data
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[Utils.KEY_USER_PREFERENCES_REFRESHTOKEN]
            }
            val value = flow.firstOrNull() ?: "nothing"
            value
        }
    }
}
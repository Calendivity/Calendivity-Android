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
                preferences[Utils.KEY_USER_PREFERENCES] = isUserOnboarding
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
                preferences[Utils.KEY_USER_PREFERENCES]
            }
            val value = flow.firstOrNull() ?: false
            value
        }
    }
}
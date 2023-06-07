package com.capstone.bangkit.calendivity.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.capstone.bangkit.calendivity.BuildConfig
import com.capstone.bangkit.calendivity.data.repo.UserPreferencesRepositoryImplementation
import com.capstone.bangkit.calendivity.domain.usecases.UserPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(
    // just my preference of naming including the package name
    name = BuildConfig.APPLICATION_ID
)

@Module
@InstallIn(SingletonComponent::class)
abstract class UserPreferencesModule {
    // binds instance of MyUserPreferencesRepository
    @Binds
    @Singleton
    abstract fun bindUserPreferencesRepository(
        userPreferencesRepositoryImplementation: UserPreferencesRepositoryImplementation
    ): UserPreferencesRepository

    companion object {
        // provides instance of DataStore
        @Provides
        @Singleton
        fun provideUserDataStorePreferences(
            @ApplicationContext applicationContext: Context
        ): DataStore<Preferences> {
            return applicationContext.userDataStore
        }
    }
}
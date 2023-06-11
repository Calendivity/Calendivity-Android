package com.capstone.bangkit.calendivity.data.repo

import com.capstone.bangkit.calendivity.domain.usecases.ApiHelper
import javax.inject.Inject

class MainRepo @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getAuthToken(authCode : String) = apiHelper.getAuthToken(authCode)
}
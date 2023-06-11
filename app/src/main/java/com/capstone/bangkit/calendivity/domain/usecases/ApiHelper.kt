package com.capstone.bangkit.calendivity.domain.usecases

import com.capstone.bangkit.calendivity.data.models.AuthTokenDataModel
import retrofit2.Response

interface ApiHelper {
    suspend fun getAuthToken(authCode : String) : Response<AuthTokenDataModel>
}
package com.capstone.bangkit.calendivity.data.repo

import com.capstone.bangkit.calendivity.domain.usecases.ApiHelper
import com.capstone.bangkit.calendivity.data.api.ApiService
import com.capstone.bangkit.calendivity.data.models.AuthTokenDataModel
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun getAuthToken(authCode : String): Response<AuthTokenDataModel> {
        return apiService.getAuthToken(authCode)
    }
}
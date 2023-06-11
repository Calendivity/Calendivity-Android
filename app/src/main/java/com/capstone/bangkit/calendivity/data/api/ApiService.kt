package com.capstone.bangkit.calendivity.data.api

import com.capstone.bangkit.calendivity.data.models.AuthTokenDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("auth")
    suspend fun getAuthToken(
        @Query("code") authCode: String
    ) : Response<AuthTokenDataModel>
}
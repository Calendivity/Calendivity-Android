package com.capstone.bangkit.calendivity.data.api

import com.capstone.bangkit.calendivity.data.models.*
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("auth")
    suspend fun getAuthToken(
        @Query("code") authCode: String
    ): Response<AuthTokenDataModel>

    @POST("tokenrefresh")
    suspend fun getTokenRefresh(
        @Body refreshTokenDataModel: RefreshTokenDataModel
    ): Response<GetRefreshTokenDataModel>

    @GET("users")
    suspend fun getUserInfo(
        @Header("Authorization") token: String
    ): Response<UserInfoDataModel>

    @PUT("users")
    suspend fun updateUserInfo(
        @Header("Authorization") token: String,
        @Body updateUserInfoDataModel: UpdateUserInfoDataModel
    ): Response<JsonObject>

    @GET("users/activities/recomendation")
    suspend fun getListRecomendation(
        @Header("Authorization") token: String,
        @Query("startTime") startTime: String, @Query("endTime") endTime: String
    ): Response<ListRecomendationDataModel>

    @POST("users/activities")
    suspend fun addUserActivity(
        @Header("Authorization") token: String,
        @Body addUserActivityDataModel: AddUserActivityDataModel
    ): Response<JsonObject>

    @GET("users/activities")
    suspend fun getAllUserActivity(
        @Header("Authorization") token: String
    ): Response<GetListAct>

    @PUT("users/activities/{activityId}")
    suspend fun updateAct(
        @Header("Authorization") token: String,
        @Path("activityId") actId: String,
        @Body updateAct: UpdateAcct
    ): Response<JsonObject>
}
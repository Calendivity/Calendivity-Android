package com.capstone.bangkit.calendivity.domain.usecases

import com.capstone.bangkit.calendivity.data.models.*
import com.google.gson.JsonObject
import retrofit2.Response

interface ApiHelper {
    suspend fun getAuthToken(authCode: String): Response<AuthTokenDataModel>

    suspend fun getUserInfo(accessToken: String): Response<UserInfoDataModel>

    suspend fun getTokenRefresh(
        refreshTokenDataModel: RefreshTokenDataModel
    ): Response<GetRefreshTokenDataModel>

    suspend fun updateUserInfo(
        accessToken: String,
        userInfoDataModel: UpdateUserInfoDataModel
    ): Response<JsonObject>

    suspend fun getListRecomendation(
        accessToken: String,
        startTime: String,
        endTime: String
    ): Response<ListRecomendationDataModel>

    suspend fun addUserAct(
        accessToken: String,
        addUserActivityDataModel: AddUserActivityDataModel
    ): Response<JsonObject>

    suspend fun getAllUserAct(
        accessToken: String
    ): Response<GetListAct>

    suspend fun updateUserAct(
        accessToken: String, actId: String, updateAcct: UpdateAcct
    ): Response<JsonObject>
}
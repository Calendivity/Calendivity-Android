package com.capstone.bangkit.calendivity.data.repo

import com.capstone.bangkit.calendivity.data.api.ApiService
import com.capstone.bangkit.calendivity.data.models.*
import com.capstone.bangkit.calendivity.domain.usecases.ApiHelper
import com.google.gson.JsonObject
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun getAuthToken(authCode: String): Response<AuthTokenDataModel> {
        return apiService.getAuthToken(authCode)
    }

    override suspend fun getUserInfo(accessToken: String): Response<UserInfoDataModel> {
        return apiService.getUserInfo(accessToken)
    }

    override suspend fun getTokenRefresh(refreshTokenDataModel: RefreshTokenDataModel): Response<GetRefreshTokenDataModel> {
        return apiService.getTokenRefresh(refreshTokenDataModel)
    }

    override suspend fun updateUserInfo(
        accessToken: String,
        userInfoDataModel: UpdateUserInfoDataModel
    ): Response<JsonObject> {
        return apiService.updateUserInfo(accessToken, userInfoDataModel)
    }

    override suspend fun getListRecomendation(
        accessToken: String,
        startTime: String,
        endTime: String
    ): Response<ListRecomendationDataModel> {
        return apiService.getListRecomendation(accessToken, startTime, endTime)
    }

    override suspend fun addUserAct(
        accessToken: String,
        addUserActivityDataModel: AddUserActivityDataModel
    ): Response<JsonObject> {
        return apiService.addUserActivity(
            accessToken, addUserActivityDataModel
        )
    }

    override suspend fun getAllUserAct(accessToken: String): Response<GetListAct> {
        return apiService.getAllUserActivity(accessToken)
    }

    override suspend fun updateUserAct(
        accessToken: String,
        actId: String,
        updateAcct: UpdateAcct
    ): Response<JsonObject> {
        return apiService.updateAct(accessToken, actId, updateAcct)
    }
}
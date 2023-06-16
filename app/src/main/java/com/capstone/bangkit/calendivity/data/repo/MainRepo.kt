package com.capstone.bangkit.calendivity.data.repo

import com.capstone.bangkit.calendivity.data.models.AddUserActivityDataModel
import com.capstone.bangkit.calendivity.data.models.RefreshTokenDataModel
import com.capstone.bangkit.calendivity.data.models.UpdateAcct
import com.capstone.bangkit.calendivity.data.models.UpdateUserInfoDataModel
import com.capstone.bangkit.calendivity.domain.usecases.ApiHelper
import javax.inject.Inject

class MainRepo @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getAuthToken(authCode: String) = apiHelper.getAuthToken(authCode)
    suspend fun getUserInfo(accessToken: String) = apiHelper.getUserInfo(accessToken)
    suspend fun getRefreshToken(
        refreshTokenDataModel: RefreshTokenDataModel
    ) =
        apiHelper.getTokenRefresh(refreshTokenDataModel)

    suspend fun updateUserInfo(
        accessToken: String,
        updateUserInfoDataModel: UpdateUserInfoDataModel
    ) =
        apiHelper.updateUserInfo(accessToken, updateUserInfoDataModel)

    suspend fun getListRecomendation(accessToken: String, startTime: String, endTime: String) =
        apiHelper.getListRecomendation(accessToken, startTime, endTime)

    suspend fun addUserAct(
        accessToken: String,
        addUserActivityDataModel: AddUserActivityDataModel
    ) = apiHelper.addUserAct(accessToken, addUserActivityDataModel)

    suspend fun getAllUserAct(
        accessToken: String
    ) = apiHelper.getAllUserAct(accessToken)

    suspend fun updateUserAct(
        accessToken: String,
        actId: String,
        updateAcct: UpdateAcct
    ) = apiHelper.updateUserAct(accessToken, actId, updateAcct)
}
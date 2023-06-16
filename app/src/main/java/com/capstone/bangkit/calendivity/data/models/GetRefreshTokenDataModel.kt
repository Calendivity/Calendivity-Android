package com.capstone.bangkit.calendivity.data.models

import com.google.gson.annotations.SerializedName

data class GetRefreshTokenDataModel(
	@field:SerializedName("access_token")
	val accessToken: String? = null
)

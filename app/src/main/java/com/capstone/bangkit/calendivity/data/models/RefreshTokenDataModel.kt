package com.capstone.bangkit.calendivity.data.models

import com.google.gson.annotations.SerializedName

data class RefreshTokenDataModel(
	@field:SerializedName("accessToken")
	val accessToken: String? = null,

	@field:SerializedName("refreshToken")
	val refreshToken: String? = null
)

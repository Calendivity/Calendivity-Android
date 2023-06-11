package com.capstone.bangkit.calendivity.data.models

import com.google.gson.annotations.SerializedName

data class AuthTokenDataModel(
	@field:SerializedName("access_token")
	val accessToken: String? = null,

	@field:SerializedName("refresh_token")
	val refreshToken: String? = null,

	@field:SerializedName("scope")
	val scope: String? = null,

	@field:SerializedName("expiry_date")
	val expiryDate: Long? = null,

	@field:SerializedName("id_token")
	val idToken: String? = null,

	@field:SerializedName("token_type")
	val tokenType: String? = null
)

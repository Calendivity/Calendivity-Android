package com.capstone.bangkit.calendivity.data.models

import com.google.gson.annotations.SerializedName

data class ListRecomendationDataModel(
	@field:SerializedName("data")
	val data: List<String?>? = null
)

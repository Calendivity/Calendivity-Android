package com.capstone.bangkit.calendivity.data.models

import com.google.gson.annotations.SerializedName

data class AddUserActivityDataModel(

	@field:SerializedName("activityName")
	val activityName: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("startTime")
	val startTime: String? = null,

	@field:SerializedName("endTime")
	val endTime: String? = null
)

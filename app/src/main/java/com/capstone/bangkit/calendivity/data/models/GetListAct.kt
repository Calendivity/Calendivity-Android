package com.capstone.bangkit.calendivity.data.models

import com.google.gson.annotations.SerializedName

data class GetListAct(
    @field:SerializedName("data")
    val data: List<DataItem?>? = null
)

data class DataItem(
    @field:SerializedName("difficulty")
    val difficulty: Int? = null,

    @field:SerializedName("activityId")
    val activityId: String? = null,

    @field:SerializedName("activityName")
    val activityName: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("startTime")
    val startTime: String? = null,

    @field:SerializedName("finish")
    val finish: Boolean? = null,

    @field:SerializedName("endTime")
    val endTime: String? = null,

    @field:SerializedName("exp")
    val exp: Int? = null
)

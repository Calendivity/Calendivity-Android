package com.capstone.bangkit.calendivity.data.models

import com.google.gson.annotations.SerializedName

data class UpdateUserInfoDataModel(
    @field:SerializedName("education")
    val education: String? = null,

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("lastEducation")
    val lastEducation: String? = null,

    @field:SerializedName("employmentType")
    val employmentType: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("job")
    val job: String? = null,

    @field:SerializedName("age")
    val age: String? = null
)

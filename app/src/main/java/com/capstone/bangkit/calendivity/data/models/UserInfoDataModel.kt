package com.capstone.bangkit.calendivity.data.models

import com.google.gson.annotations.SerializedName

data class UserInfoDataModel(
    @field:SerializedName("data")
    val data: Data? = null
)

data class Data(
    @field:SerializedName("education")
    val education: String? = null,

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("lastEducation")
    val lastEducation: String? = null,

    @field:SerializedName("employmentType")
    val employmentType: String? = null,

    @field:SerializedName("level")
    val level: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("job")
    val job: String? = null,

    @field:SerializedName("exp")
    val exp: Int? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("picture")
    val picture: String? = null,

    @field:SerializedName("age")
    val age: String? = null
)

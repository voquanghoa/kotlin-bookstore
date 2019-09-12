package com.quanghoa.bookstore.models.dto

import com.google.gson.annotations.SerializedName
import javax.validation.constraints.NotBlank

data class Signup(
        @field:NotBlank val username: String,
        @field:NotBlank val password: String,
        @field:NotBlank @SerializedName("full_name") val fullName: String
)
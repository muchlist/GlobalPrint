package com.laily.globalprint.data


import com.squareup.moshi.Json

data class LoginRequest(
    @Json(name = "password")
    val password: String,
    @Json(name = "user_id")
    val userId: String
)
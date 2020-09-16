package com.laily.globalprint.data


import com.squareup.moshi.Json

data class LoginResponse(
    @Json(name = "access_token")
    val accessToken: String,
    @Json(name = "is_admin")
    val isAdmin: Boolean,
    @Json(name = "is_customer")
    val isCustomer: Boolean,
    @Json(name = "is_staff")
    val isStaff: Boolean,
    @Json(name = "name")
    val name: String,
    @Json(name = "user_id")
    val userId: String
)
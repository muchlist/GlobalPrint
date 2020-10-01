package com.laily.globalprint.data


import com.squareup.moshi.Json

data class UserRequest(
    @Json(name = "address")
    val address: String,
    @Json(name = "is_admin")
    val isAdmin: Boolean,
    @Json(name = "is_customer")
    val isCustomer: Boolean,
    @Json(name = "is_staff")
    val isStaff: Boolean,
    @Json(name = "join_date")
    val joinDate: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "password")
    val password: String,
    @Json(name = "phone")
    val phone: String,
    @Json(name = "position")
    val position: String,
    @Json(name = "user_id")
    val userId: String
)
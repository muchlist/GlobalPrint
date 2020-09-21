package com.laily.globalprint.data


import com.squareup.moshi.Json

data class UserListResponse(
    @Json(name = "user")
    val user: List<User>
) {
    data class User(
        @Json(name = "address")
        val address: String,
        @Json(name = "_id")
        val id: String,
        @Json(name = "is_admin")
        val isAdmin: Boolean,
        @Json(name = "is_customer")
        val isCustomer: Boolean,
        @Json(name = "is_staff")
        val isStaff: Boolean,
        @Json(name = "join_date")
        val joinDate: String = "",
        @Json(name = "name")
        val name: String,
        @Json(name = "phone")
        val phone: String,
        @Json(name = "position")
        val position: String,
        @Json(name = "user_id")
        val userId: String
    )
}
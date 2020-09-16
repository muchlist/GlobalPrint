package com.laily.globalprint.data

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class ErrorResponse(
    @SerializedName("msg")
    @Json(name = "msg")
    val msg: String
)
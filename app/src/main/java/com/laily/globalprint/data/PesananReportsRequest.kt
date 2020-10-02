package com.laily.globalprint.data


import com.squareup.moshi.Json

data class PesananReportsRequest(
    @Json(name = "end_date")
    val endDate: String,
    @Json(name = "start_date")
    val startDate: String
)
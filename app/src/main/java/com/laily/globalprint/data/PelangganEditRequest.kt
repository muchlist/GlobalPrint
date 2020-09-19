package com.laily.globalprint.data


import com.squareup.moshi.Json

data class PelangganEditRequest(
    @Json(name = "alamat")
    val alamat: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "hp")
    val hp: String,
    @Json(name = "nama")
    val nama: String,
    @Json(name = "timestamp_filter")
    val timestampFilter: String
)
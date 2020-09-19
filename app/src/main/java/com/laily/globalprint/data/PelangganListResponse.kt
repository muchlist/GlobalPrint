package com.laily.globalprint.data


import com.squareup.moshi.Json

data class PelangganListResponse(
    @Json(name = "pelanggan")
    val pelanggan: List<PelangganDetailResponse>
)
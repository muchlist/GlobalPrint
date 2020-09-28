package com.laily.globalprint.data


import com.squareup.moshi.Json

data class PesananListResponse(
    @Json(name = "pesanan")
    val pesanan: List<PesananDetailResponse>
)
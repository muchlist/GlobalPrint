package com.laily.globalprint.data


import com.squareup.moshi.Json

data class BahanListResponse(
    @Json(name = "bahan")
    val bahan: List<BahanDetailResponse>
)
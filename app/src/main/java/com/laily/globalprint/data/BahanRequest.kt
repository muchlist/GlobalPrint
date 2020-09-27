package com.laily.globalprint.data


import com.squareup.moshi.Json

data class BahanRequest(
    @Json(name = "harga")
    val harga: Int,
    @Json(name = "nama")
    val nama: String,
    @Json(name = "punya_dimensi")
    val punyaDimensi: Boolean,
    @Json(name = "satuan")
    val satuan: String,
    @Json(name = "spek")
    val spek: String
)
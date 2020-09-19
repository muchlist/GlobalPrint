package com.laily.globalprint.data


import com.squareup.moshi.Json

data class PelangganRequest(
    @Json(name = "alamat")
    val alamat: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "hp")
    val hp: String,
    @Json(name = "id_pelanggan")
    val idPelanggan: String,
    @Json(name = "nama")
    val nama: String
)
package com.laily.globalprint.data


import com.squareup.moshi.Json

data class CrudRequest(
    @Json(name = "alamat")
    val alamat: String,
    @Json(name = "keterangan")
    val keterangan: String,
    @Json(name = "nama")
    val nama: String
)
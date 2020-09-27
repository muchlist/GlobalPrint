package com.laily.globalprint.data


import com.squareup.moshi.Json

data class CrudListResponse(
    @Json(name = "crud")
    val crud: List<Crud>
) {
    data class Crud(
        @Json(name = "alamat")
        val alamat: String,
        @Json(name = "_id")
        val id: String,
        @Json(name = "keterangan")
        val keterangan: String,
        @Json(name = "nama")
        val nama: String
    )
}
package com.laily.globalprint.data


import com.squareup.moshi.Json

data class PelangganDetailResponse(
    @Json(name = "aktif")
    val aktif: Boolean,
    @Json(name = "alamat")
    val alamat: String,
    @Json(name = "dibuat")
    val dibuat: String,
    @Json(name = "dibuat_oleh")
    val dibuatOleh: String,
    @Json(name = "diupdate")
    val diupdate: String,
    @Json(name = "diupdate_oleh")
    val diupdateOleh: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "hp")
    val hp: String,
    @Json(name = "_id")
    val id: String,
    @Json(name = "id_pelanggan")
    val idPelanggan: String,
    @Json(name = "nama")
    val nama: String,
    @Json(name = "total_hutang")
    val totalHutang: Int,
    @Json(name = "total_transaksi_lunas")
    val totalTransaksiLunas: Int
)
package com.laily.globalprint.data

import com.squareup.moshi.Json

data class PesananDetailResponse(
    @Json(name = "bahan")
    val bahan: Bahan,
    @Json(name = "biaya")
    val biaya: Biaya,
    @Json(name = "dibuat")
    val dibuat: String,
    @Json(name = "dibuat_oleh")
    val dibuatOleh: String,
    @Json(name = "diupdate")
    val diupdate: String,
    @Json(name = "diupdate_oleh")
    val diupdateOleh: String,
    @Json(name = "_id")
    val id: String,
    @Json(name = "id_petugas")
    val idPetugas: String,
    @Json(name = "nama_file")
    val namaFile: String,
    @Json(name = "nama_pesanan")
    val namaPesanan: String,
    @Json(name = "pelanggan")
    val pelanggan: Pelanggan,
    @Json(name = "petugas")
    val petugas: String
) {
    data class Bahan(
        @Json(name = "finishing")
        val finishing: String,
        @Json(name = "harga_bahan")
        val hargaBahan: Int,
        @Json(name = "id_bahan")
        val idBahan: String,
        @Json(name = "nama_bahan")
        val namaBahan: String,
        @Json(name = "qty")
        val qty: Int,
        @Json(name = "ukuran_x")
        val ukuranX: Int,
        @Json(name = "ukuran_y")
        val ukuranY: Int
    )

    data class Biaya(
        @Json(name = "apakah_lunas")
        val apakahLunas: Boolean,
        @Json(name = "sisa_bayar")
        val sisaBayar: Int,
        @Json(name = "total_bayar")
        val totalBayar: Int,
        @Json(name = "uang_muka")
        val uangMuka: Int
    )

    data class Pelanggan(
        @Json(name = "id_pelanggan")
        val idPelanggan: String,
        @Json(name = "nama_pelanggan")
        val namaPelanggan: String
    )
}
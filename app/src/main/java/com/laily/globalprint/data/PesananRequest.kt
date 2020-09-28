package com.laily.globalprint.data


import com.squareup.moshi.Json

data class PesananRequest(
    @Json(name = "apakah_lunas")
    val apakahLunas: Boolean,
    @Json(name = "finishing")
    val finishing: String,
    @Json(name = "id_bahan")
    val idBahan: String,
    @Json(name = "id_pelanggan")
    val idPelanggan: String,
    @Json(name = "nama_file")
    val namaFile: String,
    @Json(name = "nama_pesanan")
    val namaPesanan: String,
    @Json(name = "qty")
    val qty: Int,
    @Json(name = "uang_muka")
    val uangMuka: Int,
    @Json(name = "ukuran_x")
    val ukuranX: Int,
    @Json(name = "ukuran_y")
    val ukuranY: Int
) {
    fun validasi(): Boolean {
        if (ukuranX < 0 || ukuranY < 0 || qty < 0 || uangMuka < 0) {
            return false
        }
        if (idBahan.isEmpty() || idPelanggan.isEmpty() || namaFile.isEmpty() || namaPesanan.isEmpty()) {
            return false
        }
        return true
    }
}
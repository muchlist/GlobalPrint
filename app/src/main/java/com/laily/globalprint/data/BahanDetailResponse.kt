package com.laily.globalprint.data


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BahanDetailResponse(
    @Json(name = "diupdate")
    val diupdate: String,
    @Json(name = "diupdate_oleh")
    val diupdateOleh: String,
    @Json(name = "harga")
    val harga: Int,
    @Json(name = "_id")
    val id: String,
    @Json(name = "image")
    val image: String,
    @Json(name = "nama")
    val nama: String,
    @Json(name = "punya_dimensi")
    val punyaDimensi: Boolean,
    @Json(name = "satuan")
    val satuan: String,
    @Json(name = "spek")
    val spek: String
): Parcelable
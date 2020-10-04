package com.laily.globalprint.data


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CrudRequest(
    @Json(name = "alamat")
    val alamat: String,
    @Json(name = "keterangan")
    val keterangan: String,
    @Json(name = "nama")
    val nama: String
): Parcelable
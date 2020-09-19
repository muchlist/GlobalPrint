package com.laily.globalprint.service

import com.laily.globalprint.data.*
import com.laily.globalprint.utils.INTERNET_SERVER
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private var BASE_URL = INTERNET_SERVER

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {

    /* LOGIN -------------------------------------------------------------------
    */

    //USER ---------------------------------------------------------------------
    @POST("/api/login")
    fun login(
        @Body args: LoginRequest,
        @Header("Content-Type") contentType: String = "application/json"
    ): Call<LoginResponse>

    // PELANGGAN --------------------------------------------------------------
    @POST("/api/pelanggan")
    fun membuatPelanggan(
        @Body args: PelangganRequest,
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") token: String,
    ): Call<MessageResponse>

    @GET("/api/pelanggan")
    fun mengambilListPelanggan(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") token: String,
        @Query("nama") nama: String = "",
    ): Call<PelangganListResponse>

    @GET("/api/pelanggan/{id}")
    fun mengambilDetailPelanggan(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): Call<PelangganDetailResponse>

    @PUT("/api/pelanggan/{id}")
    fun mengeditPelanggan(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body args: PelangganEditRequest,
    ): Call<PelangganDetailResponse>

    @DELETE("/api/pelanggan/{id}")
    fun menghapusPelanggan(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<ResponseBody>

    //{{url}}/api/pelanggan/5f5ae6a8ab2b02da096413a0/aktif
    @GET("/api/pelanggan/{id}/aktif")
    fun mengaktifkanPelanggan(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") token: String,
        @Path("id") id: String
    ):  Call<MessageResponse>

}

object Api {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
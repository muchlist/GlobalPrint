package com.laily.globalprint.service

import com.laily.globalprint.data.*
import com.laily.globalprint.utils.INTERNET_SERVER
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.RequestBody
import okhttp3.Response
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

    @GET("/api/users")
    fun mengambilListUser(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") token: String,
    ): Call<UserListResponse>

    @POST("/api/admin/register")
    fun register(
        @Body args: UserRequest,
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") token: String
    ): Call<MessageResponse>


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
    ): Call<MessageResponse>

    // ---------------------------------------- BAHAN
    // {{url}}/api/bahan
    @POST("/api/bahan")
    fun membuatBahan(
        @Body args: BahanRequest,
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") token: String,
    ): Call<MessageResponse>

    @GET("/api/bahan")
    fun mengambilListBahan(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") token: String,
        @Query("nama") nama: String = "",
    ): Call<BahanListResponse>

    @GET("/api/bahan/{id}")
    fun mengambilDetailBahan(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): Call<BahanDetailResponse>

    @PUT("/api/bahan/{id}")
    fun mengeditBahan(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body args: BahanEditRequest,
    ): Call<BahanDetailResponse>

    @DELETE("/api/bahan/{id}")
    fun menghapusBahan(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<ResponseBody>

    //{{url}}/api/bahan/5f5b72aca7ade257c3e07738/upload
    @Multipart
    @POST("/api/bahan/{id}/upload")
    fun uploadGambarBahan(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Part("image\"; filename=\"pp.jpg\" ") image: RequestBody
    ): Call<BahanDetailResponse>

    //----------------------------------------- PESANAN
    @POST("/api/pesanan")
    fun membuatPesanan(
        @Body args: PesananRequest,
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") token: String,
    ): Call<MessageResponse>

    //{{url}}/api/pesanan?nama=&lunas=0&pelanggan=&bahan=   //nama pelanggan
    @GET("/api/pesanan")
    fun mengambilListPesanan(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") token: String,
        @Query("nama") nama: String = "",
        @Query("lunas") lunas: String = "",
        @Query("pelanggan") pelanggan: String = "",
        @Query("bahan") bahan: String = "",
    ): Call<PesananListResponse>

    @GET("/api/pesanan/{id}")
    fun mengambilDetailPesanan(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): Call<PesananDetailResponse>

    //{{url}}/api/pesanan/13R92020-3/lunas
    @GET("/api/pesanan/{id}/lunas")
    fun lunasiDetailPesanan(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): Call<PesananDetailResponse>

    //{{url}}/api/pesanan/1292020-2   //delete
    @DELETE("/api/pesanan/{id}")
    fun hapusDetailPesanan(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): Call<ResponseBody>



    //----------------------------------------- REPORTS
    //{{url}}/api/pelanggan-reports
    @POST("/api/pelanggan-reports")
    fun reportsPelanggan(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") token: String,
    ): Call<MessageResponse>

    //{{url}}/api/bahan-reports
    @GET("/api/bahan-reports")
    fun reportsBahan(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") token: String,
    ): Call<MessageResponse>

    //{{url}}/api/pesanan-reports?nama=&lunas=&pelanggan=&bahan=
    @POST("/api/pesanan-reports")
    fun reportsPesanan(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") token: String,
        @Query("nama") nama: String = "",
        @Query("lunas") lunas: String = "", //1 lunas 0 piutang
        @Query("pelanggan") pelanggan: String = "",
        @Query("bahan") bahan: String = "",
        @Body args: PesananReportsRequest,
    ): Call<MessageResponse>

    @GET("/api/pesanan-reports/{id}")
    fun reportsPesananNota(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): Call<MessageResponse>



    // ---------------------------------------- CRUD
    @POST("/api/crud")
    fun membuatCrud(
        @Body args: CrudRequest,
        @Header("Content-Type") contentType: String = "application/json",
    ): Call<MessageResponse>

    @GET("/api/crud")
    fun mengambilListCrud(
        @Header("Content-Type") contentType: String = "application/json",
    ): Call<CrudListResponse>

    @PUT("/api/crud/{id}")
    fun mengeditCrud(
        @Header("Content-Type") contentType: String = "application/json",
        @Path("id") id: String,
        @Body args: CrudRequest
    ): Call<CrudListResponse.Crud>

    @DELETE("/api/crud/{id}")
    fun menghapusCrud(
        @Header("Content-Type") contentType: String = "application/json",
        @Path("id") id: String,
    ): Call<ResponseBody>

}

object Api {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
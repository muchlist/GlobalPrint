package com.laily.globalprint.repository

import com.laily.globalprint.data.*
import com.laily.globalprint.service.Api
import com.laily.globalprint.service.ApiService
import com.laily.globalprint.utils.App
import com.laily.globalprint.utils.ERR_CONN
import com.laily.globalprint.utils.ERR_JSON_PARSING
import com.laily.globalprint.utils.JsonMarshaller
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object BahanRepo {
    private val apiService: ApiService = Api.retrofitService

    fun getBahan(
        bahanID: String,
        callback: (response: BahanDetailResponse?, error: String) -> Unit
    ) {
        apiService.mengambilDetailBahan(
            token = App.prefs.authTokenSave,
            id = bahanID
        ).enqueue(object : Callback<BahanDetailResponse> {
            override fun onResponse(
                call: Call<BahanDetailResponse>,
                response: Response<BahanDetailResponse>
            ) {
                when {
                    response.isSuccessful -> {
                        callback(response.body(), "")
                    }
                    response.code() == 400 || response.code() == 500 -> {
                        val responseBody = response.errorBody()?.string() ?: ""
                        callback(
                            null,
                            getMsgFromJson(responseBody)
                        )
                    }
                    else -> {
                        callback(null, response.code().toString())
                    }
                }
            }

            override fun onFailure(call: Call<BahanDetailResponse>, t: Throwable) {
                t.message?.let {
                    if (it.contains("to connect")){
                        callback(null, ERR_CONN)
                    } else {
                        callback(null, it)
                    }
                }
            }
        })
    }

    fun retrieveBahan(
        nama: String?,
        callback: (response: BahanListResponse?, error: String) -> Unit
    ) {
        apiService.mengambilListBahan(
            token = App.prefs.authTokenSave,
            nama = nama?:""
        ).enqueue(object : Callback<BahanListResponse> {
            override fun onResponse(
                call: Call<BahanListResponse>,
                response: Response<BahanListResponse>
            ) {
                when {
                    response.isSuccessful -> {
                        callback(response.body(), "")
                    }
                    response.code() == 400 || response.code() == 500 -> {
                        val responseBody = response.errorBody()?.string() ?: ""
                        callback(
                            null,
                            getMsgFromJson(responseBody)
                        )
                    }
                    response.code() == 422 || response.code() == 401 -> {
                        callback(null, "Token Expired")
                        App.prefs.authTokenSave = ""
                    }
                    else -> {
                        callback(null, response.code().toString())
                    }
                }
            }

            override fun onFailure(call: Call<BahanListResponse>, t: Throwable) {
                t.message?.let {
                    if (it.contains("to connect")){
                        callback(null, ERR_CONN)
                    } else {
                        callback(null, it)
                    }
                }
            }
        })
    }

    fun createBahan(
        args: BahanRequest,
        callback: (response: MessageResponse?, error: String) -> Unit
    ) {
        apiService.membuatBahan(
            token = App.prefs.authTokenSave,
            args = args
        ).enqueue(object : Callback<MessageResponse> {
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                when {
                    response.isSuccessful -> {
                        callback(response.body(), "")
                    }
                    response.code() == 400 || response.code() == 500 -> {
                        val responseBody = response.errorBody()?.string() ?: ""
                        callback(
                            null,
                            getMsgFromJson(responseBody)
                        )
                    }
                    else -> {
                        callback(null, response.code().toString())
                    }
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                t.message?.let {
                    if (it.contains("to connect")){
                        callback(null, ERR_CONN)
                    } else {
                        callback(null, it)
                    }
                }
            }
        })
    }

    fun editBahan(
        bahanID: String,
        args: BahanEditRequest,
        callback: (response: BahanDetailResponse?, error: String) -> Unit
    ) {
        apiService.mengeditBahan(
            token = App.prefs.authTokenSave,
            id = bahanID,
            args = args
        ).enqueue(object : Callback<BahanDetailResponse> {
            override fun onResponse(
                call: Call<BahanDetailResponse>,
                response: Response<BahanDetailResponse>
            ) {
                when {
                    response.isSuccessful -> {
                        callback(response.body(), "")
                    }
                    response.code() == 400 || response.code() == 500 -> {
                        val responseBody = response.errorBody()?.string() ?: ""
                        callback(
                            null,
                            getMsgFromJson(responseBody)
                        )
                    }
                    else -> {
                        callback(null, response.code().toString())
                    }
                }
            }

            override fun onFailure(call: Call<BahanDetailResponse>, t: Throwable) {
                t.message?.let {
                    if (it.contains("to connect")){
                        callback(null, ERR_CONN)
                    } else {
                        callback(null, it)
                    }
                }
            }
        })
    }


    fun deleteBahan(
        bahanID: String,
        callback: (success: String, error: String) -> Unit
    ) {
        apiService.menghapusBahan(
            token = App.prefs.authTokenSave,
            id = bahanID
        ).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                when {
                    response.isSuccessful -> {
                        callback("Bahan berhasil dihapus", "")
                    }
                    response.code() == 400 || response.code() == 500 -> {
                        val responseBody = response.errorBody()?.string() ?: ""
                        callback(
                            "",
                            getMsgFromJson(responseBody)
                        )
                    }
                    else -> {
                        callback("", response.code().toString())
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.message?.let {
                    if (it.contains("to connect")){
                        callback("", ERR_CONN)
                    } else {
                        callback("", it)
                    }
                }
            }
        })
    }


    fun uploadGambarBahan(
        bahanID: String,
        imageFile: RequestBody,
        callback: (response: BahanDetailResponse?, error: String) -> Unit
    ) {
        apiService.uploadGambarBahan(
            token = App.prefs.authTokenSave,
            id = bahanID,
            image = imageFile
        ).enqueue(object : Callback<BahanDetailResponse> {
            override fun onResponse(
                call: Call<BahanDetailResponse>,
                response: Response<BahanDetailResponse>
            ) {
                when {
                    response.isSuccessful -> {
                        callback(response.body(), "")
                    }
                    response.code() == 400 || response.code() == 500 -> {
                        val responseBody = response.errorBody()?.string() ?: ""
                        callback(
                            null,
                            getMsgFromJson(responseBody)
                        )
                    }
                    else -> {
                        callback(null, response.code().toString())
                    }
                }
            }

            override fun onFailure(call: Call<BahanDetailResponse>, t: Throwable) {
                t.message?.let {
                    if (it.contains("to connect")) {
                        callback(null, ERR_CONN)
                    } else {
                        callback(null, it)
                    }
                }
            }
        })
    }


    fun getReportBahan(
        callback: (response: MessageResponse?, error: String) -> Unit
    ) {
        apiService.reportsBahan(
            token = App.prefs.authTokenSave,
        ).enqueue(object : Callback<MessageResponse> {
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                when {
                    response.isSuccessful -> {
                        callback(response.body(), "")
                    }
                    response.code() == 400 || response.code() == 500 -> {
                        val responseBody = response.errorBody()?.string() ?: ""
                        callback(
                            null,
                            getMsgFromJson(responseBody)
                        )
                    }
                    else -> {
                        callback(null, response.code().toString())
                    }
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                t.message?.let {
                    if (it.contains("to connect")){
                        callback(null, ERR_CONN)
                    } else {
                        callback(null, it)
                    }
                }
            }
        })
    }


    private fun getMsgFromJson(errorBody: String): String {
        val jsonMarshaller = JsonMarshaller()
        return jsonMarshaller.getError(errorBody)?.msg ?: ERR_JSON_PARSING
    }
}
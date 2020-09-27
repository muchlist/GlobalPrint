package com.laily.globalprint.repository

import com.laily.globalprint.data.CrudListResponse
import com.laily.globalprint.data.CrudRequest
import com.laily.globalprint.data.MessageResponse
import com.laily.globalprint.service.Api
import com.laily.globalprint.service.ApiService
import com.laily.globalprint.utils.ERR_CONN
import com.laily.globalprint.utils.ERR_JSON_PARSING
import com.laily.globalprint.utils.JsonMarshaller
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CrudRepo {
    private val apiService: ApiService = Api.retrofitService

    fun retrieveCrud(
        callback: (response: CrudListResponse?, error: String) -> Unit
    ) {
        apiService.mengambilListCrud().enqueue(object : Callback<CrudListResponse> {
            override fun onResponse(
                call: Call<CrudListResponse>,
                response: Response<CrudListResponse>
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

            override fun onFailure(call: Call<CrudListResponse>, t: Throwable) {
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


    fun createCrud(
        args: CrudRequest,
        callback: (response: MessageResponse?, error: String) -> Unit
    ) {
        apiService.membuatCrud(
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
                    if (it.contains("to connect")) {
                        callback(null, ERR_CONN)
                    } else {
                        callback(null, it)
                    }
                }
            }
        })
    }

    fun editCrud(
        crudId: String,
        args: CrudRequest,
        callback: (response: CrudListResponse.Crud?, error: String) -> Unit
    ) {
        apiService.mengeditCrud(
            id = crudId,
            args = args
        ).enqueue(object : Callback<CrudListResponse.Crud> {
            override fun onResponse(
                call: Call<CrudListResponse.Crud>,
                response: Response<CrudListResponse.Crud>
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

            override fun onFailure(call: Call<CrudListResponse.Crud>, t: Throwable) {
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


    fun deleteCrud(
        crudId: String,
        callback: (success: String, error: String) -> Unit
    ) {
        apiService.menghapusCrud(
            id = crudId
        ).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                when {
                    response.isSuccessful -> {
                        callback("Pelanggan berhasil dihapus", "")
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
                    if (it.contains("to connect")) {
                        callback("", ERR_CONN)
                    } else {
                        callback("", it)
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
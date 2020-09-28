package com.laily.globalprint.repository

import com.laily.globalprint.data.MessageResponse
import com.laily.globalprint.data.PesananRequest
import com.laily.globalprint.service.Api
import com.laily.globalprint.service.ApiService
import com.laily.globalprint.utils.App
import com.laily.globalprint.utils.ERR_CONN
import com.laily.globalprint.utils.ERR_JSON_PARSING
import com.laily.globalprint.utils.JsonMarshaller
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PesananRepo {
    private val apiService: ApiService = Api.retrofitService

    fun createPesanan(
        args: PesananRequest,
        callback: (response: MessageResponse?, error: String) -> Unit
    ) {
        apiService.membuatPesanan(
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
                    if (it.contains("to connect")) {
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
package com.laily.globalprint.repository

import com.laily.globalprint.data.*
import com.laily.globalprint.service.Api
import com.laily.globalprint.service.ApiService
import com.laily.globalprint.utils.App
import com.laily.globalprint.utils.ERR_CONN
import com.laily.globalprint.utils.ERR_JSON_PARSING
import com.laily.globalprint.utils.JsonMarshaller
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserRepo {
    private val apiService: ApiService = Api.retrofitService

    fun registerUser(
        arg: UserRequest,
        callback: (response: MessageResponse?, error: String) -> Unit
    ) {
        apiService.register(args = arg,token = App.prefs.authTokenSave).enqueue(object : Callback<MessageResponse> {
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

    fun melakukanLogin(
        loginRequest: LoginRequest,
        callback: (response: LoginResponse?, error: String) -> Unit
    ) {
        apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
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

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
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

    fun retrieveUser(
        nama: String?,
        callback: (response: UserListResponse?, error: String) -> Unit
    ) {
        apiService.mengambilListUser(
            token = App.prefs.authTokenSave,
        ).enqueue(object : Callback<UserListResponse> {
            override fun onResponse(
                call: Call<UserListResponse>,
                response: Response<UserListResponse>
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

            override fun onFailure(call: Call<UserListResponse>, t: Throwable) {
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
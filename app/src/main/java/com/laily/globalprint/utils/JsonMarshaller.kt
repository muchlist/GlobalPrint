package com.laily.globalprint.utils

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.laily.globalprint.data.ErrorResponse

class JsonMarshaller {

    fun getError(jsonString: String): ErrorResponse?{
        if (jsonString.isNotEmpty()) {
            return try {
                val options = Gson().fromJson(jsonString, ErrorResponse::class.java)
                options
            } catch (e: JsonParseException) {
                null
            }
        }
        return null
    }
}
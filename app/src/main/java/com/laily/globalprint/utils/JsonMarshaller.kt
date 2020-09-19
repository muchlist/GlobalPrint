package com.laily.globalprint.utils

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.laily.globalprint.data.MessageResponse

class JsonMarshaller {

    fun getError(jsonString: String): MessageResponse?{
        if (jsonString.isNotEmpty()) {
            return try {
                val options = Gson().fromJson(jsonString, MessageResponse::class.java)
                options
            } catch (e: JsonParseException) {
                null
            }
        }
        return null
    }
}
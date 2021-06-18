package com.example.mytest.data.remote

import com.example.mytest.utils.ResourceHelper
import retrofit2.Response
import timber.log.Timber

abstract class RemoteBaseData{
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): ResourceHelper<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return ResourceHelper.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): ResourceHelper<T> {
        Timber.d(message)
        return ResourceHelper.error("Network call has failed for a following reason: $message")
    }
}
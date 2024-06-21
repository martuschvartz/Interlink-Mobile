package com.example.interlink.remote

import android.util.Log
import com.example.interlink.DataSourceException
import com.example.interlink.remote.model.DataContent
import com.example.interlink.remote.model.RemoteError
import com.example.interlink.remote.model.RemoteEvent
import com.example.interlink.remote.model.RemoteResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import java.io.IOException

abstract class RemoteDataSource {
    suspend fun <T : Any> handleApiResponse(
        execute: suspend () -> Response<RemoteResult<T>>
    ): T {
        try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                return body.result
            }
            response.errorBody()?.let {
                val gson = Gson()
                val error = gson.fromJson<RemoteError>(
                    it.string(),
                    object : TypeToken<RemoteError?>() {}.type
                )
                throw DataSourceException(error.code, "", error.description)
            }
            throw DataSourceException(UNEXPECTED_ERROR_CODE, "Missing error", null)
        } catch (e: DataSourceException) {
            throw e
        } catch (e: IOException) {
            throw DataSourceException(
                CONNECTION_ERROR_CODE,
                "Connection error",
                getDetailsFromException(e)
            )
        } catch (e: Exception) {
            throw DataSourceException(
                UNEXPECTED_ERROR_CODE,
                "Unexpected error",
                getDetailsFromException(e)
            )
        }
    }

    suspend fun handleApiEvent(
        execute: suspend () -> Response<RemoteEvent>
    ): DataContent {
        try {
            val response = execute()
            Log.d("DEBUG", "${response.body()}")
            val body = response.body()
            if (response.isSuccessful && body != null) {
                return body.data
            }
            response.errorBody()?.let {
                val gson = Gson()
                val error = gson.fromJson<RemoteError>(
                    it.string(),
                    object : TypeToken<RemoteError?>() {}.type
                )
                throw DataSourceException(error.code, "", error.description)
            }
            throw DataSourceException(UNEXPECTED_ERROR_CODE, "Missing error", null)
        } catch (e: DataSourceException) {
            Log.d("DEBUG", "$e")
            throw e
        } catch (e: IOException) {
            Log.d("DEBUG", "$e")

            throw DataSourceException(
                CONNECTION_ERROR_CODE,
                "Connection error",
                getDetailsFromException(e)
            )
        } catch (e: Exception) {
            Log.d("DEBUG", "$e")

            throw DataSourceException(
                UNEXPECTED_ERROR_CODE,
                "Unexpected error",
                getDetailsFromException(e)
            )
        }
    }

    private fun getDetailsFromException(e: Exception): List<String> {
        return if (e.message != null) listOf(e.message!!) else emptyList()
    }

    companion object {
        const val CONNECTION_ERROR_CODE = 98
        const val UNEXPECTED_ERROR_CODE = 99
    }
}
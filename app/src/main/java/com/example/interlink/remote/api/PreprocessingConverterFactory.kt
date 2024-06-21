package com.example.interlink.remote.api

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

// Esto es lo que se encarga de corregir el desastre que devuelve el endpoint de /events
// No planeo dejar comentarios explicando que hace porque realmente no vale la pena, es basicamente
// solucionar en el front un problema de la api
class PreprocessingConverterFactory(private val gson: Gson) : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val gsonConverter = GsonConverterFactory.create(gson).responseBodyConverter(type, annotations, retrofit)
        return PreprocessingConverter(gsonConverter as Converter<ResponseBody, Any>)
    }


    private class PreprocessingConverter<T>(
        private val delegate: Converter<ResponseBody, T>
    ) : Converter<ResponseBody, T> {

        fun preprocessResponse(input: String): String {
            if (input.isEmpty() || input[0] != 'i') {
                return input
            }

            val regex = Regex("data: \"(\\w+)\": \"([^\"]+)\"")
            val matches = regex.findAll(input)
            val jsonObject = JsonObject()
            for (match in matches) {
                val key = match.groupValues[1]
                val value = match.groupValues[2]
                jsonObject.addProperty(key, value)
            }
            val argsRegex = Regex("data: \"args\": \\{(.+?)\\}")
            val argsMatch = argsRegex.find(input)
            if (argsMatch != null) {
                val argsString = argsMatch.groupValues[1]
                val argsObject = JsonObject()
                val argsKeyValuePairs = argsString.split(",")
                for (pair in argsKeyValuePairs) {
                    val (key, value) = pair.split(":").map { it.trim().replace("\"", "") }
                    argsObject.addProperty(key, value)
                }
                jsonObject.add("args", argsObject)
            }
            val cleaned = Gson().toJson(jsonObject)
            Log.d("DEBUG", cleaned)
            return cleaned
        }

        override fun convert(value: ResponseBody): T? {
            val rawResponse = value.string()
            val cleanedResponse = preprocessResponse(rawResponse)
            val cleanedResponseBody = cleanedResponse.toResponseBody(value.contentType())
            return delegate.convert(cleanedResponseBody)
        }
    }


}

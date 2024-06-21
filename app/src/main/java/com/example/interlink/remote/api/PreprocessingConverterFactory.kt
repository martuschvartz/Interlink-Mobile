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

        private fun preprocessResponse(input: String): String {
            if (input.isEmpty()){
                return "[]"
            }

            if (input[0] != 'i') {
                return input
            }

            val events = mutableListOf<JsonObject>()

            val eventRegex = Regex("id: '(\\d+)\\s+data: \\{(.*?)\\s+data: \\}", RegexOption.DOT_MATCHES_ALL)
            val eventMatches = eventRegex.findAll(input)

            for (eventMatch in eventMatches) {
                val id = eventMatch.groupValues[1]
                val dataBlock = eventMatch.groupValues[2]

                val dataRegex = Regex("data: \"(\\w+)\": \"([^\"]+)\"")
                val matches = dataRegex.findAll(dataBlock)
                val jsonObject = JsonObject()
                jsonObject.addProperty("id", id)
                for (match in matches) {
                    val key = match.groupValues[1]
                    val value = match.groupValues[2]
                    jsonObject.addProperty(key, value)
                }

                val argsRegex = Regex("data: \"args\": \\{(.+?)\\}")
                val argsMatch = argsRegex.find(dataBlock)
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

                events.add(jsonObject)
            }

            val cleanedResponse = Gson().toJson(events)
            Log.d("DEBUG","Cleaned Response: $cleanedResponse") // Debugging
            return cleanedResponse
        }

        override fun convert(value: ResponseBody): T? {
            val rawResponse = value.string()
            val cleanedResponse = preprocessResponse(rawResponse)
            val cleanedResponseBody = cleanedResponse.toResponseBody(value.contentType())
            return delegate.convert(cleanedResponseBody)
        }
    }


}

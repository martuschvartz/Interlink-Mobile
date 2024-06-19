package com.example.interlink

class DataSourceException(
    var code: Int,
    message: String,
    var details: List<String>?
) : Exception(message)
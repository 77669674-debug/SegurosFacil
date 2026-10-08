package com.example.segurosfacil.data.repository

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun Long.toIso8601(): String {
    val formato = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
    formato.timeZone = TimeZone.getTimeZone("UTC")
    return formato.format(Date(this))
}
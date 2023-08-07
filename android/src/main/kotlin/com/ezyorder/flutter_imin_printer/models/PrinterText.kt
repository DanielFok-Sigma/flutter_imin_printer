package com.ezyorder.flutter_imin_printer.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@JsonClass(generateAdapter = true)
data class PrinterText(
    val text: String,
    @Json(name = "textAlign") val textAlign: Int = 1,
    @Json(name = "textSize") val textSize: Int = 28,
    @Json(name = "textStyle") val textStyle: Int = 0,
    @Json(name = "lineSpacing") val lineSpacing: Float = 1.0f
) {

    companion object {
        fun fromJson(json: String): PrinterText? {
            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()

            val adapter = moshi.adapter(PrinterText::class.java)
            return adapter.fromJson(json)
        }
    }
}


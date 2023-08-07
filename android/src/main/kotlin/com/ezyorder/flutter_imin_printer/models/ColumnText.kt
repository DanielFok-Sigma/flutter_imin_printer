package com.ezyorder.flutter_imin_printer.models

import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

data class ColumnText(
    val text: String,
    @Json(name = "textWidth") val textWidth: Int = 1,
    @Json(name = "textAlign") val textAlign: Int = 0,
    @Json(name = "textSize") val textSize: Int = 28,
) {

    companion object {
        fun fromJsonList(json: String): List<ColumnText>? {
            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()

            val type = Types.newParameterizedType(List::class.java, ColumnText::class.java)
            val adapter = moshi.adapter<List<ColumnText>>(type)
            return adapter.fromJson(json)
        }
    }


}

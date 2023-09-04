package com.ezyorder.flutter_imin_printer.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

enum class QRAlignmentMode(val value: Int) {
    LEFT(0),
    CENTER(1),
    RIGHT(2)
}

@JsonClass(generateAdapter = true)
data class QRObj(
    val qrCode: String,
    @Json(name = "alignment") val alignmentMode: QRAlignmentMode = QRAlignmentMode.CENTER,
) {

    companion object {
        fun fromJson(json: String): QRObj? {
            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()

            val adapter = moshi.adapter(QRObj::class.java)
            return adapter.fromJson(json)
        }
    }
}

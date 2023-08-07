package com.ezyorder.flutter_imin_printer.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@JsonClass(generateAdapter = true)
data class BarcodeText(
    val barcode: String,
    @Json(name = "barcodeContentPrint") val barcodeContentPrint: Int = 0,
    @Json(name = "barcodeAlign") val barcodeAlign: Int = 1
) {

    companion object {
        fun fromJson(json: String): BarcodeText? {
            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()

            val adapter = moshi.adapter(BarcodeText::class.java)
            return adapter.fromJson(json)
        }
    }
}

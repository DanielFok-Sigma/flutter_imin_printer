package com.ezyorder.flutter_imin_printer.models


class BarcodeText(
    val barcode: String,
    val barcodeContentPrint: Int = 0,
    val barcodeAlign: Int = 1
) {

    companion object {
        fun fromJson(json: Map<String, Any>): BarcodeText {
            return BarcodeText(
                json["barcode"] as String,
                json["barcodeContentPrint"] as Int,
                json["barcodeAlign"] as Int,
            )
        }
    }
}
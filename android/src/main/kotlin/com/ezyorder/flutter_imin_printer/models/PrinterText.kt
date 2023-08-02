package com.ezyorder.flutter_imin_printer.models

import android.graphics.Typeface

class PrinterText(
    val text: String,
    val textAlign: Int = 1,
    val textSize: Int = 28,
    val textStyle: Int = 0,
    val lineSpacing: Float = 1.0f,
) {

    companion object {
        fun fromJson(json: Map<String, Any>): PrinterText {
            return PrinterText(
                json["text"] as String,
                json["textAlign"] as Int,
                json["textSize"] as Int,
                json["textStyle"] as Int,
                json["lineSpacing"] as Float,
            )
        }
    }
}
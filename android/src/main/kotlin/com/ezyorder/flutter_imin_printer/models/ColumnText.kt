package com.ezyorder.flutter_imin_printer.models


class ColumnText(
    val text: String,
    val textWidth: Int = 1,
    val textAlign: Int = 0,
    val textSize: Int = 28,
) {

    companion object {
        fun fromJson(json: Map<String, Any>): ColumnText {
            return ColumnText(
                json["text"] as String,
                json["textWidth"] as Int,
                json["textAlign"] as Int,
                json["textSize"] as Int,

            )
        }
    }
}
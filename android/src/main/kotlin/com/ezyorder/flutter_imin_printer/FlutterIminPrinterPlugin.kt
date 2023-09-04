package com.ezyorder.flutter_imin_printer

import android.os.Build
import android.util.Log
import androidx.annotation.NonNull
import com.ezyorder.flutter_imin_printer.models.BarcodeText
import com.ezyorder.flutter_imin_printer.models.ColumnText
import com.ezyorder.flutter_imin_printer.models.PrinterText
import com.ezyorder.flutter_imin_printer.models.QRObj
import com.imin.library.IminSDKManager
import com.imin.library.SystemPropManager
import com.imin.printerlib.IminPrintUtils
import com.imin.printerlib.IminPrintUtils.PrintConnectType
import com.imin.printerlib.print.PrintUtils
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** FlutterIminPrinterPlugin */
class FlutterIminPrinterPlugin : FlutterPlugin, MethodCallHandler, FlutterActivity() {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel

    private var mIminPrintUtils: IminPrintUtils? = null
    private var connectType = PrintConnectType.USB


    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_imin_printer")
        channel.setMethodCallHandler(this)
        val applicationContext = flutterPluginBinding.applicationContext
        if (mIminPrintUtils == null) {
            mIminPrintUtils = IminPrintUtils.getInstance(applicationContext)
        }
        mIminPrintUtils?.resetDevice()
    }


    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {


        when (call.method) {
            "getPlatformVersion" -> {
                result.success("Android ${Build.VERSION.RELEASE}")
            }

            "initSDK" -> {

                try {

                    val deviceModel = SystemPropManager.getModel()
                    val brand = SystemPropManager.getBrand()
                    Log.d("FlutterIminPrinter", "initSDK: $deviceModel, $brand")

                    connectType = when {
                        deviceModel.contains("M2-203") || deviceModel.contains("M2-202") || deviceModel.contains(
                            "M2-Pro"
                        ) -> {
                            PrintConnectType.SPI
                        }

                        else -> {
                            PrintConnectType.USB
                        }
                    }
                    mIminPrintUtils?.initPrinter(connectType)
//                    Log.d("IminPrinter", "resetDevice start")
//                    mIminPrintUtils?.resetDevice()
//                    Log.d("IminPrinter", "resetDevice end")

                    Log.d("IminPrinter", "initSDK: $deviceModel, ConnectType: $connectType")

                } catch (e: Exception) {
                    val stackTrace = Log.getStackTraceString(e)
                    Log.d("FlutterIminPrinterError", "initSDK: $e")
                    Log.d("FlutterIminPrinterError", "initSDK: $stackTrace")
                }


                result.success("init")

            }

            "getPrinterStatus" -> {
                when (connectType) {
                    PrintConnectType.USB -> {
                        val status = mIminPrintUtils?.getPrinterStatus(connectType)
                        //针对S1， //0：打印机正常 1：打印机未连接或未上电 3：打印头打开 7：纸尽  8：纸将尽  99：其它错误
                        Log.d("FlutterIminPrinter", " print USB status:$status")
                        result.success(status)
                    }

                    PrintConnectType.SPI -> {
                        mIminPrintUtils?.getPrinterStatus(
                            PrintConnectType.SPI
                        ) { status ->
                            Log.d(
                                "FlutterIminPrinter",
                                "Print SPI status:" + status + "  PrintUtils.getPrintStatus==  " + PrintUtils.getPrintStatus()
                            )
                            if (status == -1 && PrintUtils.getPrintStatus() == -1) {
                                result.success(-1)
                            } else {
                                result.success(status)
                            }
                        }
                    }

                    else -> {
                        result.success(99)
                    }
                }
            }

            "printText" -> {
                val jsonString = call.arguments as String
                val printerText = PrinterText.fromJson(jsonString);

                if (printerText != null) {
                    Log.d("FlutterIminPrinter", "printText: ${printerText.text}")
                    mIminPrintUtils?.setAlignment(printerText.textAlign)
                    mIminPrintUtils?.setTextSize(printerText.textSize)
                    mIminPrintUtils?.setTextStyle(printerText.textStyle)
                    mIminPrintUtils?.setTextLineSpacing(printerText.lineSpacing)
                    mIminPrintUtils?.printText(printerText.text, 1)
                }



                result.success("printText")
            }

            "printBarcode" -> {

                val jsonString = call.arguments as String
                val barcodeText = BarcodeText.fromJson(jsonString);

                if (barcodeText != null) {
                    mIminPrintUtils?.setBarCodeContentPrintPos(barcodeText.barcodeContentPrint);
                    mIminPrintUtils?.printBarCode(73, barcodeText.barcode, barcodeText.barcodeAlign)
                }


                result.success("printBarcode")
            }

            "printSpace" -> {
                mIminPrintUtils?.printAndLineFeed()
                result.success("printSpace")
            }

            "printLines" -> {
                val lines = call.arguments as Int
                mIminPrintUtils?.printAndFeedPaper(lines)
                result.success("printLines")
            }

            "printColumn" -> {

                val jsonString = call.arguments as String


                val colArr: List<ColumnText>? = ColumnText.fromJsonList(jsonString)

                if (colArr != null) {
                    val colArrStrings = colArr.map { it.text }.toTypedArray()
                    val colArrWidth = colArr.map { it.textWidth }.toIntArray()
                    val colArrAlign = colArr.map { it.textAlign }.toIntArray()
                    val colArrSize = colArr.map { it.textSize }.toIntArray()


//                colTextArr –> column text string array
//                colWidthArr –> Array of the width of each column, calculated in English characters,
//                each Chinese character occupies two English characters, each width is greater than 0.
//                colAlign –> alignment: 0 to the left, 1 to the center, and 2 to the right
//                size –> Font size per column string array
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        mIminPrintUtils?.printColumnsText(
                            colArrStrings, colArrWidth, colArrAlign, colArrSize
                        )
                    }

                }

                result.success("printColumn")
            }


            "partialCut" -> {
                mIminPrintUtils?.partialCut()
                result.success("partialCut")
            }

            "setPageFormat" -> {
                val data = call.arguments as Int
                mIminPrintUtils?.setPageFormat(data)
                result.success("setPageFormat")
            }


            "openDrawer" -> {
                IminSDKManager.opencashBox()
                result.success("openDrawer")
            }

            "printQR" -> {

                val jsonString = call.arguments as String
                val qrObj = QRObj.fromJson(jsonString);

                if (qrObj != null) {
                    mIminPrintUtils?.printQrCode(qrObj.qrCode, qrObj.alignmentMode.value)
                }
                result.success("printQR")

            }

            "printHR" -> {
                mIminPrintUtils?.setAlignment(1)
                mIminPrintUtils?.setTextSize(28)
                mIminPrintUtils?.setTextStyle(0)
                mIminPrintUtils?.setTextLineSpacing(1.0f)

                val is58mm = IminPrintUtils.textWidth == 384
                if (is58mm) {
                    mIminPrintUtils?.printText("------------------------------------------------", 1)
                } else {
                    mIminPrintUtils?.printText("------------------------------------------------------------", 1)
                }

                result.success("printHR")
            }


            "printHR2" -> {
                mIminPrintUtils?.setAlignment(1)
                mIminPrintUtils?.setTextSize(28)
                mIminPrintUtils?.setTextStyle(0)
                mIminPrintUtils?.setTextLineSpacing(1.0f)

                val is58mm = IminPrintUtils.textWidth == 384
                if (is58mm) {
                    mIminPrintUtils?.printText("=========================", 1)
                } else {
                    mIminPrintUtils?.printText("====================================", 1)
                }

                result.success("printHR2")
            }


//            "printText2" -> {
//                val text = call.arguments as String
//                Log.d("FlutterIminPrinter", "printText2: $text")
//
//                mIminPrintUtils?.setTextSize(48)
//                mIminPrintUtils?.printText(text, 1)
//                mIminPrintUtils?.printAndFeedPaper(20)
//
//                result.success("printText2")
//            }


//            "printDemo" -> {
//                mIminPrintUtils?.printText("Receipt 1\n")
//
//                mIminPrintUtils?.setAlignment(2)
//                mIminPrintUtils?.printText("Order No?.220411A0015\n")
//
//                mIminPrintUtils?.setTextSize(48)
//                mIminPrintUtils?.setAlignment(1)
//                mIminPrintUtils?.setTextStyle(Typeface.BOLD)
//                mIminPrintUtils?.printText("Delivery charge：15?.00\n")
//
//                mIminPrintUtils?.setTextSize(26)
//                mIminPrintUtils?.setAlignment(0)
//                mIminPrintUtils?.setTextStyle(Typeface.NORMAL)
//                mIminPrintUtils?.printAndFeedPaper(20)
//                mIminPrintUtils?.printAndFeedPaper(5)
//                mIminPrintUtils?.printText("Order No?.220411A0015\n")
//                mIminPrintUtils?.printText("Receipt 2\n")
//                mIminPrintUtils?.setAlignment(2)
//                mIminPrintUtils?.printText("Order No?.220411A0015\n")
//
//                mIminPrintUtils?.setTextSize(48)
//                mIminPrintUtils?.setAlignment(1)
//                mIminPrintUtils?.setTextStyle(Typeface.BOLD)
//                mIminPrintUtils?.printText("Delivery charge:15?.00\n")
//                mIminPrintUtils?.printText("Delivery charge:15?.00\n")
//
//                mIminPrintUtils?.setTextSize(26)
//                mIminPrintUtils?.setAlignment(0)
//                mIminPrintUtils?.setTextStyle(Typeface.NORMAL)
//                mIminPrintUtils?.printAndFeedPaper(20)
//                mIminPrintUtils?.printAndFeedPaper(5)
//                mIminPrintUtils?.printText("Order No.220411A0015\n")
//
//                mIminPrintUtils?.printAndFeedPaper(20)
//                mIminPrintUtils?.printAndFeedPaper(5)
//
//                result.success("printDemo")
//            }


            else -> {
                result.notImplemented()
            }
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        mIminPrintUtils?.unRegisterReceiver()
        mIminPrintUtils?.resetDevice()
        channel.setMethodCallHandler(null)
    }


}

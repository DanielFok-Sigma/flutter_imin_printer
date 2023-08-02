import 'dart:convert';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'flutter_imin_printer_platform_interface.dart';
import 'models/column_text.dart';
import 'models/printer_text.dart';

/// An implementation of [FlutterIminPrinterPlatform] that uses method channels.
class MethodChannelFlutterIminPrinter extends FlutterIminPrinterPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('flutter_imin_printer');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future<void> initSDK() async {
    final result = await methodChannel.invokeMethod<String>('initSDK');
    debugPrint('initSDK result: $result');
  }

  @override
  Future<String?> getPrinterStatus() async {
    final result = await methodChannel.invokeMethod('getPrinterStatus');
    return result.toString();
  }

  @override
  Future<void> printText(PrinterText printerText) async {
    await methodChannel.invokeMethod<void>('printText', jsonEncode(printerText.toJson()));
  }

  @override
  Future<void> printSpace() async {
    await methodChannel.invokeMethod<void>('printSpace');
  }

  @override
  Future<void> printLines({int lines = 1}) async {
    await methodChannel.invokeMethod<void>('printLines', lines);
  }



  @override
  Future<void> printText2(String text) async {
    await methodChannel.invokeMethod<void>('printText2', text);
  }

  @override
  Future<void> printDemo() async {
    await methodChannel.invokeMethod<void>('printDemo');
  }

  @override
  Future<void> printBarcode(String barcode) async {
    await methodChannel.invokeMethod<void>('printBarcode', barcode);
  }

  @override
  Future<void> printColumn(List<ColumnText> column) async {
    await methodChannel.invokeMethod<void>('printColumn', jsonEncode(column.map((e) => e.toJson()).toList()));
  }

  @override
  Future<void> openDrawer() async {
    await methodChannel.invokeMethod<void>('openDrawer');
  }

}

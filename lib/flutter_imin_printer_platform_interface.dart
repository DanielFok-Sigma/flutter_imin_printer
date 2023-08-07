import 'package:flutter_imin_printer/models/barcode_text.dart';
import 'package:flutter_imin_printer/models/printer_text.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'flutter_imin_printer_method_channel.dart';
import 'models/column_text.dart';

abstract class FlutterIminPrinterPlatform extends PlatformInterface {
  /// Constructs a FlutterIminPrinterPlatform.
  FlutterIminPrinterPlatform() : super(token: _token);

  static final Object _token = Object();

  static FlutterIminPrinterPlatform _instance = MethodChannelFlutterIminPrinter();

  /// The default instance of [FlutterIminPrinterPlatform] to use.
  ///
  /// Defaults to [MethodChannelFlutterIminPrinter].
  static FlutterIminPrinterPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [FlutterIminPrinterPlatform] when
  /// they register themselves.
  static set instance(FlutterIminPrinterPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<void> initSDK() {
    throw UnimplementedError('initSDK() has not been implemented.');
  }

  Future<String?> getPrinterStatus() {
    throw UnimplementedError('getPrinterStatus() has not been implemented.');
  }

  Future<void> printText(PrinterText printerText) {
    throw UnimplementedError('printText() has not been implemented.');
  }

  Future<void> printSpace() {
    throw UnimplementedError('printSpace() has not been implemented.');
  }

  Future<void> printLines({int lines = 1}) {
    throw UnimplementedError('printLines() has not been implemented.');
  }

  Future<void> printText2(String text) {
    throw UnimplementedError('printText2() has not been implemented.');
  }

  Future<void> printDemo() {
    throw UnimplementedError('printDemo() has not been implemented.');
  }

  Future<void> printBarcode(BarcodeText barcodeText) {
    throw UnimplementedError('printBarcode() has not been implemented.');
  }

  Future<void> printColumn(List<ColumnText> column) {
    throw UnimplementedError('printColumn() has not been implemented.');
  }

  Future<void> openDrawer() {
    throw UnimplementedError('openDrawer() has not been implemented.');
  }

  Future<void> setPageFormat(int style) {
    throw UnimplementedError('setPageFormat() has not been implemented.');
  }

  Future<void> partialCut() {
    throw UnimplementedError('partialCut() has not been implemented.');
  }


}

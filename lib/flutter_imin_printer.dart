
import 'flutter_imin_printer_platform_interface.dart';
import 'models/column_text.dart';
import 'models/printer_text.dart';

export 'models/column_text.dart';
export 'models/printer_text.dart';



class FlutterIminPrinter {
  Future<String?> getPlatformVersion() {
    return FlutterIminPrinterPlatform.instance.getPlatformVersion();
  }

  //Init SDK
  Future<void> initSDK() async {
    return FlutterIminPrinterPlatform.instance.initSDK();
  }

  //Get Printer Status
  Future<String?> getPrinterStatus() async {
    return FlutterIminPrinterPlatform.instance.getPrinterStatus();
  }

  //Print Text
  Future<void> printText(PrinterText printerText) async {
    return FlutterIminPrinterPlatform.instance.printText(printerText);
  }

  //Print Space
  Future<void> printSpace() async {
    return FlutterIminPrinterPlatform.instance.printSpace();
  }

  //Print Lines
  Future<void> printLines({int lines = 1}) async {
    return FlutterIminPrinterPlatform.instance.printLines(lines: lines);
  }

  //Print Text 2
  Future<void> printText2(String text) async {
    return FlutterIminPrinterPlatform.instance.printText2(text);
  }

  //Print Demo
  Future<void> printDemo() async {
    return FlutterIminPrinterPlatform.instance.printDemo();
  }

  //Print Barcode
  Future<void> printBarcode(String barcode) async {
    return FlutterIminPrinterPlatform.instance.printBarcode(barcode);
  }

  //Print Column
  Future<void> printColumn(List<ColumnText> column) async {
    return FlutterIminPrinterPlatform.instance.printColumn(column);
  }

  //Partial Cut
  Future<void> partialCut() async {
    return FlutterIminPrinterPlatform.instance.partialCut();
  }

  //Open Drawer
  Future<void> openDrawer() async {
    return FlutterIminPrinterPlatform.instance.openDrawer();
  }

}

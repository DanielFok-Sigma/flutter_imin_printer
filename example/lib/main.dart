import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_imin_printer/flutter_imin_printer.dart';
import 'package:flutter_imin_printer/models/barcode_text.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  String printerStatus = 'Unknown';
  final _flutterIminPrinterPlugin = FlutterIminPrinter();

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    // We also handle the message potentially returning null.
    try {
      platformVersion = await _flutterIminPrinterPlugin.getPlatformVersion() ?? 'Unknown platform version';
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              Text('Running on: $_platformVersion\n'),
              ElevatedButton(
                onPressed: () async {
                  await _flutterIminPrinterPlugin.initSDK();
                },
                child: const Text("initSDK"),
              ),
              ElevatedButton(
                onPressed: () async {
                  final result = await _flutterIminPrinterPlugin.getPrinterStatus();
                  debugPrint('getPrinterStatus result: $result');
                  setState(() {
                    printerStatus = result ?? 'Unknown';
                  });
                },
                child: Text("getPrinterStatus :$printerStatus"),
              ),
              //Print Text
              ElevatedButton(
                onPressed: () async {
                  await _flutterIminPrinterPlugin.printText(PrinterText(text: "Hello World Imin Printer"));
                  await _flutterIminPrinterPlugin.printSpace();
                  await _flutterIminPrinterPlugin.printText(PrinterText(text: "Hello World Imin Printer 2", textSize: 18));
                  await _flutterIminPrinterPlugin.printLines(lines: 25);
                },
                child: const Text("printText"),
              ),
              //Print Text 2
              // ElevatedButton(
              //   onPressed: () async {
              //     await _flutterIminPrinterPlugin.printText2("Hello World Imin Printer sadas ");
              //   },
              //   child: const Text("printText2"),
              // ),
              //Print Demo
              // ElevatedButton(
              //   onPressed: () async {
              //     await _flutterIminPrinterPlugin.printDemo();
              //   },
              //   child: const Text("printDemo"),
              // ),
              //Print Barcode
              ElevatedButton(
                onPressed: () async {
                  BarcodeText barcodeText = BarcodeText(barcode: "{B012345678912",barcodeContentPrint: 2);
                  await _flutterIminPrinterPlugin.printBarcode(barcodeText);
                  await _flutterIminPrinterPlugin.printSpace();
                },
                child: const Text("printBarcode"),
              ),
              //Print Lines
              ElevatedButton(
                onPressed: () async {
                  await _flutterIminPrinterPlugin.printLines(lines: 100);
                },
                child: const Text("printLines"),
              ),

              //Print Column
              ElevatedButton(
                onPressed: () async {
                  List<ColumnText> column = [
                    ColumnText(text: "Hello World Imin Printer rerrsadasdsdadasd", textWidth: 1, textAlign: 0, textSize: 18),
                    ColumnText(text: "RM 10000.00", textWidth: 1, textAlign: 2, textSize: 20),
                  ];
                  await _flutterIminPrinterPlugin.printColumn(column);
                  await _flutterIminPrinterPlugin.printText(PrinterText(text: 'testing'));
                  await _flutterIminPrinterPlugin.printColumn(column);
                  await _flutterIminPrinterPlugin.printLines(lines: 120);
                },
                child: const Text("printColumn"),
              ),
              //Open Drawer
              ElevatedButton(
                onPressed: () async {
                  await _flutterIminPrinterPlugin.openDrawer();
                },
                child: const Text("openDrawer"),
              ),
              //Print ----------------------------------------
              ElevatedButton(
                onPressed: () async {
                  await _flutterIminPrinterPlugin.printText(PrinterText(text: '------------------------------------------------'));
                  await _flutterIminPrinterPlugin.printLines(lines: 120);
                },
                child: const Text("---------"),
              ),
              ElevatedButton(
                onPressed: () async {
                  await _flutterIminPrinterPlugin.printText(PrinterText(text: "这个是个测试文本", textSize: 18));
                  List<ColumnText> column = [
                    ColumnText(text: "番茄", textWidth: 2, textAlign: 0, textSize: 18),
                    ColumnText(text: "10块", textWidth: 2, textAlign: 2, textSize: 20),
                  ];
                  await _flutterIminPrinterPlugin.printColumn(column);
                  await _flutterIminPrinterPlugin.printLines(lines: 25);
                },
                child: const Text("这个是个测试文本"),
              ),

              //Print Chinese Text


            ],
          ),
        ),
      ),
    );
  }
}

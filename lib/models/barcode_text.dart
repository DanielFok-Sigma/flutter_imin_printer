class BarcodeText {
  final String barcode;
  // position –> position HRI character printing position
  // 0 –> Do not print
  // 1 –> Above the barcode
  // 2 –> Below the barcode
  // 3 –> Barcodes are printed above and below
  final int barcodeContentPrint;
  // 0 = left / 1 = center / 2 = right / default = 0
  final int barcodeAlign;


  BarcodeText({
    required this.barcode,
    this.barcodeContentPrint = 0,
    this.barcodeAlign = 1,
  });

  factory BarcodeText.fromJson(Map<String, dynamic> json) {
    return BarcodeText(
      barcode: json['barcode'] as String,
      barcodeContentPrint: json['barcodeContentPrint'] as int,
      barcodeAlign: json['barcodeAlign'] as int,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'barcode': barcode,
      'barcodeContentPrint': barcodeContentPrint,
      'barcodeAlign': barcodeAlign,
    };
  }
}

class PrinterText {
  final String text;
  // 0 = left / 1 = center / 2 = right / default = 0
  final int textAlign;
  // default 28
  final int textSize;
  // NORMAL = 0 BOLD = 1 ITALIC = 2 BOLD_ITALIC = 3
  final int textStyle;
  // default 1.0f
  final double lineSpacing;

  PrinterText({
    required this.text,
    this.textAlign = 0,
    this.textSize = 28,
    this.textStyle = 0,
    this.lineSpacing = 1.0,
  });

  factory PrinterText.fromJson(Map<String, dynamic> json) {
    return PrinterText(
      text: json['text'] as String,
      textAlign: json['textAlign'] as int,
      textSize: json['textSize'] as int,
      textStyle: json['textStyle'] as int,
      lineSpacing: json['lineSpacing'] as double,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'text': text,
      'textAlign': textAlign,
      'textSize': textSize,
      'textStyle': textStyle,
      'lineSpacing': lineSpacing,
    };
  }
}

class ColumnText{
  final String text;
final int textWidth;
  final int textAlign;
  final int textSize;


  ColumnText({
    required this.text,
    this.textWidth = 1,
    this.textAlign = 0,
    this.textSize = 28,
  });

  factory ColumnText.fromJson(Map<String, dynamic> json) {
    return ColumnText(
      text: json['text'] as String,
      textWidth: json['textWidth'] as int,
      textAlign: json['textAlign'] as int,
      textSize: json['textSize'] as int,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'text': text,
      'textWidth': textWidth,
      'textAlign': textAlign,
      'textSize': textSize,
    };
  }
}
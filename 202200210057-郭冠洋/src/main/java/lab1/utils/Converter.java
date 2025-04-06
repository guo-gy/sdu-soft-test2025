package lab1.utils;

public class Converter {
  /**
   * 将十进制数字转为二进制数字。
   * @param dec 十进制数字串，可能很长。
   * @return 二进制数字串
   */
  public static String decToBin(String dec) {
    return Integer.toBinaryString(Integer.parseInt(dec));
  }

  /**
   * 将十进制数字转为十六进制数字。
   * @param dec 十进制数字串，可能很长。
   * @return 十六进制数字串
   */
  public static String decToHex(String dec) {
    return Integer.toHexString(Integer.parseInt(dec));
  }
}

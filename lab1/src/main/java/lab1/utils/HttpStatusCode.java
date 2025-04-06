package lab1.utils;

public enum HttpStatusCode {
  OK(200, "成功"),
  NOT_FOUND(404, "资源未找到"),
  SERVER_ERROR(500, "服务器错误");

  private final int code;
  private final String info;

  HttpStatusCode(int code, String message) {
    this.code = code;
    this.info = message;
  }

  public int getCode() {
    return code;
  }

  public String getInfo() {
    return info;
  }
}
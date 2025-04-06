package lab1.model;

import lab1.utils.HttpStatusCode;

import java.util.Map;

/**
 * @brief HTTP响应体
 */
public class Response {
  private HttpStatusCode code;
  private String info;
  private Map<String, Object> data;

  public static Response success() {
    Response rsp = new Response();
    rsp.setCode(HttpStatusCode.OK);
    rsp.setInfo("success");
    return rsp;
  }

  public static Response success(Map<String, Object> map) {
    Response rsp = success();
    rsp.setData(map);
    return rsp;
  }

  public HttpStatusCode getCode() {
    return code;
  }

  public void setCode(HttpStatusCode code) {
    this.code = code;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public Map<String, Object> getData() {
    return data;
  }

  public void setData(Map<String, Object> data) {
    this.data = data;
  }
}

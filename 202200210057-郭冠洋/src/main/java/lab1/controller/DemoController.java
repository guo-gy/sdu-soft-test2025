package lab1.controller;

import lab1.model.Argument;
import lab1.utils.Converter;
import org.springframework.web.bind.annotation.*;
import lab1.model.Response;

import java.math.BigInteger;
import java.util.*;

@RestController
public class DemoController {
  /**
   * @brief 列举可用的路由。
   */
  @GetMapping("/")
  @ResponseBody
  public Response index() {
    Map<String, Object> map = new HashMap<>();
    map.put("dec-to-hex(十进制转十六进制串)", "POST /dec-to-hex");
    map.put("calc(四则运算计算器)", "POST /calc");
    return Response.success(map);
  }

  /**
   * 十进制转十六进制串
   */
  @PostMapping("/dec-to-hex")
  @ResponseBody
  public Response dec_to_hex(@RequestBody Argument arg) {
    var b = Converter.decToHex(arg.getValue());
    var m = new HashMap<String, Object>();
    m.put("hex", b);
    return Response.success(m);
  }

  /**
   * 计算器
   */
  @PostMapping("/calc")
  @ResponseBody
  public Response calc(@RequestBody Argument arg) {
    var s = new Scanner(arg.getValue());
    var m = new HashMap<String, Object>();
    BigInteger a = new BigInteger(s.next());
    if(s.hasNext()) {
      var o = s.next().charAt(0); // FIXME
      var b = new BigInteger(s.next());
      while(s.hasNext()) {
        var p = s.next().charAt(0); // FIXME
        var c = new BigInteger(s.next());
        if(level(o) >= level(p)) {
          a = eval(a, o, b);
          o = p;
          b = c;
        } else {
          b = eval(b, p, c);
        }
      }
      a = eval(a, o, b);
    }
    m.put("result", a);
    return Response.success(m);
  }

  /**
   * 算符优先级
   */
  private int level(char c) {
    return (c == '+' || c == '-') ? 0 : 1;
  }

  /**
   * 表达式求值
   */
  private static BigInteger eval(BigInteger a, char o, BigInteger b) {
    return switch (o) {
      case '+' -> b.add(a);
      case '-' -> a.subtract(b);
      case '*' -> b.multiply(a);
      default -> a.divide(b);
    };
  }
}

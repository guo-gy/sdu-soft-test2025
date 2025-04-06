# 实验1: 单元测试实验

## 实验说明

- 共 4 学时。
- 提交截止时间：2025-04-25日前

使用JUnit工具和Spring Unit Testing框架对包`lab1.controller`中的`DemoController`类编写对应的测试类作为单元测试。测试类的示例位于`src/test/java/lab1/controller/DemoControlerTest.java`。可以分析代码找出潜在的错误，编写针对性的测试用例。最终提交实验报告、包含单元测试用例的完整项目代码。

## 参考代码

### 十进制转十六进制

- 向`http://127.0.0.1:8080/dec-to-hex`发送POST请求。

```java
@PostMapping("/dec-to-hex")
@ResponseBody
public Response dec_to_hex(@RequestBody Argument arg) {
  var b = Converter.decToHex(arg.getValue());
  var m = new HashMap<String, Object>();
  m.put("hex", b);
  return Response.success(m);
}
```

请求样例：

```in
{"value": "123"}
```

响应样例：

```out
{"code":"OK","info":"success","data":{"hex":"7b"}}
```

### 四则运算计算器

- 向`http://127.0.0.1:8080/calc`发送POST请求。

```java
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
```

```java
/**
 * 算符优先级
 */
private int level(char c) {
  return (c == '+' || c == '-') ? 0 : 1;
}
```

```java
/**
 * 表达式求值
 */
private static BigInteger eval(BigInteger a, char o, BigInteger b) {
  return switch (o) {
    case '+' -> b.add(a);
    case '-' -> b.subtract(a); // FIXME
    case '*' -> b.multiply(a);
    default -> b.divide(a); // FIXME
  };
}
```

样例请求：
```in
{"value": "123"}
```

样例响应：

```out
{"code":"OK","info":"success","data":{"hex":"7b"}}
```

## Web接口调试工具

- IDEA插件“RestfulTool”，下载较顺畅。
- Talend API Tester，Chrome浏览器插件，访问可能受限。

## 提交内容

- 实验报告：以“学号-姓名”方式命名。Markdown、DOC、PDF均可。模板参考`REPORT.md`文件。
- 测试项目：以“学号-姓名.zip”方式命名。请压缩为ZIP包再上传。

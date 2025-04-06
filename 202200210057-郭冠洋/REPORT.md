# 实验报告

- [x] 学号：202200210057
- [x] 姓名：郭冠洋
- [x] 班级：菁英班

## 实验过程

- 测试工具：JUnit、Spring Boot Test、MockMvc
- 调试工具：Postman

1. 第一次测试。进行了两次测试，一次失败测试。
失败的原因：断言期望5xx状态码失败。
"/dec-to-hex"的Get请求返回4xx是正常的，因为没有Get方法。
但是有post方法。
所以我们要写正确十进制转十六进制串的测试用例。

2. 知道了十进制转十六进制串测试失败原因，那么我们开始进行修改：
例如：
> 请求样例：
{"value": "123"}
响应样例：
{"code":"OK","info":"success","data":{"hex":"7b"}}
测试代码为：
```java
@Test
  void testDecToHex() throws Exception {
    mockMvc.perform(get("/dec-to-hex"))
      .andExpect(status().is4xxClientError());

    mockMvc.perform(post("/dec-to-hex")
            .contentType("application/json") // 设置请求体类型为 JSON
            .content("{\"value\": \"123\"}")) // 提供请求体
        .andExpect(status().isOk()) // 期望返回 200 状态码
        .andExpect(jsonPath("$.code").value("OK")) // 验证响应中的 code 字段
        .andExpect(jsonPath("$.info").value("success")) // 验证响应中的 info 字段
        .andExpect(jsonPath("$.data.hex").value("7b")); // 验证返回的十六进制值
  }
```

3. 我们再次进行测试
```powershell
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
```
可以看到这次成功了。

4. 接下来写计算器的测试用例。
> 测试加法：
请求体为 {"value": "2 + 3"}。
验证返回的 result 字段值为 5。

> 测试减法：
请求体为 {"value": "10 - 4"}。
验证返回的 result 字段值为 6。

> 测试乘法：
请求体为 {"value": "7 * 8"}。
验证返回的 result 字段值为 56。

> 测试除法：
请求体为 {"value": "20 / 4"}。
验证返回的 result 字段值为 5。

测试代码为：
```java
@Test
  void testCalc() throws Exception {
    // 测试加法
    mockMvc.perform(post("/calc")
        .contentType("application/json")
        .content("{\"value\": \"2 + 3\"}")) // 请求体
        .andExpect(status().isOk()) // 期望返回 200 状态码
        .andExpect(jsonPath("$.code").value("OK")) // 验证响应中的 code 字段
        .andExpect(jsonPath("$.info").value("success")) // 验证响应中的 info 字段
        .andExpect(jsonPath("$.data.result").value("5")); // 验证计算结果

    // 测试减法
    mockMvc.perform(post("/calc")
        .contentType("application/json")
        .content("{\"value\": \"10 - 4\"}")) // 请求体
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.result").value("6")); // 验证计算结果

    // 测试乘法
    mockMvc.perform(post("/calc")
        .contentType("application/json")
        .content("{\"value\": \"7 * 8\"}")) // 请求体
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.result").value("56")); // 验证计算结果

    // 测试除法
    mockMvc.perform(post("/calc")
        .contentType("application/json")
        .content("{\"value\": \"20 / 4\"}")) // 请求体
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.result").value("5")); // 验证计算结果

    // 测试加减混合运算
    mockMvc.perform(post("/calc")
            .contentType("application/json")
            .content("{\"value\": \"10 + 5 - 3\"}")) // 请求体
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.result").value("12")); // 验证计算结果

    // 测试乘除混合运算
    mockMvc.perform(post("/calc")
            .contentType("application/json")
            .content("{\"value\": \"8 * 3 / 4\"}")) // 请求体
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.result").value("6")); // 验证计算结果

    // 测试加减乘混合运算
    mockMvc.perform(post("/calc")
            .contentType("application/json")
            .content("{\"value\": \"2 + 3 * 4 - 5\"}")) // 请求体
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.result").value("9")); // 验证计算结果

    // 测试加减乘除混合运算
    mockMvc.perform(post("/calc")
            .contentType("application/json")
            .content("{\"value\": \"20 / 4 + 3 * 2 - 1\"}")) // 请求体
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.result").value("10")); // 验证计算结果

    // 测试复杂表达式
    mockMvc.perform(post("/calc")
            .contentType("application/json")
            .content("{\"value\": \"100 - 20 / 5 + 3 * 4\"}")) // 请求体
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.result").value("109")); // 验证计算结果
  }
```

5. 再次测试。
发现了报错：
```powershell
[ERROR] Failures: 
[ERROR]   DemoControllerTest.testCalc:55 JSON path "$.data.result" expected:<6> but was:<-6>
```
然后我们去检查修改减法的实现。

6. 改进减法的实现。
```java
case '-' -> b.subtract(a); // FIXME
```
修改为
```java
case '-' -> a.subtract(b);
```

7. 再次测试，发现减法没问题了。但是
```powershell
[ERROR] Failures: 
[ERROR]   DemoControllerTest.testCalc:69 JSON path "$.data.result" expected:<5> but was:<0>
```

8. 我们再去检查并修改除法：
```java
default -> b.divide(a); // FIXME
```
修改为：
```java
default -> a.divide(b);
```

9. 再次测试，发现没问题了
```powershell
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.364 s -- in lab1.DemoAppTests
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
```


## 缺陷记录
1. 测试用例错误。
"/dec-to-hex"没有GET方法，所以期望是4xx.
有POST方法，就需要写出来具体的测试样例。
2. 减法和除法。
减法和除法的顺序反了。
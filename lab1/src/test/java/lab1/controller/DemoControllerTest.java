package lab1.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DemoController.class)
class DemoControllerTest {
  @Autowired
  private MockMvc mockMvc; // 模拟 HTTP 请求

  @Test
  void testIndex() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value("OK"));
  }

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
        .andExpect(jsonPath("$.data.result").value("108")); // 验证计算结果
  }

}

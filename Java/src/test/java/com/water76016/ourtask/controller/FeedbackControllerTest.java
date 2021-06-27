package com.water76016.ourtask.controller;

import com.alibaba.fastjson.JSONObject;
import com.water76016.ourtask.common.RestResult;
import com.water76016.ourtask.entity.Feedback;
import com.water76016.ourtask.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
class FeedbackControllerTest {
    private MockMvc mockMvc;
    private String token;
    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        //获取登录用户的token
        token = login();
    }

    /**
     * 获取登录用户的token
     * @return
     * @throws Exception
     */
    String login() throws Exception {
        User user = User.builder().username("user").password("123456").build();
        String content = JSONObject.toJSONString(user);
        String token = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk())
                .andReturn().getResponse().getHeader("Authorization");
        return token;
    }

    /**
     * 测试添加反馈接口
     * @throws Exception
     */
    @Test
    void add() throws Exception {
        Feedback feedback = Feedback.builder().userId(1).title("测试").description("测试").build();
        String content = JSONObject.toJSONString(feedback);
        mockMvc.perform(post("/feedback/add")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk());
    }
}
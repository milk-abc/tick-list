package com.water76016.ourtask.controller;

import com.alibaba.fastjson.JSONObject;
import com.water76016.ourtask.entity.Label;
import com.water76016.ourtask.entity.User;
import org.junit.jupiter.api.AfterEach;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
class LabelControllerTest {
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

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllList() throws Exception {
        mockMvc.perform(get("/label/listAll/1")
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * 把label添加、修改、删除三个操作放在一起
     */
    @Test
    void labelManage() throws Exception {
        add();
        update();
        deleteLabelById();

    }
    void deleteLabelById() throws Exception {
        //todo:由于无法获取自动生成主键id，该方法暂时无法完成
    }

    void update() {
        //todo:由于无法获取自动生成主键id，该方法暂时无法完成
    }

    void add() throws Exception {
        Label label = Label.builder().userId(1).name("测试").build();
        String content = JSONObject.toJSONString(label);
        mockMvc.perform(post("/label/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk());

    }

    @Test
    void getPageList() throws Exception {
        mockMvc.perform(get("/label/getPageList/1/1/5")
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
package com.water76016.ourtask.controller;

import com.alibaba.fastjson.JSONObject;
import com.water76016.ourtask.entity.Category;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
class CategoryControllerTest {
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
     * 把category添加、修改、删除三个操作放在一起
     */
    @Test
    void categoryManage() throws Exception {
        add();
        update();
        delete();

    }

    void add() throws Exception {
        Category category = Category.builder().id(1).name("测试").build();
        String content = JSONObject.toJSONString(category);
        mockMvc.perform(post("/category/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk());
    }

    void delete() {
        //todo:由于无法获取自动生成主键id，该方法暂时无法完成
    }

    void update() {
        //todo:由于无法获取自动生成主键id，该方法暂时无法完成
    }

    @Test
    void listAll() throws Exception {
        mockMvc.perform(get("/category/listAll/1")
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getCategoryById() throws Exception {
        mockMvc.perform(get("/category/get/1")
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON))
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
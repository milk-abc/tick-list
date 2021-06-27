package com.water76016.ourtask.controller;

import com.alibaba.fastjson.JSONObject;
import com.water76016.ourtask.dto.SelectCondition;
import com.water76016.ourtask.dto.TaskParam;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
class TaskControllerTest {
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

    @Test
    void saveOrUpdate() throws Exception {
        List<Integer> labelList = new ArrayList<>();
        TaskParam taskParam = new TaskParam(1, 1, 1, "测试", labelList, "测试");
        String content = JSONObject.toJSONString(taskParam);
        mockMvc.perform(post("/task/save")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk());
    }

    @Test
    void deleteTaskById() throws Exception {
        mockMvc.perform(get("/task/delete/6")
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllList() throws Exception {
        mockMvc.perform(get("/task/getAllList/1")
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getTaskList() throws Exception {
        mockMvc.perform(get("/task/getAllList/1/1")
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getPageList() throws Exception {
        List<Integer> labelIdList = new ArrayList<>();
        SelectCondition selectCondition = new SelectCondition("1", 1, labelIdList);
        String content = JSONObject.toJSONString(selectCondition);
        mockMvc.perform(post("/task/getPageList/1/1/5")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk());
    }

    @Test
    void countTaskForDay() throws Exception {
        mockMvc.perform(get("/task/countTaskForDay/1")
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void countTaskForWeek() throws Exception {
        mockMvc.perform(get("/task/countTaskForWeek/1")
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getStatistics() throws Exception {
        mockMvc.perform(get("/task/getStatistics/1")
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void countTodayForCategory() throws Exception {
        mockMvc.perform(get("/task/countTodayForCategory/1")
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
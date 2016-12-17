package com.barrycommins.securitydemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityDemoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAdminCanAccessTest1() throws Exception {
        mockMvc.perform(get("/test1")
                .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk());
    }

    @Test
    public void testUserCanNotAccessTest1() throws Exception {
        mockMvc.perform(get("/test1")
                .with(user("user")))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testNotLoggedInTest1Redirects() throws Exception {
        mockMvc.perform(get("/test1"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testAdminCanAccessTest2() throws Exception {
        mockMvc.perform(get("/test2")
                .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk());
    }

    @Test
    public void testUserCanNotAccessTest2() throws Exception {
        mockMvc.perform(get("/test2")
                .with(user("user")))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testNotLoggedInTest2Redirects() throws Exception {
        mockMvc.perform(get("/test2"))
                .andExpect(status().is3xxRedirection());
    }
}

package ro.codecrafters.BankingSystem.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class BankUserControllerTest {
    private final MockMvc mockMvc;

    @Autowired
    public BankUserControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void checkClient_shouldReturn200_whenCalled() throws Exception {
        mockMvc.perform(post("/clients/verify")).andExpect(status().isOk());
    }

    @Test
    public void generateDocument() throws Exception {
        mockMvc.perform(post("/clients/generate?type=ENROLMENT")).andExpect(status().isOk());
        mockMvc.perform(post("/clients/generate?type=DENIAL")).andExpect(status().isOk());
        mockMvc.perform(post("/clients/generate?type=WRONG")).andExpect(status().isBadRequest());
    }

    @Test
    public void uploadDocument() throws Exception {
        mockMvc.perform(post("/clients/upload?type=ENROLMENT")).andExpect(status().isOk());
        mockMvc.perform(post("/clients/upload?type=DENIAL")).andExpect(status().isOk());
        mockMvc.perform(post("/clients/upload?type=WRONG")).andExpect(status().isBadRequest());
    }

}

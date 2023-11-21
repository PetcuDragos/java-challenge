package ro.codecrafters.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClientReputationControllerTest {

    private final MockMvc mockMvc;

    @Autowired
    public ClientReputationControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void getReputationOfClient_expectStatusOkAndValue10_whenNationalIdStartsWith2() throws Exception {
        mockMvc.perform(post("/clients/reputation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nationalId\":\"2990101030021\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }

    @Test
    public void getReputationOfClient_expectStatusOkAndValue60_whenNationalIdStartsWith1() throws Exception {
        mockMvc.perform(post("/clients/reputation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nationalId\":\"1990101030021\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("60"));
    }

    @Test
    public void getReputationOfClient_expectStatusOkAndValue100_whenNationalIdNotStartingWith1Or2() throws Exception {
        mockMvc.perform(post("/clients/reputation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nationalId\":\"6990101030021\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("100"));
    }


}
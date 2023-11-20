package ro.codecrafters.BankingSystem.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import ro.codecrafters.BankingSystem.api.ClientReputationApi;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class BankUserControllerTest {
    private final MockMvc mockMvc;

    @MockBean
    private ClientReputationApi clientReputationApi;

    @Autowired
    public BankUserControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void checkClient_shouldReturn200_whenExpirationDateIsPresent() throws Exception {
        Mockito.when(clientReputationApi.getClientReputation(any())).thenReturn(100);
        mockMvc.perform(post("/clients/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"expirationDate\":\"2025-01-01\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void generateDocument() throws Exception {
        mockMvc.perform(post("/clients/generate?type=ENROLMENT")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
        mockMvc.perform(post("/clients/generate?type=DENIAL")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
        mockMvc.perform(post("/clients/generate?type=WRONG")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void uploadDocument() throws Exception {

        MockMultipartFile file = new MockMultipartFile("file", "test.txt",
                MediaType.TEXT_PLAIN_VALUE, "Test".getBytes());

        mockMvc.perform(multipart("/clients/upload?type=ENROLMENT").file(file))
                .andExpect(status().isOk());
        mockMvc.perform(multipart("/clients/upload?type=DENIAL").file(file))
                .andExpect(status().isOk());
        mockMvc.perform(multipart("/clients/upload?type=WRONG").file(file))
                .andExpect(status().isBadRequest());
    }

}

package ro.codecrafters.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ro.codecrafters.model.Client;
import ro.codecrafters.repository.ClientRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
class ClientControllerTest {

    private final MockMvc mockMvc;
    private final ClientRepository clientRepository;

    @Autowired
    public ClientControllerTest(MockMvc mockMvc, ClientRepository clientRepository) {
        this.mockMvc = mockMvc;
        this.clientRepository = clientRepository;
        addClientDataTest();
    }

    public void addClientDataTest() {
        clientRepository.save(new Client("199010103002", "John Doe"));
    }

    @Test
    void checkClientExistence_ShouldReturnFalse_WhenCheckingNonExistingId() throws Exception {
        mockMvc.perform(post("/clients/existence")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nationalId\":\"299010103002\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void checkClientExistence_ShouldReturnTrue_WhenCheckingExistingId() throws Exception {
        mockMvc.perform(post("/clients/existence")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nationalId\":\"199010103002\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
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
import ro.codecrafters.BankingSystem.api.ClientExistenceApi;
import ro.codecrafters.BankingSystem.api.ClientReputationApi;
import ro.codecrafters.BankingSystem.exception.ApiConnectionException;
import ro.codecrafters.BankingSystem.util.Risk;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class BankUserControllerTest {
    private final MockMvc mockMvc;

    @MockBean
    private ClientReputationApi clientReputationApi;

    @MockBean
    private ClientExistenceApi clientExistenceApi;

    @Autowired
    public BankUserControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void checkClient_shouldReturnOk_whenAllFieldsArePresentAndValidAndApisAreWorking() throws Exception {
        Mockito.when(clientReputationApi.getClientReputation(any())).thenReturn(100);
        Mockito.when(clientExistenceApi.checkClientExistence(any())).thenReturn(false);

        String jsonInput = "{" +
                "\"creationDate\": \"2023-02-02\"," +
                "\"expirationDate\" : \"2024-01-01\"," +
                "\"nationalId\" : \"1990101030021\"," +
                "\"fullName\" : \"Dragos\"}";

        mockMvc.perform(post("/clients/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isOk())
                .andExpect(jsonPath("validExpirationDate", is(true)))
                .andExpect(jsonPath("risk", is(Risk.HIGH_RISK.getMessage())))
                .andExpect(jsonPath("clientAlreadyExisting", is(false)));
    }

    @Test
    public void checkClient_shouldReturnBadRequest_whenExpirationDateIsMissing() throws Exception {
        Mockito.when(clientReputationApi.getClientReputation(any())).thenReturn(100);
        Mockito.when(clientExistenceApi.checkClientExistence(any())).thenReturn(false);

        String jsonInput = "{" +
                "\"creationDate\": \"2023-02-02\"," +
                "\"nationalId\" : \"1990101030021\"," +
                "\"fullName\" : \"Dragos\"}";

        mockMvc.perform(post("/clients/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Expiration date cannot be null"));
    }

    @Test
    public void checkClient_shouldReturnBadRequest_whenCreationDateIsMissing() throws Exception {
        Mockito.when(clientReputationApi.getClientReputation(any())).thenReturn(100);
        Mockito.when(clientExistenceApi.checkClientExistence(any())).thenReturn(false);

        String jsonInput = "{" +
                "\"expirationDate\" : \"2024-01-01\"," +
                "\"nationalId\" : \"1990101030021\"," +
                "\"fullName\" : \"Dragos\"}";

        mockMvc.perform(post("/clients/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Creation date cannot be null"));
    }

    @Test
    public void checkClient_shouldReturnBadRequest_whenCreationDateIsInFuture() throws Exception {
        Mockito.when(clientReputationApi.getClientReputation(any())).thenReturn(100);
        Mockito.when(clientExistenceApi.checkClientExistence(any())).thenReturn(false);

        String jsonInput = "{" +
                "\"creationDate\": \"2025-02-02\"," +
                "\"expirationDate\" : \"2024-01-01\"," +
                "\"nationalId\" : \"1990101030021\"," +
                "\"fullName\" : \"Dragos\"}";

        mockMvc.perform(post("/clients/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The creation date cannot be in future"));
    }

    @Test
    public void checkClient_shouldReturnBadRequest_whenNationalIdIsMissing() throws Exception {
        Mockito.when(clientReputationApi.getClientReputation(any())).thenReturn(100);
        Mockito.when(clientExistenceApi.checkClientExistence(any())).thenReturn(false);

        String jsonInput = "{" +
                "\"creationDate\": \"2023-02-02\"," +
                "\"expirationDate\" : \"2024-01-01\"," +
                "\"fullName\" : \"Dragos\"}";

        mockMvc.perform(post("/clients/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("National id cannot be null"));
    }

    @Test
    public void checkClient_shouldReturnBadRequest_whenNationalIdLengthIsLongerThan13() throws Exception {
        Mockito.when(clientReputationApi.getClientReputation(any())).thenReturn(100);
        Mockito.when(clientExistenceApi.checkClientExistence(any())).thenReturn(false);

        String jsonInput = "{" +
                "\"creationDate\": \"2023-02-02\"," +
                "\"expirationDate\" : \"2024-01-01\"," +
                "\"nationalId\" : \"199010103002111\"," +
                "\"fullName\" : \"Dragos\"}";

        mockMvc.perform(post("/clients/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The national id has a wrong length"));
    }

    @Test
    public void checkClient_shouldReturnBadRequest_whenNationalIdLengthIsSmallerThan13() throws Exception {
        Mockito.when(clientReputationApi.getClientReputation(any())).thenReturn(100);
        Mockito.when(clientExistenceApi.checkClientExistence(any())).thenReturn(false);

        String jsonInput = "{" +
                "\"creationDate\": \"2023-02-02\"," +
                "\"expirationDate\" : \"2024-01-01\"," +
                "\"nationalId\" : \"1990\"," +
                "\"fullName\" : \"Dragos\"}";

        mockMvc.perform(post("/clients/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The national id has a wrong length"));
    }

    @Test
    public void checkClient_shouldReturnBadRequest_whenNationalIdHasWrongFormat() throws Exception {
        Mockito.when(clientReputationApi.getClientReputation(any())).thenReturn(100);
        Mockito.when(clientExistenceApi.checkClientExistence(any())).thenReturn(false);

        String jsonInput = "{" +
                "\"creationDate\": \"2023-02-02\"," +
                "\"expirationDate\" : \"2024-01-01\"," +
                "\"nationalId\" : \"199010103002a\"," +
                "\"fullName\" : \"Dragos\"}";

        mockMvc.perform(post("/clients/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The national id should only contain digits"));
    }

    @Test
    public void checkClient_shouldReturnBadRequest_whenFullNameIsMissing() throws Exception {
        Mockito.when(clientReputationApi.getClientReputation(any())).thenReturn(100);
        Mockito.when(clientExistenceApi.checkClientExistence(any())).thenReturn(false);

        String jsonInput = "{" +
                "\"creationDate\": \"2023-02-02\"," +
                "\"expirationDate\" : \"2024-01-01\"," +
                "\"nationalId\" : \"1990101030021\"}";

        mockMvc.perform(post("/clients/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Full name cannot be null")));
    }

    @Test
    public void checkClient_shouldReturnBadRequest_whenFullNameIsBlank() throws Exception {
        Mockito.when(clientReputationApi.getClientReputation(any())).thenReturn(100);
        Mockito.when(clientExistenceApi.checkClientExistence(any())).thenReturn(false);

        String jsonInput = "{" +
                "\"creationDate\": \"2023-02-02\"," +
                "\"expirationDate\" : \"2024-01-01\"," +
                "\"nationalId\" : \"1990101030021\"," +
                "\"fullName\" : \" \"}";

        mockMvc.perform(post("/clients/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Full name cannot be blank")));
    }

    @Test
    public void checkClient_shouldReturnBadRequest_whenFullNameIsNotValid() throws Exception {
        Mockito.when(clientReputationApi.getClientReputation(any())).thenReturn(100);
        Mockito.when(clientExistenceApi.checkClientExistence(any())).thenReturn(false);

        String jsonInput = "{" +
                "\"creationDate\": \"2023-02-02\"," +
                "\"expirationDate\" : \"2024-01-01\"," +
                "\"nationalId\" : \"1990101030021\"," +
                "\"fullName\" : \"Dragos22\"}";

        mockMvc.perform(post("/clients/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("The format of the name is incorrect")));
    }

    @Test
    public void checkClient_shouldReturnInternalServerError_whenReputationApiIsNotWorking() throws Exception {
        Mockito.when(clientReputationApi.getClientReputation(any())).thenThrow(ApiConnectionException.class);
        Mockito.when(clientExistenceApi.checkClientExistence(any())).thenReturn(false);

        String jsonInput = "{" +
                "\"creationDate\": \"2023-02-02\"," +
                "\"expirationDate\" : \"2024-01-01\"," +
                "\"nationalId\" : \"1990101030021\"," +
                "\"fullName\" : \"Dragos\"}";

        mockMvc.perform(post("/clients/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Could not connect to the client reputation api."));
    }

    @Test
    public void checkClient_shouldReturnInternalServerError_whenExistenceApiIsNotWorking() throws Exception {
        Mockito.when(clientReputationApi.getClientReputation(any())).thenReturn(100);
        Mockito.when(clientExistenceApi.checkClientExistence(any())).thenThrow(ApiConnectionException.class);

        String jsonInput = "{" +
                "\"creationDate\": \"2023-02-02\"," +
                "\"expirationDate\" : \"2024-01-01\"," +
                "\"nationalId\" : \"1990101030021\"," +
                "\"fullName\" : \"Dragos\"}";

        mockMvc.perform(post("/clients/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Could not connect to the client existence api."));
    }

    @Test
    public void generateDocument_shouldReturnOk_whenDocumentTypeValidAndContentIsPresent() throws Exception {
        mockMvc.perform(post("/clients/generate?type=ENROLMENT")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());

        mockMvc.perform(post("/clients/generate?type=DENIAL")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void generateDocument_shouldReturnBadRequest_whenDocumentTypeInvalid() throws Exception {
        mockMvc.perform(post("/clients/generate?type=WRONG")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void generateDocument_shouldReturnBadRequest_whenContentIsNull() throws Exception {
        mockMvc.perform(post("/clients/generate?type=DENIAL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void uploadDocument_shouldReturnOk_whenDocumentTypeIsValidAndFileIsGiven() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt",
                MediaType.TEXT_PLAIN_VALUE, "Test".getBytes());

        mockMvc.perform(multipart("/clients/upload?type=ENROLMENT").file(file))
                .andExpect(status().isOk());
        mockMvc.perform(multipart("/clients/upload?type=DENIAL").file(file))
                .andExpect(status().isOk());
    }

    @Test
    public void uploadDocument_shouldReturnBadRequest_whenDocumentTypeIsInvalid() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt",
                MediaType.TEXT_PLAIN_VALUE, "Test".getBytes());

        mockMvc.perform(multipart("/clients/upload?type=WRONG").file(file))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void uploadDocument_shouldReturnBadRequest_whenFileIsMissing() throws Exception {
        mockMvc.perform(multipart("/clients/upload?type=DENIAL"))
                .andExpect(status().isBadRequest());
    }

}

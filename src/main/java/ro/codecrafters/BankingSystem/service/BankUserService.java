package ro.codecrafters.BankingSystem.service;

import org.springframework.stereotype.Service;
import ro.codecrafters.BankingSystem.api.ClientExistenceApi;
import ro.codecrafters.BankingSystem.api.ClientReputationApi;
import ro.codecrafters.BankingSystem.dto.ClientDataDto;
import ro.codecrafters.BankingSystem.dto.ClientReportDto;
import ro.codecrafters.BankingSystem.dto.DocumentType;
import ro.codecrafters.BankingSystem.util.Risk;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

@Service
public class BankUserService {

    private final ClientReputationApi clientReputationApi;
    private final ClientExistenceApi clientExistenceApi;

    public BankUserService(ClientReputationApi clientReputationApi, ClientExistenceApi clientExistenceApi) {
        this.clientReputationApi = clientReputationApi;
        this.clientExistenceApi = clientExistenceApi;
    }

    public byte[] generateDocumentWithType(DocumentType documentType) throws IOException {
        InputStream resourceAsStream = getClass().getResourceAsStream(getFilePathForDocumentType(documentType));
        byte[] bytes = resourceAsStream.readAllBytes();
        resourceAsStream.close();
        return bytes;
    }

    private String getFilePathForDocumentType(DocumentType documentType) {
        if (documentType == DocumentType.ENROLMENT) return "/static/generatedEnrolmentFile.txt";
        return "/static/generatedDenialFile.txt";
    }

    public ClientReportDto checkClientData(ClientDataDto clientDataDto) {
        boolean validExpirationDate = clientDataDto.getExpirationDate().isAfter(LocalDate.now());
        Integer reputation = clientReputationApi.getClientReputation(clientDataDto);
        Boolean existence = clientExistenceApi.checkClientExistence(clientDataDto);
        return new ClientReportDto(validExpirationDate, Risk.getEnumByValue(reputation).getMessage(), existence);
    }
}

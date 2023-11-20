package ro.codecrafters.BankingSystem.service;

import org.springframework.stereotype.Service;
import ro.codecrafters.BankingSystem.api.ClientReputationApi;
import ro.codecrafters.BankingSystem.dto.ClientDataDto;
import ro.codecrafters.BankingSystem.dto.ClientReportDto;
import ro.codecrafters.BankingSystem.dto.DocumentType;
import ro.codecrafters.BankingSystem.util.Risk;

import java.io.File;
import java.time.LocalDate;

@Service
public class BankUserService {

    private final ClientReputationApi clientReputationApi;

    public BankUserService(ClientReputationApi clientReputationApi) {
        this.clientReputationApi = clientReputationApi;
    }
    public File generateDocumentWithType(DocumentType documentType) {
        String filePathForDocumentType = getFilePathForDocumentType(documentType);
        return new File(filePathForDocumentType);
    }

    private String getFilePathForDocumentType(DocumentType documentType) {
        if (documentType == DocumentType.ENROLMENT) return "static/generatedEnrolmentFile.txt";
        return "static/generatedDenialFile.txt";
    }

    public ClientReportDto checkClientData(ClientDataDto clientDataDto) {
        boolean validExpirationDate = clientDataDto.getExpirationDate().isAfter(LocalDate.now());
        // todo: create other user existence through external api
        Integer reputation = clientReputationApi.getClientReputation(clientDataDto);
        return new ClientReportDto(validExpirationDate, Risk.getEnumByValue(reputation).message, true);
    }
}

package ro.codecrafters.BankingSystem.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.codecrafters.BankingSystem.dto.ClientDataDto;
import ro.codecrafters.BankingSystem.dto.ClientReportDto;
import ro.codecrafters.BankingSystem.dto.DocumentType;
import ro.codecrafters.BankingSystem.util.Risk;

import java.io.File;
import java.time.LocalDate;

@Service
public class BankUserService {
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
        Integer reputation = callApiToGetReputation(clientDataDto);
        return new ClientReportDto(validExpirationDate, Risk.getEnumByValue(reputation).message, true);
    }

    private Integer callApiToGetReputation(ClientDataDto clientDataDto) {
        //todo switch to feign client
        if (clientDataDto.getNationalId() == null) return 100;
        RestTemplate restTemplate = new RestTemplate();
        Integer reputation = restTemplate.exchange("http://localhost:8081/clients/reputation", HttpMethod.POST, new HttpEntity<>(clientDataDto), Integer.class).getBody();
        if (reputation == null) throw new IllegalArgumentException("reputation came invalid");
        return reputation;
    }
}

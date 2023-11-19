package ro.codecrafters.BankingSystem.service;

import org.springframework.stereotype.Service;
import ro.codecrafters.BankingSystem.dto.ClientDataDto;
import ro.codecrafters.BankingSystem.dto.ClientReportDto;
import ro.codecrafters.BankingSystem.dto.DocumentType;

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
        // todo: create other checks through external apis
        return new ClientReportDto(validExpirationDate, "High risk", true);
    }
}

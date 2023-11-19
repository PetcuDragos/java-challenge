package ro.codecrafters.BankingSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class ClientReportDto implements Serializable {
    private boolean validExpirationDate;
    private String risk;
    private boolean clientAlreadyExisting;
}

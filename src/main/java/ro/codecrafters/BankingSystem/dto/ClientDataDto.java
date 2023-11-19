package ro.codecrafters.BankingSystem.dto;

import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
public class ClientDataDto implements Serializable {
    private String fullName;
    private String nationalId;
    private LocalDate creationDate;
    private LocalDate expirationDate;
}

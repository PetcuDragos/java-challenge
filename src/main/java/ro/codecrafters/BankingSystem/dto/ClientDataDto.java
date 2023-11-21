package ro.codecrafters.BankingSystem.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ClientDataDto implements Serializable {
    @NotNull(message = "Full name cannot be null")
    @NotBlank(message = "Full name cannot be blank")
    @Pattern(regexp = "[a-zA-Z -]+", message = "The format of the name is incorrect")
    private String fullName;

    @NotNull(message = "National id cannot be null")
    @Size(max = 13, min = 13, message = "The national id has a wrong length")
    @Pattern(regexp = "[0-9]+", message = "The national id should only contain digits")
    private String nationalId;

    @NotNull(message = "Creation date cannot be null")
    @PastOrPresent(message = "The creation date cannot be in future")
    private LocalDate creationDate;

    @NotNull(message = "Expiration date cannot be null")
    private LocalDate expirationDate;
}

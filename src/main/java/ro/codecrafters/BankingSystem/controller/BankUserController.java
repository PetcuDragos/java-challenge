package ro.codecrafters.BankingSystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.codecrafters.BankingSystem.dto.ClientDataDto;
import ro.codecrafters.BankingSystem.dto.ClientReportDto;
import ro.codecrafters.BankingSystem.dto.DocumentType;
import ro.codecrafters.BankingSystem.service.BankUserService;

import java.io.IOException;

@RestController
@RequestMapping("/clients")
@SuppressWarnings("unused")
public class BankUserController {
    private final BankUserService bankUserService;

    public BankUserController(BankUserService bankUserService) {
        this.bankUserService = bankUserService;
    }

    @PostMapping("/verify")
    public ResponseEntity<ClientReportDto> checkClient(@RequestBody ClientDataDto clientDataDto) {
        return ResponseEntity.ok(bankUserService.checkClientData(clientDataDto));
    }

    @PostMapping(value = "/generate", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> generateDocument(@RequestParam("type") DocumentType documentType,
                                                   @RequestBody ClientDataDto clientDataDto) throws IOException {
        return new ResponseEntity<>(bankUserService.generateDocumentWithType(documentType), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocument(@RequestParam("type") DocumentType documentType,
                                                 @RequestBody MultipartFile file) {
        return ResponseEntity.ok("Uploaded " + documentType + " file with name " + file.getOriginalFilename());
    }

}

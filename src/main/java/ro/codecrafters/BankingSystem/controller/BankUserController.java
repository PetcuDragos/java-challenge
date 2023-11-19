package ro.codecrafters.BankingSystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.codecrafters.BankingSystem.dto.DocumentType;

@RestController
@RequestMapping("/clients")
@SuppressWarnings("unused")
public class BankUserController {
    @PostMapping("/verify")
    public ResponseEntity<?> checkClient() {
        return ResponseEntity.ok("Not implemented yet!");
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generateDocument(@RequestParam("type") DocumentType documentType) {
        return ResponseEntity.ok("Generate " + documentType + ", not implemented yet!");
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadDocument(@RequestParam("type") DocumentType documentType) {
        return ResponseEntity.ok("Upload " + documentType + ", not implemented yet!");
    }

}

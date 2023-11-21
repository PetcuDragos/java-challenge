package ro.codecrafters.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.codecrafters.dto.ClientDataDto;
import ro.codecrafters.service.ClientReputationService;

@RestController
@SuppressWarnings("unused")
public class ClientReputationController {

    private final ClientReputationService clientReputationService;

    public ClientReputationController(ClientReputationService clientReputationService) {
        this.clientReputationService = clientReputationService;
    }

    @PostMapping("/clients/reputation")
    public ResponseEntity<Integer> getReputationOfClient(@RequestBody ClientDataDto clientDataDto) {
        return ResponseEntity.ok(clientReputationService.calculateClientReputation(clientDataDto));
    }
}

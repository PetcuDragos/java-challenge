package ro.codecrafters.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.codecrafters.dto.ClientDataDto;
import ro.codecrafters.service.ClientService;

@RestController
@SuppressWarnings("unused")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("clients/existence")
    public ResponseEntity<Boolean> checkClientExistence(@RequestBody ClientDataDto clientDataDto) {
        boolean clientExists = clientService.checkClientExistence(clientDataDto);
        return new ResponseEntity<>(clientExists, HttpStatus.OK);
    }
}

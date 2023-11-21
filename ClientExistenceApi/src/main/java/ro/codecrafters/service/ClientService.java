package ro.codecrafters.service;

import org.springframework.stereotype.Service;
import ro.codecrafters.dto.ClientDataDto;
import ro.codecrafters.repository.ClientRepository;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    public boolean checkClientExistence(ClientDataDto clientDataDto) {
        return clientRepository.findById(clientDataDto.getNationalId()).isPresent();
    }
}

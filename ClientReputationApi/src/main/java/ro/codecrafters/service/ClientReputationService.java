package ro.codecrafters.service;

import org.springframework.stereotype.Service;
import ro.codecrafters.dto.ClientDataDto;

@Service
public class ClientReputationService {

    public int calculateClientReputation(ClientDataDto clientDataDto) {
        String nationalId = clientDataDto.getNationalId();
        if (nationalId.charAt(0) == '2') return 10;
        else if (nationalId.charAt(0) == '1') return 60;
        return 100;
    }
}

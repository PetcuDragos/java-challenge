package ro.codecrafters.BankingSystem.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ro.codecrafters.BankingSystem.dto.ClientDataDto;

@FeignClient(url = "${reputation-api-url}", name = "clientReputationApi")
public interface ClientReputationApi {

    @PostMapping(path = "/clients/reputation")
    Integer getClientReputation(@RequestBody ClientDataDto clientDataDto);
}

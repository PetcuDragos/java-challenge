package ro.codecrafters.BankingSystem.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ro.codecrafters.BankingSystem.dto.ClientDataDto;

@FeignClient(url = "${existence-api-url}", name = "clientExistenceApi")
public interface ClientExistenceApi {

    @PostMapping("clients/existence")
    Boolean checkClientExistence(@RequestBody ClientDataDto clientDataDto);
}

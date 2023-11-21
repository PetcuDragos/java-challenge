package ro.codecrafters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.codecrafters.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
}

package ro.codecrafters.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity(name = "client")
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @Column(name = "national_id")
    private String nationalId;

    @Column(name = "full_name")
    private String fullName;
}

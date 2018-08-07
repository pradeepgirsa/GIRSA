package za.co.global.persistence.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.client.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}

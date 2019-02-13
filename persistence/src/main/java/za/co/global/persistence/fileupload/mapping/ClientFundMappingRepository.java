package za.co.global.persistence.fileupload.mapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.mapping.ClientFundMapping;

@Repository
public interface ClientFundMappingRepository extends JpaRepository<ClientFundMapping, Long> {

    ClientFundMapping findByClientFundCode(String clientFundCode);
}

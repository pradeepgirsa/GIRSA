package za.co.global.persistence.fileupload.mapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.mapping.IssuerMappings;

@Repository
public interface IssuerMappingsRepository extends JpaRepository<IssuerMappings, Long> {

    IssuerMappings findByIssuerCode(String issuerCode);
}

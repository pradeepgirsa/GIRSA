package za.co.global.persistence.fileupload.mapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.mapping.IssuerMapping;

@Repository
public interface IssuerMappingsRepository extends JpaRepository<IssuerMapping, Long> {

    IssuerMapping findByIssuerCode(String issuerCode);
    IssuerMapping findByBarraCode(String barraCode);
}

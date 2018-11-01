package za.co.global.persistence.fileupload.mapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.mapping.DerivativeType;

@Repository
public interface DerivativeTypesRepository extends JpaRepository<DerivativeType, Long> {

    DerivativeType findByType(String type);
}

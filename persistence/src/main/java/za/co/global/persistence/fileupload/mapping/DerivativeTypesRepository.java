package za.co.global.persistence.fileupload.mapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.mapping.DerivativeTypes;
import za.co.global.domain.fileupload.mapping.InstrumentCode;

@Repository
public interface DerivativeTypesRepository extends JpaRepository<DerivativeTypes, Long> {
}

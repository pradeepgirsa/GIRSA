package za.co.global.persistence.fileupload.mapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.mapping.InstrumentCode;
import za.co.global.domain.fileupload.mapping.AdditionalClassification;

@Repository
public interface InstrumentCodeRepository extends JpaRepository<InstrumentCode, Long> {

    InstrumentCode findByManagerCode(String managerCode);
}

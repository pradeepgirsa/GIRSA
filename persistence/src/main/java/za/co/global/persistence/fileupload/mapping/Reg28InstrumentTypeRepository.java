package za.co.global.persistence.fileupload.mapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.mapping.InstrumentCode;
import za.co.global.domain.fileupload.mapping.Reg28InstrumentType;

@Repository
public interface Reg28InstrumentTypeRepository extends JpaRepository<Reg28InstrumentType, Long> {

    Reg28InstrumentType findByReg28InstrType(String reg28InstrType);
}

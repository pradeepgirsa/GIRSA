package za.co.global.persistence.fileupload.mapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.mapping.Indices;
import za.co.global.domain.fileupload.mapping.Reg28InstrumentType;

@Repository
public interface IndicesRepository extends JpaRepository<Indices, Long> {

    Indices findBySecurityAndType(String security, String type);
}

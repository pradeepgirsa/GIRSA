package za.co.global.persistence.fileupload.mapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.mapping.SARBClassificationMapping;

@Repository
public interface SARBClassificationMappingRepository extends JpaRepository<SARBClassificationMapping, Long> {

    SARBClassificationMapping findBySarbClassification(String sarbClassification);
}

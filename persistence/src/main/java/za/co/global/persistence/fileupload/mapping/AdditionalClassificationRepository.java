package za.co.global.persistence.fileupload.mapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.mapping.AdditionalClassification;

import java.util.List;

@Repository
public interface AdditionalClassificationRepository  extends JpaRepository<AdditionalClassification, Long> {

    AdditionalClassification findByAlphaCode(String alphaCode);

    List<AdditionalClassification> findByIndustryAndSectorAndSuperSectorAndSubSector(String industry,
                                                                                     String sector, String superSector,
                                                                                     String subSector);

}

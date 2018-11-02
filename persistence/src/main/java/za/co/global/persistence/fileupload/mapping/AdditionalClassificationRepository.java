package za.co.global.persistence.fileupload.mapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.mapping.AdditionalClassification;

@Repository
public interface AdditionalClassificationRepository  extends JpaRepository<AdditionalClassification, Long> {

    AdditionalClassification findByAlphaCode(String alphaCode);

}

package za.co.global.persistence.fileupload.mapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.mapping.PSGFundMapping;

@Repository
public interface PSGFundMappingRepository extends JpaRepository<PSGFundMapping, Long> {
}

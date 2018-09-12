package za.co.global.persistence.upload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.upload.Holding;

@Repository
public interface HoldingRepository extends JpaRepository<Holding, Long> {
}

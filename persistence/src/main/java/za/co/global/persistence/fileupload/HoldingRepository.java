package za.co.global.persistence.fileupload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.client.fpm.Holding;

@Repository
public interface HoldingRepository extends JpaRepository<Holding, Long> {
}

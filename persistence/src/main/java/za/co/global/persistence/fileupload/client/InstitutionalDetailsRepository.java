package za.co.global.persistence.fileupload.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.client.InstitutionalDetails;

@Repository
public interface InstitutionalDetailsRepository extends JpaRepository<InstitutionalDetails, Long> {
}

package za.co.global.persistence.fileupload.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.client.SecurityListing;

@Repository
public interface SecurityListingRepository extends JpaRepository<SecurityListing, Long> {

    SecurityListing findBySecurityCode(String securityCode);

}

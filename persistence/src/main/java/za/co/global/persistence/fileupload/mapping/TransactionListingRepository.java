package za.co.global.persistence.fileupload.mapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.mapping.DailyPricing;
import za.co.global.domain.fileupload.mapping.TransactionListing;

@Repository
public interface TransactionListingRepository extends JpaRepository<TransactionListing, Long> {

}

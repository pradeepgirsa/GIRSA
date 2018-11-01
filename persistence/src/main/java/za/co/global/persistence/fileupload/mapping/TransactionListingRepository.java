package za.co.global.persistence.fileupload.mapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.mapping.DailyPricing;
import za.co.global.domain.fileupload.mapping.TransactionListing;

import java.util.List;

@Repository
public interface TransactionListingRepository extends JpaRepository<TransactionListing, Long> {

    List<TransactionListing> findByClientPortfolioCodeAndInstrumentCode(String clientPortfolioCode, String instrumentCode);

}

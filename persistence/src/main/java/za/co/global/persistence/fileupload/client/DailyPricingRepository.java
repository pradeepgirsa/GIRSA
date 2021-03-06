package za.co.global.persistence.fileupload.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.client.DailyPricing;

import java.util.Date;

@Repository
public interface DailyPricingRepository extends JpaRepository<DailyPricing, Long> {

    //DailyPricing findBySecurityAndType(String security, String type);

    DailyPricing findByBondCode(String bondCode);

    DailyPricing findByIssuerAndBondCodeAndIssueDate(String issuer, String bondCode, Date issueDate);
}

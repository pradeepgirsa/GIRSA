package za.co.global.persistence.fileupload.mapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.fileupload.mapping.DailyPricing;
import za.co.global.domain.fileupload.mapping.Indices;

import java.util.Date;
import java.util.List;

@Repository
public interface DailyPricingRepository extends JpaRepository<DailyPricing, Long> {

    //DailyPricing findBySecurityAndType(String security, String type);

    List<DailyPricing> findByIssuerAndBondCode(String issuer, String bondCode);

    DailyPricing findByIssuerAndBondCodeAndIssueDate(String issuer, String bondCode, Date issueDate);
}

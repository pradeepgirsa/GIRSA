package za.co.global.persistence.fileupload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.client.Client;
import za.co.global.domain.fileupload.client.fpm.Holding;
import za.co.global.domain.report.ReportData;

import java.util.List;

@Repository
public interface HoldingRepository extends JpaRepository<Holding, Long> {

    List<Holding> findByPortfolioCodeAndClientAndReportDataIsNull(String portfolioCode, Client client);
    List<Holding> findByClientAndReportData(Client client, ReportData reportData);

}

package za.co.global.persistence.fileupload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.global.domain.client.Client;
import za.co.global.domain.fileupload.client.fpm.Holding;
import za.co.global.domain.report.ReportData;

import java.util.List;

@Repository
public interface HoldingRepository extends JpaRepository<Holding, Long> {

    //TODO - refactor
    @Query("SELECT h FROM Holding h WHERE h.client =: client AND h.portfolioCode =: portfolioCode " +
            "AND (h.reportData IS NULL OR h.reportData.reportStatus = 'REGISTERED')")
    List<Holding> findByPortfolioCodeAndClientAndReportDataIsNullOrRegisteredReportData(@Param("portfolioCode")String portfolioCode,
                                                                                        @Param("client") Client client);

    @Query("SELECT h FROM Holding h WHERE h.client =: client AND (h.reportData IS NULL OR h.reportData =:reportData)")
    List<Holding> findByClientAndReportDataIsNullOrReportData(@Param("client") Client client, @Param("reportData") ReportData reportData);

    List<Holding> findByClientAndReportDataIsNull(Client client);

}

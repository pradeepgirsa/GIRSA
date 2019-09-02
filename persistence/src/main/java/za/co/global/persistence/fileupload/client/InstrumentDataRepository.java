package za.co.global.persistence.fileupload.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import za.co.global.domain.client.Client;
import za.co.global.domain.fileupload.client.InstrumentData;
import za.co.global.domain.report.ReportData;
import za.co.global.domain.report.ReportStatus;

import java.util.List;

@Repository
public interface InstrumentDataRepository extends JpaRepository<InstrumentData, Long> {


    List<InstrumentData> findByClientAndReportData(Client client, ReportData reportData);

    List<InstrumentData> findByClient(Client client);

    List<InstrumentData> findByPortfolioCodeAndInstrumentCodeAndClient(String portfolioCode, String instrumentCode, Client client);

    List<InstrumentData> findAllByOrderByUpdatedDateDesc();

    @Query("SELECT i.portfolioCode, SUM(i.currentMarketValue) FROM InstrumentData i GROUP BY i.portfolioCode")
    List<Object[]> findTotalCurrentMarketValueGroupByPortfolioCode();




}

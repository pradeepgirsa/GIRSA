package za.co.global.persistence.fileupload.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.client.Client;
import za.co.global.domain.fileupload.client.InstrumentData;
import za.co.global.domain.report.ReportData;
import za.co.global.domain.report.ReportStatus;

import java.util.List;

@Repository
public interface InstrumentDataRepository extends JpaRepository<InstrumentData, Long> {


    List<InstrumentData> findByClientAndReportData(Client client, ReportData reportData);

    List<InstrumentData> findByClientAndReportDataIsNull(Client client);

    List<InstrumentData> findByPortfolioCodeAndInstrumentCodeAndClientAndReportDataIsNullOrPortfolioCodeAndInstrumentCodeAndClientAndReportData_ReportStatus(
            String portfolioCode, String instrumentCode, Client client,
            String portfolioCode1, String instrumentCode1, Client client1,  ReportStatus reportStatus);



}

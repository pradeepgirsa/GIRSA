package za.co.global.persistence.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.client.Client;
import za.co.global.domain.report.ReportData;
import za.co.global.domain.report.ReportStatus;

import java.util.List;

@Repository
public interface ReportDataRepository extends JpaRepository<ReportData, Long> {

    List<ReportData> findByReportStatusAndClient(ReportStatus reportStatus, Client client);


}

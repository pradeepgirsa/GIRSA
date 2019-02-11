//package za.co.global.persistence.fileupload;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import za.co.global.domain.client.Client;
//import za.co.global.domain.fileupload.client.notused.Holding;
//import za.co.global.domain.report.ReportData;
//import za.co.global.domain.report.ReportStatus;
//
//import java.util.List;
//
//@Repository
//public interface HoldingRepository extends JpaRepository<Holding, Long> {
//
//   /* //TODO - refactor
//    @Query("SELECT h FROM Holding h WHERE h.client =: client AND h.portfolioCode =: portfolioCode " +
//            "AND (h.reportData IS NULL OR h.reportData.reportStatus = 'REGISTERED')")*/
//
//
//    List<Holding> findByPortfolioCodeAndClientAndReportDataIsNullOrPortfolioCodeAndClientAndReportData_ReportStatus(String portfolioCode, Client client,
//                                                                                                              String portfolioCode1, Client client1,
//                                                                                                              ReportStatus reportStatus);
//
//
////    @Query("SELECT h FROM Holding h WHERE h.client =:client AND (h.reportData IS NULL OR h.reportData =:reportData)")
////    List<Holding> findByClientAndReportDataIsNullOrReportData(@Param("client") Client client, @Param("reportData") ReportData reportData);
//
//    List<Holding> findByClientAndReportData(Client client, ReportData reportData);
//
//    List<Holding> findByClientAndReportDataIsNull(Client client);
//
//}

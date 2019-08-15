package za.co.global.services.report;

import za.co.global.domain.exception.GirsaException;
import za.co.global.domain.report.QStatsBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ReportCreationService {

    void createExcelFile(List<QStatsBean> qStatsBeans, String filePath, Map<String, BigDecimal> fundTotalMarketValueMap) throws GirsaException;
}

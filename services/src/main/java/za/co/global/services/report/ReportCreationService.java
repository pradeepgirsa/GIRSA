package za.co.global.services.report;

import za.co.global.domain.exception.GirsaException;
import za.co.global.domain.report.QStatsBean;

import java.util.List;

public interface ReportCreationService {

    void createExcelFile(List<QStatsBean> qStatsBeans, String filePath) throws GirsaException;
}

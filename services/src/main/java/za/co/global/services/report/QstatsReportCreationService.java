package za.co.global.services.report;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.global.domain.exception.GirsaException;
import za.co.global.domain.report.QStatsBean;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;

@Service
public class QstatsReportCreationService implements ReportCreationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QstatsReportCreationService.class);

    private static String[] columns = {"ACIFundCode", "FundName", "ManCoCode", "CreatedDate", "Quarter",
            "MVTotal", "InstitutionalTotal", "NoOfAccounts", "WeightAvgDuration", "WeightAvgMaturity", "ACIAssetClass",
            "InstrCode", "Holding", "BV", "CurrencyCode", "SecurityName", "MV", "PerOfPort", "Weighting", "EqtIndexLink",
            "African", "MarketCap", "SharesInIssue", "AddClassification", "TTMInc", "IssuerCode", "CouponRate",
            "MaturityDate", "ResetMaturityDate", "TTMCur", "InstrRateST", "InstrRateLT", "IssuerRateST", "IssuerRateLT",
            "IssuerName", "RateAgency", "CompConDeb", "MarketCapIssuer", "PerIssuedCap", "CapitalReserves", "ASISADefined1",
            "ASISADefined2", "ASISADefined3", "ASISADefined4", "ASISADefined5"};

    @Override
    public void createExcelFile(List<QStatsBean> qStatsBeans, String filePath) throws GirsaException {

        // Create a Workbook
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fileOut = new FileOutputStream(filePath)) {
            // new HSSFWorkbook() for generating `.xls` file

        /* CreationHelper helps us create instances of various things like DataFormat,
           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
            CreationHelper createHelper = workbook.getCreationHelper();

            // Create a Sheet
            Sheet sheet = workbook.createSheet("Qstats");

            // Create a Font for styling header cells
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
//            headerFont.setColor(IndexedColors.RED.getIndex());

            // Create a CellStyle with the font
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Create a Row
            Row headerRow = sheet.createRow(0);

            // Create cells
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }


            // Create Cell Style for formatting Date
            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/mm/dd"));

            int rowNum = 1;


            // Create Other rows and cells with employees data
            for (QStatsBean qStatsBean : qStatsBeans) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(qStatsBean.getAciFundCode());
                row.createCell(1).setCellValue(qStatsBean.getFundName());
                row.createCell(2).setCellValue(qStatsBean.getMancoCode());

                Cell createdDateCell = row.createCell(3);
                createdDateCell.setCellValue(qStatsBean.getCreatedDate());
                createdDateCell.setCellStyle(dateCellStyle);

                Cell quarterDateCell = row.createCell(4);
                quarterDateCell.setCellValue(qStatsBean.getQuarter());
                quarterDateCell.setCellStyle(dateCellStyle);

                Cell mvTotalCell = row.createCell(5);
                mvTotalCell.setCellValue(qStatsBean.getMvTotal() != null ? qStatsBean.getMvTotal().doubleValue() : null);
                mvTotalCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                Cell institutionalTotalCell = row.createCell(6);
                institutionalTotalCell.setCellValue(qStatsBean.getInstitutionalTotal() != null ? qStatsBean.getInstitutionalTotal().doubleValue() : null);
                institutionalTotalCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                Cell noOfAccountsCell = row.createCell(7);
                noOfAccountsCell.setCellValue(qStatsBean.getNoOfAccounts() != null ? qStatsBean.getNoOfAccounts().doubleValue(): null);
                noOfAccountsCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                Cell weightedAvgDuration = row.createCell(8);



                BigDecimal couponRate = qStatsBean.getCouponRate() != null ? qStatsBean.getCouponRate() : BigDecimal.ZERO;
                BigDecimal currentYield = qStatsBean.getCurrentYield();
                BigDecimal effWeight = qStatsBean.getEffWeight();
                if(couponRate != null && effWeight != null && currentYield != null && qStatsBean.getMaturityDate().after(qStatsBean.getQuarter())) {
                    String reportDateCellReference = CellReference.convertNumToColString(4)+rowNum;
                    String resetMaturityDateCellReference = CellReference.convertNumToColString(27)+rowNum;
                    String formula=String.format("MDURATION(%s,%s,"+
                            couponRate.doubleValue()+","+ currentYield.doubleValue()+",2)*"+ effWeight.doubleValue()+"*"+365.25, reportDateCellReference, resetMaturityDateCellReference);
                    weightedAvgDuration.setCellFormula(formula);
                    weightedAvgDuration.setCellType(Cell.CELL_TYPE_FORMULA);
                }


                BigDecimal weightedAvgMaturity = null;
                if(qStatsBean.getModifiedDuration() != null && effWeight != null) {
                    weightedAvgMaturity = qStatsBean.getModifiedDuration().multiply(effWeight).multiply(BigDecimal.valueOf(365.25));
                }
                Cell weightedAvgMaturityCell = row.createCell(9);
                weightedAvgMaturityCell.setCellValue(weightedAvgMaturity != null ? weightedAvgMaturity.doubleValue() :  null);
                weightedAvgMaturityCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                row.createCell(10).setCellValue(qStatsBean.getAciAssetclass());
                row.createCell(11).setCellValue(qStatsBean.getInstrCode());

                Cell holdingCell = row.createCell(12);
                holdingCell.setCellValue(qStatsBean.getHolding() != null ? qStatsBean.getHolding().doubleValue() : null);
                holdingCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                Cell bookValueCell = row.createCell(13);
                bookValueCell.setCellValue(qStatsBean.getBookValue() != null ? qStatsBean.getBookValue().doubleValue() :  null);
                bookValueCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                row.createCell(14).setCellValue(qStatsBean.getCurrencyCode());
                row.createCell(15).setCellValue(qStatsBean.getSecurityName());

                Cell marketValueCell = row.createCell(16);
                marketValueCell.setCellValue(qStatsBean.getMarketValue() != null ? qStatsBean.getMarketValue().doubleValue() :  null);
                marketValueCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                Cell perOdPertCell = row.createCell(17);
                perOdPertCell.setCellValue(qStatsBean.getPerOfPort() != null ? qStatsBean.getPerOfPort().doubleValue() : null);
                perOdPertCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                Cell weightingCell = row.createCell(18);
                if(qStatsBean.getWeighting() != null) {
                    weightingCell.setCellValue(qStatsBean.getWeighting().doubleValue());
                }
                weightingCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                Cell isEquityIndexLinkCell = row.createCell(19);
                isEquityIndexLinkCell.setCellValue(qStatsBean.isEqtIndexLink());
                isEquityIndexLinkCell.setCellType(Cell.CELL_TYPE_BOOLEAN);

                Cell isAfricanCell = row.createCell(20);
                isAfricanCell.setCellValue(qStatsBean.isAfrican());
                isAfricanCell.setCellType(Cell.CELL_TYPE_BOOLEAN);

                Cell marketCapCell = row.createCell(21);
                marketCapCell.setCellValue(qStatsBean.getMarketCap() != null ? qStatsBean.getMarketCap().doubleValue() : null);
                marketCapCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                Cell sharesInIssueCell = row.createCell(22);
                if(qStatsBean.getSharesInIssue() != null) {
                    sharesInIssueCell.setCellValue(qStatsBean.getSharesInIssue().doubleValue());
                }
                sharesInIssueCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                row.createCell(23).setCellValue(qStatsBean.getAddClassification());
                row.createCell(25).setCellValue(qStatsBean.getIssuerCode());

                Cell couponRateCell = row.createCell(26);
                if(couponRate != null) {
                    couponRateCell.setCellValue(couponRate.doubleValue());
                }
                couponRateCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                Cell maturityDate = row.createCell(27);
                maturityDate.setCellValue(qStatsBean.getMaturityDate());
                maturityDate.setCellStyle(dateCellStyle);

                Cell resetMaturityDate = row.createCell(28);
                if(qStatsBean.getResetMaturityDate() != null) {
                    resetMaturityDate.setCellValue(qStatsBean.getResetMaturityDate());
                }
                resetMaturityDate.setCellStyle(dateCellStyle);

                Cell ttmCurCell = row.createCell(29);
                if(qStatsBean.getTtmCur() != null) {
                    ttmCurCell.setCellValue(qStatsBean.getTtmCur().doubleValue());
                }
                ttmCurCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                row.createCell(30).setCellValue(qStatsBean.getInstrRateST());
                row.createCell(31).setCellValue(qStatsBean.getInstrRateLT());
                row.createCell(32).setCellValue(qStatsBean.getIssuerRateST());
                row.createCell(33).setCellValue(qStatsBean.getIssuerRateLT());
                row.createCell(34).setCellValue(qStatsBean.getIssuerName());
                row.createCell(35).setCellValue(qStatsBean.getRateAgency());
                row.createCell(36).setCellValue(qStatsBean.isCompConDeb());

                Cell marketCapIssuerCell = row.createCell(37);
                if(qStatsBean.getMarketCapIssuer() != null ) {
                    marketCapIssuerCell.setCellValue(qStatsBean.getMarketCapIssuer().doubleValue());
                }
                marketCapIssuerCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                Cell perIssuedCapCell = row.createCell(38);
                if(qStatsBean.getPerIssuedCap() != null) {
                    perIssuedCapCell.setCellValue(qStatsBean.getPerIssuedCap().doubleValue());
                }
                perIssuedCapCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                Cell capitalReservesCell = row.createCell(39);
                if(qStatsBean.getCapitalReserves() != null) {
                    capitalReservesCell.setCellValue(qStatsBean.getCapitalReserves().doubleValue());
                }
                capitalReservesCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                row.createCell(40).setCellValue(qStatsBean.getAsiSADefined1());
                row.createCell(41).setCellValue(qStatsBean.getAsiSADefined2());
                row.createCell(42).setCellValue(qStatsBean.getAsiSADefined3());
                row.createCell(43).setCellValue(qStatsBean.getAsiSADefined4());
                row.createCell(44).setCellValue(qStatsBean.getAsiSADefined5());

            }

            // Resize all columns to fit the content size
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write the output to a file

            workbook.write(fileOut);

            // Closing the workbook
        } catch (Exception e) {
            LOGGER.error("Error while creating Qstats excel file", e);
            throw new GirsaException(e);
        }
    }
}

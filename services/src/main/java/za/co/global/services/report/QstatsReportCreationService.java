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

    private static String[] columns = { "Quarter", "ManCoCode", "CreatedDate", "ACIFundCode", "FundName",
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
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));

            //CellStyle textCellStyle = workbook.createCellStyle();
            //textCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("Text"));
            //textCellStyle.setDataFormat(BuiltinFormats.getBuiltinFormat("text"));

            CellStyle numericCellStyle = workbook.createCellStyle();
            numericCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("##########"));

            CellStyle numeric18Decimal2 = workbook.createCellStyle();
            numeric18Decimal2.setDataFormat(createHelper.createDataFormat().getFormat("0.000000"));
            CellStyle percentageCellStyle = workbook.createCellStyle();
            percentageCellStyle.setDataFormat(workbook.createDataFormat().getFormat("0.000000%"));


            int rowNum = 1;


            // Create Other rows and cells with employees data
            for (QStatsBean qStatsBean : qStatsBeans) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(3).setCellValue(qStatsBean.getAciFundCode());
                row.createCell(4).setCellValue(qStatsBean.getFundName());
                row.createCell(1).setCellValue(qStatsBean.getMancoCode());

                Cell createdDateCell = row.createCell(2);
                if(qStatsBean.getCreatedDate() != null) {
                    createdDateCell.setCellValue(qStatsBean.getCreatedDate());
                }
                createdDateCell.setCellStyle(dateCellStyle);

                Cell quarterDateCell = row.createCell(0);
                if(qStatsBean.getQuarter() != null) {
                    quarterDateCell.setCellValue(qStatsBean.getQuarter());
                }
                quarterDateCell.setCellStyle(dateCellStyle);

                Cell mvTotalCell = row.createCell(5);
                if(qStatsBean.getMvTotal() != null) {
                    mvTotalCell.setCellValue(qStatsBean.getMvTotal().doubleValue());
                }
                mvTotalCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                mvTotalCell.setCellStyle(numeric18Decimal2);

                Cell institutionalTotalCell = row.createCell(6);
                if(qStatsBean.getInstitutionalTotal() != null) {
                    institutionalTotalCell.setCellValue(qStatsBean.getInstitutionalTotal().doubleValue());
                }
                institutionalTotalCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                institutionalTotalCell.setCellStyle(numeric18Decimal2);

                Cell noOfAccountsCell = row.createCell(7);
                if(qStatsBean.getNoOfAccounts() != null) {
                    noOfAccountsCell.setCellValue(qStatsBean.getNoOfAccounts().doubleValue());
                }
                noOfAccountsCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                noOfAccountsCell.setCellStyle(numericCellStyle);

                Cell weightedAvgDuration = row.createCell(8);

                BigDecimal couponRate = qStatsBean.getCouponRate() != null ? qStatsBean.getCouponRate() : BigDecimal.ZERO;
                BigDecimal currentYield = qStatsBean.getCurrentYield();
                BigDecimal effWeight = qStatsBean.getEffWeight();
                if(qStatsBean.getMaturityDate() != null && couponRate != null && effWeight != null && currentYield != null && qStatsBean.getMaturityDate().after(qStatsBean.getQuarter())) {
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
                if(weightedAvgMaturity != null) {
                    weightedAvgMaturityCell.setCellValue(weightedAvgMaturity.doubleValue());
                }
                weightedAvgMaturityCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                weightedAvgMaturityCell.setCellStyle(numeric18Decimal2);

                row.createCell(10).setCellValue(qStatsBean.getAciAssetclass());
                row.createCell(11).setCellValue(qStatsBean.getInstrCode());

                Cell holdingCell = row.createCell(12);
                if(qStatsBean.getHolding() != null) {
                    holdingCell.setCellValue(qStatsBean.getHolding().doubleValue());
                }
                holdingCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                holdingCell.setCellStyle(numeric18Decimal2);

                Cell bookValueCell = row.createCell(13);
                if(qStatsBean.getBookValue() != null) {
                    bookValueCell.setCellValue(qStatsBean.getBookValue().doubleValue());
                }
                bookValueCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                bookValueCell.setCellStyle(numeric18Decimal2);

                row.createCell(14).setCellValue(qStatsBean.getCurrencyCode());
                row.createCell(15).setCellValue(qStatsBean.getSecurityName());

                Cell marketValueCell = row.createCell(16);
                if(qStatsBean.getMarketValue() != null) {
                    marketValueCell.setCellValue(qStatsBean.getMarketValue().doubleValue());
                }
                marketValueCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                marketValueCell.setCellStyle(numeric18Decimal2);

                Cell perOdPertCell = row.createCell(17);
                if(qStatsBean.getPerOfPort() != null) {
                    perOdPertCell.setCellValue(qStatsBean.getPerOfPort().doubleValue());
                    perOdPertCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    perOdPertCell.setCellStyle(percentageCellStyle);
                }

                Cell weightingCell = row.createCell(18);
                if(qStatsBean.getWeighting() != null) {
                    weightingCell.setCellValue(qStatsBean.getWeighting().doubleValue());
                }
                weightingCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                weightingCell.setCellStyle(numeric18Decimal2);

                Cell isEquityIndexLinkCell = row.createCell(19);
                isEquityIndexLinkCell.setCellValue(qStatsBean.isEqtIndexLink() ? "TRUE" : "FALSE");
//                isEquityIndexLinkCell.setCellType(Cell.CELL_TYPE_BOOLEAN);

                Cell isAfricanCell = row.createCell(20);
                isAfricanCell.setCellValue(qStatsBean.isAfrican() ? "TRUE" : "FALSE");
                //isAfricanCell.setCellType(Cell.CELL_TYPE_BOOLEAN);

                Cell marketCapCell = row.createCell(21);
                if(qStatsBean.getMarketCap() != null) {
                    marketCapCell.setCellValue(qStatsBean.getMarketCap().doubleValue());
                }
                marketCapCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                marketCapCell.setCellStyle(numeric18Decimal2);

                Cell sharesInIssueCell = row.createCell(22);
                if(qStatsBean.getSharesInIssue() != null) {
                    sharesInIssueCell.setCellValue(qStatsBean.getSharesInIssue().doubleValue());
                }
                sharesInIssueCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                sharesInIssueCell.setCellStyle(numericCellStyle);

                row.createCell(23).setCellValue(qStatsBean.getAddClassification());
                if(qStatsBean.getIssuerCode() != null) {
                    row.createCell(25).setCellValue(qStatsBean.getIssuerCode());
                }

                Cell couponRateCell = row.createCell(26);
                if(couponRate != null) {
                    couponRateCell.setCellValue(couponRate.doubleValue());
                }
                couponRateCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                couponRateCell.setCellStyle(numeric18Decimal2);

                Cell maturityDate = row.createCell(27);
                if(qStatsBean.getMaturityDate() != null && qStatsBean.getMaturityDate().after(qStatsBean.getQuarter())) {
                    maturityDate.setCellValue(qStatsBean.getMaturityDate());
                }
                maturityDate.setCellStyle(dateCellStyle);

                Cell resetMaturityDate = row.createCell(28);
                if(qStatsBean.getResetMaturityDate() != null && qStatsBean.getMaturityDate().after(qStatsBean.getQuarter())) {
                    resetMaturityDate.setCellValue(qStatsBean.getResetMaturityDate());
                }
                resetMaturityDate.setCellStyle(dateCellStyle);

                Cell ttmCurCell = row.createCell(29);
                if(qStatsBean.getTtmCur() != null) {
                    ttmCurCell.setCellValue(qStatsBean.getTtmCur().doubleValue());
                }
                ttmCurCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                ttmCurCell.setCellStyle(numeric18Decimal2);

                row.createCell(30).setCellValue(qStatsBean.getInstrRateST());
                row.createCell(31).setCellValue(qStatsBean.getInstrRateLT());
                row.createCell(32).setCellValue(qStatsBean.getIssuerRateST());
                row.createCell(33).setCellValue(qStatsBean.getIssuerRateLT());
                row.createCell(34).setCellValue(qStatsBean.getIssuerName()); //Not mapped

                /*Cell issuerNameCell = row.createCell(34); //Not mapped
                if(qStatsBean.getIssuerName() != null){
                    issuerNameCell.setCellValue(qStatsBean.getIssuerName());
                }
                issuerNameCell.setCellType(Cell.CELL_TYPE_STRING);
                issuerNameCell.setCellStyle(textCellStyle);*/

                row.createCell(35).setCellValue(qStatsBean.getRateAgency());
                row.createCell(36).setCellValue(qStatsBean.isCompConDeb() ? "TRUE" : "FALSE");

                Cell marketCapIssuerCell = row.createCell(37);
                if(qStatsBean.getMarketCapIssuer() != null ) {
                    marketCapIssuerCell.setCellValue(qStatsBean.getMarketCapIssuer().doubleValue());
                }
                marketCapIssuerCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                marketCapIssuerCell.setCellStyle(numeric18Decimal2);

                Cell perIssuedCapCell = row.createCell(38);
                if(qStatsBean.getPerIssuedCap() != null) {
                    perIssuedCapCell.setCellValue(qStatsBean.getPerIssuedCap().doubleValue());
                }
                perIssuedCapCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                perIssuedCapCell.setCellStyle(percentageCellStyle);

                Cell capitalReservesCell = row.createCell(39);
                if(qStatsBean.getCapitalReserves() != null) {
                    capitalReservesCell.setCellValue(qStatsBean.getCapitalReserves().doubleValue());
                }
                capitalReservesCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                capitalReservesCell.setCellStyle(numeric18Decimal2);

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

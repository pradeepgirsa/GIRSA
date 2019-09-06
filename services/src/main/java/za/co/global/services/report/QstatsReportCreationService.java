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
import java.util.Map;

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
    public void createExcelFile(List<QStatsBean> qStatsBeans, String filePath, Map<String, BigDecimal> fundTotalMarketValueMap) throws GirsaException {

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

            //CellStyle textCellStyle = workbook.createCellStyle();
            //textCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("Text"));
            //textCellStyle.setDataFormat(BuiltinFormats.getBuiltinFormat("text"));

            CellStyle numericCellStyle = workbook.createCellStyle();
            numericCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("##########"));

            CellStyle numeric18Decimal2 = workbook.createCellStyle();
            numeric18Decimal2.setDataFormat(createHelper.createDataFormat().getFormat("0.00####"));
            CellStyle percentageCellStyle = workbook.createCellStyle();
            percentageCellStyle.setDataFormat(workbook.createDataFormat().getFormat("0.00####%"));


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
                BigDecimal totalMV = fundTotalMarketValueMap.get(qStatsBean.getBarraFundname());
                if(totalMV != null) {
                    mvTotalCell.setCellValue(totalMV.doubleValue());
                }
                mvTotalCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                mvTotalCell.setCellStyle(numeric18Decimal2);

                BigDecimal perOfPort = qStatsBean.getMarketValue() != null && totalMV != null ?
                        qStatsBean.getMarketValue().divide(totalMV, 8, BigDecimal.ROUND_HALF_UP) : null;
                qStatsBean.setPerOfPort(perOfPort);

                Cell institutionalTotalCell = row.createCell(6);
                if(qStatsBean.getInstitutionalTotal() != null  && qStatsBean.getInstitutionalTotal().compareTo(BigDecimal.ZERO) != 0) {
                    institutionalTotalCell.setCellValue(qStatsBean.getInstitutionalTotal().doubleValue());
                    institutionalTotalCell.setCellStyle(numeric18Decimal2);
                }
                institutionalTotalCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                Cell noOfAccountsCell = row.createCell(7);
                if(qStatsBean.getNoOfAccounts() != null) {
                    noOfAccountsCell.setCellValue(qStatsBean.getNoOfAccounts().doubleValue());
                    noOfAccountsCell.setCellStyle(numericCellStyle);
                }
                noOfAccountsCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                Cell weightedAvgDuration = row.createCell(8);

                BigDecimal couponRate = qStatsBean.getCouponRate() != null ? qStatsBean.getCouponRate() : BigDecimal.ZERO;
                BigDecimal currentYield = qStatsBean.getCurrentYield();
                BigDecimal effWeight = qStatsBean.getEffWeight();
                if(qStatsBean.getResetMaturityDate() != null && couponRate != null && effWeight != null && currentYield != null && qStatsBean.getResetMaturityDate().after(qStatsBean.getQuarter())) {
                    String reportDateCellReference = CellReference.convertNumToColString(0)+rowNum;
                    String resetMaturityDateCellRef = CellReference.convertNumToColString(28)+rowNum;
                    String couponRateCellReference = CellReference.convertNumToColString(26)+rowNum;
                    String formula=String.format("MDURATION(%s,%s,"+
                            couponRateCellReference+","+ currentYield.doubleValue()+",2,4)*"+ effWeight.doubleValue()+"*"+365.25, reportDateCellReference, resetMaturityDateCellRef);
                    weightedAvgDuration.setCellFormula(formula);
                    weightedAvgDuration.setCellType(Cell.CELL_TYPE_FORMULA);
                    weightedAvgDuration.setCellStyle(numeric18Decimal2);
                } else {
                    weightedAvgDuration.setCellValue(0d);
                    weightedAvgDuration.setCellType(Cell.CELL_TYPE_NUMERIC);
                }

//                BigDecimal weightedAvgMaturity = null;
//                if(qStatsBean.getModifiedDuration() != null && effWeight != null) {
//                    weightedAvgMaturity = qStatsBean.getModifiedDuration().multiply(effWeight).multiply(BigDecimal.valueOf(365.25));
//                }

                Cell weightedAvgMaturityCell = row.createCell(9);
                if(qStatsBean.getMaturityDate() != null && couponRate != null && effWeight != null && currentYield != null && qStatsBean.getMaturityDate().after(qStatsBean.getQuarter())) {
                    String reportDateCellReference = CellReference.convertNumToColString(0)+rowNum;
                    String maturityDateCellReference = CellReference.convertNumToColString(27)+rowNum;
                    String couponRateCellReference = CellReference.convertNumToColString(26)+rowNum;
                    String formula=String.format("MDURATION(%s,%s,"+
                            couponRateCellReference+","+ currentYield.doubleValue()+",2,4)*"+ effWeight.doubleValue()+"*"+365.25, reportDateCellReference, maturityDateCellReference);
                    weightedAvgMaturityCell.setCellFormula(formula);
                    weightedAvgMaturityCell.setCellType(Cell.CELL_TYPE_FORMULA);
                    weightedAvgMaturityCell.setCellStyle(numeric18Decimal2);
                } else {
                    weightedAvgMaturityCell.setCellValue(0d);
                    weightedAvgMaturityCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                }


//                if(weightedAvgMaturity != null) {
//                    weightedAvgMaturityCell.setCellValue(weightedAvgMaturity.doubleValue());
//                }
//                weightedAvgMaturityCell.setCellType(Cell.CELL_TYPE_NUMERIC);


                row.createCell(10).setCellValue(qStatsBean.getAciAssetclass());
                row.createCell(11).setCellValue(qStatsBean.getInstrCode());

                Cell holdingCell = row.createCell(12);
                if(qStatsBean.getHolding() != null && qStatsBean.getHolding().compareTo(BigDecimal.ZERO) != 0) {
                    holdingCell.setCellValue(qStatsBean.getHolding().doubleValue());
                    holdingCell.setCellStyle(numeric18Decimal2);
                }
                holdingCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                Cell bookValueCell = row.createCell(13);
                if(qStatsBean.getBookValue() != null && qStatsBean.getBookValue().compareTo(BigDecimal.ZERO) != 0) {
                    bookValueCell.setCellValue(qStatsBean.getBookValue().doubleValue());
                    bookValueCell.setCellStyle(numeric18Decimal2);
                }
                bookValueCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                row.createCell(14).setCellValue(qStatsBean.getCurrencyCode());
                row.createCell(15).setCellValue(qStatsBean.getSecurityName());

                Cell marketValueCell = row.createCell(16);
                if(qStatsBean.getMarketValue() != null && qStatsBean.getMarketValue().compareTo(BigDecimal.ZERO) != 0) {
                    marketValueCell.setCellValue(qStatsBean.getMarketValue().doubleValue());
                    marketValueCell.setCellStyle(numeric18Decimal2);
                }
                marketValueCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                Cell perOdPertCell = row.createCell(17);
                if(qStatsBean.getPerOfPort() != null && qStatsBean.getPerOfPort().compareTo(BigDecimal.ZERO) != 0) {
                    perOdPertCell.setCellValue(qStatsBean.getPerOfPort().doubleValue());
                    perOdPertCell.setCellStyle(percentageCellStyle);
                }
                perOdPertCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                Cell weightingCell = row.createCell(18);
                if(qStatsBean.getWeighting() != null && qStatsBean.getWeighting().compareTo(BigDecimal.ZERO) != 0) {
                    weightingCell.setCellValue(qStatsBean.getWeighting().doubleValue());
                    weightingCell.setCellStyle(numeric18Decimal2);
                }
                weightingCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                Cell isEquityIndexLinkCell = row.createCell(19);
                isEquityIndexLinkCell.setCellValue(qStatsBean.isEqtIndexLink() ? "TRUE" : "FALSE");
//                isEquityIndexLinkCell.setCellType(Cell.CELL_TYPE_BOOLEAN);

                Cell isAfricanCell = row.createCell(20);
                isAfricanCell.setCellValue(qStatsBean.isAfrican() ? "TRUE" : "FALSE");
                //isAfricanCell.setCellType(Cell.CELL_TYPE_BOOLEAN);

                Cell marketCapCell = row.createCell(21);
                if(qStatsBean.getMarketCap() != null  && qStatsBean.getMarketCap().compareTo(BigDecimal.ZERO) != 0) {
                    marketCapCell.setCellValue(qStatsBean.getMarketCap().doubleValue());
                    marketCapCell.setCellStyle(numeric18Decimal2);
                }
                marketCapCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                Cell sharesInIssueCell = row.createCell(22);
                if(qStatsBean.getSharesInIssue() != null  && qStatsBean.getSharesInIssue().compareTo(BigDecimal.ZERO) != 0) {
                    sharesInIssueCell.setCellValue(qStatsBean.getSharesInIssue().doubleValue());
                    sharesInIssueCell.setCellStyle(numericCellStyle);
                }
                sharesInIssueCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                row.createCell(23).setCellValue(qStatsBean.getAddClassification());
                if(qStatsBean.getIssuerCode() != null) {
                    row.createCell(25).setCellValue(qStatsBean.getIssuerCode());
                }

                Cell couponRateCell = row.createCell(26);
                if(couponRate != null && couponRate.compareTo(BigDecimal.ZERO) != 0) {
                    couponRateCell.setCellValue((couponRate.multiply(BigDecimal.valueOf(100))).doubleValue());
                    couponRateCell.setCellStyle(numeric18Decimal2);
                }
                couponRateCell.setCellType(Cell.CELL_TYPE_NUMERIC);

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
                if(qStatsBean.getTtmCur() != null && qStatsBean.getTtmCur().compareTo(BigDecimal.ZERO) != 0) {
                    ttmCurCell.setCellValue(qStatsBean.getTtmCur().doubleValue());
                    ttmCurCell.setCellStyle(numeric18Decimal2);
                }
                ttmCurCell.setCellType(Cell.CELL_TYPE_NUMERIC);

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
                if(qStatsBean.getMarketCapIssuer() != null && qStatsBean.getMarketCapIssuer().compareTo(BigDecimal.ZERO) != 0) {
                    marketCapIssuerCell.setCellValue(qStatsBean.getMarketCapIssuer().doubleValue());
                    marketCapIssuerCell.setCellStyle(numeric18Decimal2);
                }
                marketCapIssuerCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                Cell perIssuedCapCell = row.createCell(38);
                if(qStatsBean.getPerIssuedCap() != null && qStatsBean.getPerIssuedCap().compareTo(BigDecimal.ZERO) != 0) {
                    perIssuedCapCell.setCellValue((qStatsBean.getPerIssuedCap()).doubleValue());
                    perIssuedCapCell.setCellStyle(numeric18Decimal2);
                }
                perIssuedCapCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                Cell capitalReservesCell = row.createCell(39);
                if(qStatsBean.getCapitalReserves() != null && qStatsBean.getCapitalReserves().compareTo(BigDecimal.ZERO) != 0) {
                    capitalReservesCell.setCellValue(qStatsBean.getCapitalReserves().doubleValue());
                    capitalReservesCell.setCellStyle(numeric18Decimal2);
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

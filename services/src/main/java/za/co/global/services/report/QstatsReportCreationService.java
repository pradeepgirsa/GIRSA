package za.co.global.services.report;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.global.domain.exception.GirsaException;
import za.co.global.domain.report.QStatsBean;

import java.io.FileOutputStream;
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
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

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
                mvTotalCell.setCellValue(qStatsBean.getMvTotal().doubleValue());
                mvTotalCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                Cell institutionalTotalCell = row.createCell(6);
                institutionalTotalCell.setCellValue(qStatsBean.getInstitutionalTotal().doubleValue());
                institutionalTotalCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                Cell noOfAccountsCell = row.createCell(7);
                noOfAccountsCell.setCellValue(qStatsBean.getNoOfAccounts().doubleValue());
                noOfAccountsCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                java.sql.Date sqlQuarterDate = new java.sql.Date(qStatsBean.getQuarter().getTime());
                java.sql.Date sqlPricingRedemptionDate = new java.sql.Date(qStatsBean.getPricingRedemptionDate().getTime());
                java.sql.Date sqlResetMaturityDate = new java.sql.Date(qStatsBean.getMaturityDate().getTime());

                Cell weightedAvgDeuration = row.createCell(8);
                String formula = "MDURATION("+sqlQuarterDate+","+sqlPricingRedemptionDate+","+sqlResetMaturityDate+","
                        +qStatsBean.getCouponRate().doubleValue()+","+qStatsBean.getCurrentYield().doubleValue()+","
                        +2+")*"+qStatsBean.getEffWeight().doubleValue()+"*"+365.25;
                weightedAvgDeuration.setCellFormula(formula);

//                String settlementCell = CellReference.convertNumToColString(myColumnNumber);
//                String maturityCell = CellReference.convertNumToColString(myColumnNumber);
//                String couponCell = CellReference.convertNumToColString(myColumnNumber);
//                String yieldCell = CellReference.convertNumToColString(myColumnNumber);
//                String formula=String.format("MDURATION(%s;%s;%s:%s;2)*%d*365.25", ccol, row, ccol, row+1);

//                BigDecimal weightedAvgMaturity = null;
//                if(qStatsBean.getModifiedDuration() != null && qStatsBean.getEffWeight() != null) {
//                    weightedAvgMaturity = qStatsBean.getModifiedDuration().multiply(qStatsBean.getEffWeight()).multiply(BigDecimal.valueOf(365.25));
//                }
             //   row.createCell(9).setCellValue(weightedAvgMaturity.doubleValue()); //TODO formula
                row.createCell(10).setCellValue(qStatsBean.getAciAssetclass());
                row.createCell(11).setCellValue(qStatsBean.getInstrCode());
                row.createCell(12).setCellValue(qStatsBean.getHolding().doubleValue());
                row.createCell(13).setCellValue(qStatsBean.getBookValue().doubleValue());
                row.createCell(14).setCellValue(qStatsBean.getCurrencyCode());
                row.createCell(15).setCellValue(qStatsBean.getSecurityName());
                row.createCell(16).setCellValue(qStatsBean.getMarketValue().doubleValue());
                row.createCell(17).setCellValue(qStatsBean.getPerOfPort().doubleValue());
                row.createCell(18).setCellValue(qStatsBean.getWeighting().doubleValue());
                row.createCell(19).setCellValue(qStatsBean.isEqtIndexLink());
                row.createCell(20).setCellValue(qStatsBean.isAfrican());
                row.createCell(21).setCellValue(qStatsBean.getMarketCap().doubleValue());
                row.createCell(22).setCellValue(qStatsBean.getSharesInIssue().doubleValue());
                row.createCell(23).setCellValue(qStatsBean.getAddClassification());
               // row.createCell(24).setCellValue(qStatsBean.getTtmInc().doubleValue());
                row.createCell(25).setCellValue(qStatsBean.getIssuerCode());
                row.createCell(26).setCellValue(qStatsBean.getCouponRate().doubleValue());

                Cell maturityDate = row.createCell(27);
                maturityDate.setCellValue(qStatsBean.getMaturityDate());
                maturityDate.setCellStyle(dateCellStyle);

                Cell resetMaturityDate = row.createCell(28);
                resetMaturityDate.setCellValue(qStatsBean.getResetMaturityDate());
                resetMaturityDate.setCellStyle(dateCellStyle);

                row.createCell(29).setCellValue(qStatsBean.getTtmCur() != null ? qStatsBean.getTtmCur().doubleValue(): 0.0);
                row.createCell(30).setCellValue(qStatsBean.getInstrRateST());
                row.createCell(31).setCellValue(qStatsBean.getInstrRateLT());
                row.createCell(32).setCellValue(qStatsBean.getIssuerRateST());
                row.createCell(33).setCellValue(qStatsBean.getIssuerRateLT());
                row.createCell(34).setCellValue(qStatsBean.getIssuerName());
                row.createCell(35).setCellValue(qStatsBean.getRateAgency());
                row.createCell(36).setCellValue(qStatsBean.isCompConDeb());
                row.createCell(37).setCellValue(qStatsBean.getMarketCapIssuer() != null ? qStatsBean.getMarketCapIssuer().doubleValue() : 0.0);
                row.createCell(38).setCellValue(qStatsBean.getPerIssuedCap() != null ? qStatsBean.getPerIssuedCap().doubleValue() : 0.0);
                row.createCell(39).setCellValue(qStatsBean.getCapitalReserves() != null ? qStatsBean.getCapitalReserves().doubleValue() : 0.0);
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

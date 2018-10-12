package za.co.global.controllers.report;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import za.co.global.domain.fileupload.barra.DSU5_GIRREP4;
import za.co.global.domain.fileupload.client.InstitutionalDetails;
import za.co.global.domain.fileupload.client.NumberOfAccounts;
import za.co.global.domain.fileupload.client.fpm.Holding;
import za.co.global.domain.fileupload.client.fpm.HoldingCategory;
import za.co.global.domain.fileupload.client.fpm.Instrument;
import za.co.global.domain.fileupload.mapping.InstrumentCode;
import za.co.global.domain.fileupload.mapping.PSGFundMapping;
import za.co.global.domain.report.QStatsBean;
import za.co.global.persistence.fileupload.HoldingRepository;
import za.co.global.persistence.fileupload.barra.DSU5_GIRREP4Repository;
import za.co.global.persistence.fileupload.client.InstitutionalDetailsRepository;
import za.co.global.persistence.fileupload.client.NumberOfAccountsRepository;
import za.co.global.persistence.fileupload.mapping.InstrumentCodeRepository;
import za.co.global.persistence.fileupload.mapping.PSGFundMappingRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GenerateQstats {

    @Value("${file.upload.folder}")
    protected String fileUploadFolder;

    private static String[] columns = {"ACIFundCode", "FundName", "ManCoCode", "CreatedDate", "Quarter",
        "MVTotal", "InstitutionalTotal", "NoOfAccounts", "WeightAvgDuration", "WeightAvgMaturity", "ACIAssetClass",
        "InstrCode", "Holding", "BV", "CurrencyCode", "SecurityName", "MV", "PerOfPort", "Weighting", "EqtIndexLink",
            "African", "MarketCap", "SharesInIssue", "AddClassification", "TTMInc", "IssuerCode", "CouponRate",
            "MaturityDate", "ResetMaturityDate", "TTMCur", "InstrRateST", "InstrRateLT", "IssuerRateST", "IssuerRateLT",
            "IssuerName", "RateAgency", "CompConDeb", "MarketCapIssuer", "PerIssuedCap", "CapitalReserves", "ASISADefined1",
            "ASISADefined2", "ASISADefined3", "ASISADefined4", "ASISADefined5"};

    @Autowired
    private HoldingRepository holdingRepository;

    @Autowired
    private PSGFundMappingRepository psgFundMappingRepository;

    @Autowired
    private InstitutionalDetailsRepository institutionalDetailsRepository;

    @Autowired
    private NumberOfAccountsRepository numberOfAccountsRepository;

    @Autowired
    private InstrumentCodeRepository instrumentCodeRepository;

    @Autowired
    private DSU5_GIRREP4Repository dsu5_girrep4Repository;

    @GetMapping("/generate_qstats")
    public ModelAndView showUpload() {
        List<Holding> holdings = holdingRepository.findAll();

        Map<String, PSGFundMapping> psgFundMappings = getPSGFundMappings();
        for(Holding holding: holdings) {
            PSGFundMapping psgFundMapping = psgFundMappings.get(holding.getPortfolioCode());
            NumberOfAccounts numberofAccounts = numberOfAccountsRepository.findByFundCode(psgFundMapping.getPsgFundCode());
            InstitutionalDetails institutionalDetails = institutionalDetailsRepository.findByFundCode(psgFundMapping.getPsgFundCode());
            for(HoldingCategory holdingCategory : holding.getHoldingCategories()) {
                for(Instrument instrument : holdingCategory.getInstruments()) {
                    QStatsBean qStatsBean = new QStatsBean();

                    InstrumentCode instrumentCode = instrumentCodeRepository.findByManagerCode(instrument.getInstrumentCode());
                    DSU5_GIRREP4 dsu5_girrep4 = dsu5_girrep4Repository.findByAssetId(instrumentCode.getBarraCode());

                    qStatsBean.setAciFundCode(psgFundMapping.getPsgFundCode());
                    qStatsBean.setFundName(psgFundMapping.getManagerFundName()); //TODO - fpm source
                    qStatsBean.setMancoCode("PSG"); //TODO - fpm source
                    qStatsBean.setCreatedDate(new Date()); //TODO - fpm source
                    qStatsBean.setQuarter(new Date()); //TODO - fpm source
                    qStatsBean.setMvTotal(holding.getNetBaseCurrentMarketValue()); //TODO - check correct field
                    qStatsBean.setInstitutionalTotal(institutionalDetails.getSplit());
                    qStatsBean.setNoOfAccounts(numberofAccounts.getTotal());
                    qStatsBean.setWeightedAvgDuration(BigDecimal.ZERO); //TODO - calculation
                    qStatsBean.setWeightedAvgMaturity(BigDecimal.ZERO); //TODO - calculation
                    qStatsBean.setAciAssetclass("DE"); ////TODO - calculation
                    qStatsBean.setInstrCode(instrument.getInstrumentCode());
                    qStatsBean.setHolding(instrument.getHoldingPrice());
                    qStatsBean.setBookValue(instrument.getCurrentBookValue());
                    qStatsBean.setCurrencyCode(instrument.getIssueCurrency());
                    qStatsBean.setSecurityName(instrument.getInstrumentDescription());
                    qStatsBean.setMarketValue(instrument.getBaseCurrentMarketValue());

                    BigDecimal perOfPort = qStatsBean.getMarketValue().divide(qStatsBean.getMvTotal());
                    qStatsBean.setPerOfPort(perOfPort); //TODO - calculation

                    qStatsBean.setWeighting(BigDecimal.ZERO); //TODO - calculation
                    qStatsBean.setEqtIndexLink(Boolean.FALSE); //TODO - check
                    qStatsBean.setAfrican(Boolean.FALSE); //TODO - check
                    qStatsBean.setMarketCap(new BigDecimal(dsu5_girrep4.getMarketCapitalization().replaceAll(",", "")));
                    qStatsBean.setSharesInIssue(new BigDecimal(dsu5_girrep4.getSharesOutstanding().replaceAll(",", ""))); //TODO - verify
                    qStatsBean.setAddClassification("Classfication"); //TODO - calculation
                    qStatsBean.setTtmInc(BigDecimal.ZERO); //TODO - calculation
                    qStatsBean.setIssuerCode(dsu5_girrep4.getIssuer()); //TODO - verify
//                    qStatsBean.setCouponRate(dsu5_girrep4.get()); //TODO - verify
                    qStatsBean.setInstrRateST("InstraRateST"); //TODO - check




                }
            }
        }


        ModelAndView modelAndView = new ModelAndView("upload/uploadFile");
        return modelAndView;
    }


    private void createExcel(List<QStatsBean> qStatsBeans) throws IOException {
        // Create a Workbook
        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

        /* CreationHelper helps us create instances of various things like DataFormat,
           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        CreationHelper createHelper = workbook.getCreationHelper();

        // Create a Sheet
        Sheet sheet = workbook.createSheet("Qstats");

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Create cells
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }



        // Create Cell Style for formatting Date
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        int rowNum =1;


        // Create Other rows and cells with employees data
        for(QStatsBean qStatsBean : qStatsBeans) {
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

            row.createCell(5).setCellValue(qStatsBean.getMvTotal().doubleValue());
            row.createCell(6).setCellValue(qStatsBean.getInstitutionalTotal().doubleValue());
            row.createCell(7).setCellValue(qStatsBean.getNoOfAccounts().doubleValue());
            row.createCell(8).setCellValue(qStatsBean.getWeightedAvgDuration().doubleValue());
            row.createCell(9).setCellValue(qStatsBean.getWeightedAvgMaturity().doubleValue());
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
            row.createCell(24).setCellValue(qStatsBean.getTtmInc().doubleValue());
            row.createCell(25).setCellValue(qStatsBean.getIssuerCode());
            row.createCell(26).setCellValue(qStatsBean.getCouponRate().doubleValue());

            Cell maturityDate = row.createCell(27);
            maturityDate.setCellValue(qStatsBean.getMaturityDate());
            maturityDate.setCellStyle(dateCellStyle);

            Cell resetMaturityDate = row.createCell(28);
            resetMaturityDate.setCellValue(qStatsBean.getResetMaturityDate());
            resetMaturityDate.setCellStyle(dateCellStyle);

            row.createCell(29).setCellValue(qStatsBean.getTtmCur().doubleValue());
            row.createCell(30).setCellValue(qStatsBean.getInstrRateST());
            row.createCell(31).setCellValue(qStatsBean.getInstrRateLT());
            row.createCell(32).setCellValue(qStatsBean.getIssuerRateST());
            row.createCell(33).setCellValue(qStatsBean.getIssuerRateLT());
            row.createCell(34).setCellValue(qStatsBean.getIssuerName());
            row.createCell(35).setCellValue(qStatsBean.getRateAgency());
            row.createCell(36).setCellValue(qStatsBean.isCompConDeb());
            row.createCell(37).setCellValue(qStatsBean.getMarketCapIssuer().doubleValue());
            row.createCell(38).setCellValue(qStatsBean.getPerIssuedCap().doubleValue());
            row.createCell(39).setCellValue(qStatsBean.getCapitalReserves().doubleValue());
            row.createCell(40).setCellValue(qStatsBean.getAsiSADefined1());
            row.createCell(41).setCellValue(qStatsBean.getAsiSADefined2());
            row.createCell(42).setCellValue(qStatsBean.getAsiSADefined3());
            row.createCell(43).setCellValue(qStatsBean.getAsiSADefined4());
            row.createCell(44).setCellValue(qStatsBean.getAsiSADefined5());

        }

        // Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(fileUploadFolder+ File.separator +"result.xlsx");
        workbook.write(fileOut);
        fileOut.close();

        // Closing the workbook
        workbook.close();
    }


    private Map<String, PSGFundMapping> getPSGFundMappings() {
        Map<String, PSGFundMapping> psgFundMappingMap = new HashMap<>();
        List<PSGFundMapping> all = psgFundMappingRepository.findAll();
        for(PSGFundMapping psgFundMapping : all) {
            psgFundMappingMap.put(psgFundMapping.getManagerFundCode(), psgFundMapping);
        }
        return psgFundMappingMap;
    }
}



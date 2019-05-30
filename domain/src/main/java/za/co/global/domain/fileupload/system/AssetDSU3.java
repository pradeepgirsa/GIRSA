package za.co.global.domain.fileupload.system;

import com.gizbel.excel.annotations.ExcelBean;
import com.gizbel.excel.annotations.ExcelColumnHeader;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by root on 2019/05/22.
 */
@ExcelBean
@Entity
@Table(name = "barra_asset_dsu3")
public class AssetDSU3 implements Serializable {

    private static final long serialVersionUID = 5672532110913374632L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ExcelColumnHeader(columnHeader = "Asset ID")
    @Column(name = "ASSET_ID")
    private String assetId;

    @ExcelColumnHeader(columnHeader = "Asset Name")
    @Column(name = "ASSET_NAME")
    private String assetName;

    @ExcelColumnHeader(columnHeader = "Holdings", dataType = "big_decimal")
    @Column(name = "HOLDINGS")
    private BigDecimal holdings;

    @ExcelColumnHeader(columnHeader = "Price", dataType = "big_decimal")
    @Column(name = "PRICE")
    private BigDecimal price;

    @ExcelColumnHeader(columnHeader = "Eff Exposure", dataType = "big_decimal")
    @Column(name = "EFF_EXPOSURE")
    private BigDecimal effExposure;

    @ExcelColumnHeader(columnHeader = "Mkt Value", dataType = "big_decimal")
    @Column(name = "MARKET_VALUE")
    private BigDecimal marketValue;

    @ExcelColumnHeader(columnHeader = "Inst. Type")
    @Column(name = "INST_TYPE")
    private String instType;

    @ExcelColumnHeader(columnHeader = "Inst. Sub-type")
    @Column(name = "INST_SUB_TYPE")
    private String instSubType;

    @ExcelColumnHeader(columnHeader = "Eff Weight (%)", dataType = "big_decimal")
    @Column(name = "EFF_WEIGHT")
    private BigDecimal effWeight;

    @ExcelColumnHeader(columnHeader = "GIR Issuer")
    @Column(name = "GIR_ISSUER")
    private String girIssuer;

    @ExcelColumnHeader(columnHeader = "Reg28_InstrType")
    @Column(name = "REG28INSTR_TYPE")
    private String reg28InstrType;

    @ExcelColumnHeader(columnHeader = "SARB Classification")
    @Column(name = "SARB_CLASSIFICATION")
    private String sarbClassification;

    @ExcelColumnHeader(columnHeader = "Africa Values", dataType = "big_decimal")
    @Column(name = "AFRICA_VALUES")
    private BigDecimal africaValues;

    @ExcelColumnHeader(columnHeader = "Market Capitalization", dataType = "big_decimal")
    @Column(name = "MARKET_CAPITALIZATION")
    private BigDecimal marketCapitalization;

    @ExcelColumnHeader(columnHeader = "Amount Issued", dataType = "big_decimal")
    @Column(name = "AMOUNT_ISSUED")
    private BigDecimal amountIssued;

    @ExcelColumnHeader(columnHeader = "ICB Industry")
    @Column(name = "ICB_INDUSTRY")
    private String icbIndustry;

    @ExcelColumnHeader(columnHeader = "ICB Supersector")
    @Column(name = "ICB_SUPER_SECTOR")
    private String icbSuperSector;

    @ExcelColumnHeader(columnHeader = "Subsector")
    @Column(name = "SUB_SECTOR")
    private String subsector;

    @ExcelColumnHeader(columnHeader = "GICS Industry Group")
    @Column(name = "GICS_INDUSTRY_GROUP")
    private String gicsIndustryGroup;

    @ExcelColumnHeader(columnHeader = "Time to Maturity")
    @Column(name = "TIME_TO_MATURITY")
    private String timeToMaturity;

    @ExcelColumnHeader(columnHeader = "Local Market")
    @Column(name = "LOCAL_MARKET")
    private String localMarket;

    @ExcelColumnHeader(columnHeader = "Country Of Exposure")
    @Column(name = "COUNTRY_OF_EXPOSURE")
    private String countryOfExposure;

    @ExcelColumnHeader(columnHeader = "Country Of Incorporation")
    @Column(name = "COUNTRY_OF_INCORPORATION")
    private String countryOfIncorporation;

    @ExcelColumnHeader(columnHeader = "Country Of Quotation")
    @Column(name = "COUNTRY_OF_QUOTATION")
    private String countryOfQuotation;

    @ExcelColumnHeader(columnHeader = "Moody Rating")
    @Column(name = "MOODY_RATING")
    private String moodyRating;

    @ExcelColumnHeader(columnHeader = "Parent IssuerID")
    @Column(name = "PARENT_ISSUER_ID")
    private String parentIssuerID;

    @ExcelColumnHeader(columnHeader = "IssuerShortName")
    @Column(name = "ISSUER_SHORT_NAME")
    private String issuerShortName;

    @Column(name = "NET_INDICATOR")
    private boolean netIndicator = Boolean.FALSE.booleanValue();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public BigDecimal getHoldings() {
        return holdings;
    }

    public void setHoldings(BigDecimal holdings) {
        this.holdings = holdings;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getEffExposure() {
        return effExposure;
    }

    public void setEffExposure(BigDecimal effExposure) {
        this.effExposure = effExposure;
    }

    public BigDecimal getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(BigDecimal marketValue) {
        this.marketValue = marketValue;
    }

    public String getInstType() {
        return instType;
    }

    public void setInstType(String instType) {
        this.instType = instType;
    }

    public String getInstSubType() {
        return instSubType;
    }

    public void setInstSubType(String instSubType) {
        this.instSubType = instSubType;
    }

    public BigDecimal getEffWeight() {
        return effWeight;
    }

    public void setEffWeight(BigDecimal effWeight) {
        this.effWeight = effWeight;
    }

    public String getGirIssuer() {
        return girIssuer;
    }

    public void setGirIssuer(String girIssuer) {
        this.girIssuer = girIssuer;
    }

    public String getReg28InstrType() {
        return reg28InstrType;
    }

    public void setReg28InstrType(String reg28InstrType) {
        this.reg28InstrType = reg28InstrType;
    }

    public String getSarbClassification() {
        return sarbClassification;
    }

    public void setSarbClassification(String sarbClassification) {
        this.sarbClassification = sarbClassification;
    }

    public BigDecimal getAfricaValues() {
        return africaValues;
    }

    public void setAfricaValues(BigDecimal africaValues) {
        this.africaValues = africaValues;
    }

    public BigDecimal getMarketCapitalization() {
        return marketCapitalization;
    }

    public void setMarketCapitalization(BigDecimal marketCapitalization) {
        this.marketCapitalization = marketCapitalization;
    }

    public BigDecimal getAmountIssued() {
        return amountIssued;
    }

    public void setAmountIssued(BigDecimal amountIssued) {
        this.amountIssued = amountIssued;
    }

    public String getIcbIndustry() {
        return icbIndustry;
    }

    public void setIcbIndustry(String icbIndustry) {
        this.icbIndustry = icbIndustry;
    }

    public String getIcbSuperSector() {
        return icbSuperSector;
    }

    public void setIcbSuperSector(String icbSuperSector) {
        this.icbSuperSector = icbSuperSector;
    }

    public String getSubsector() {
        return subsector;
    }

    public void setSubsector(String subsector) {
        this.subsector = subsector;
    }

    public String getGicsIndustryGroup() {
        return gicsIndustryGroup;
    }

    public void setGicsIndustryGroup(String gicsIndustryGroup) {
        this.gicsIndustryGroup = gicsIndustryGroup;
    }

    public String getTimeToMaturity() {
        return timeToMaturity;
    }

    public void setTimeToMaturity(String timeToMaturity) {
        this.timeToMaturity = timeToMaturity;
    }

    public String getLocalMarket() {
        return localMarket;
    }

    public void setLocalMarket(String localMarket) {
        this.localMarket = localMarket;
    }

    public String getCountryOfExposure() {
        return countryOfExposure;
    }

    public void setCountryOfExposure(String countryOfExposure) {
        this.countryOfExposure = countryOfExposure;
    }

    public String getCountryOfIncorporation() {
        return countryOfIncorporation;
    }

    public void setCountryOfIncorporation(String countryOfIncorporation) {
        this.countryOfIncorporation = countryOfIncorporation;
    }

    public String getCountryOfQuotation() {
        return countryOfQuotation;
    }

    public void setCountryOfQuotation(String countryOfQuotation) {
        this.countryOfQuotation = countryOfQuotation;
    }

    public String getMoodyRating() {
        return moodyRating;
    }

    public void setMoodyRating(String moodyRating) {
        this.moodyRating = moodyRating;
    }

    public String getParentIssuerID() {
        return parentIssuerID;
    }

    public void setParentIssuerID(String parentIssuerID) {
        this.parentIssuerID = parentIssuerID;
    }

    public String getIssuerShortName() {
        return issuerShortName;
    }

    public void setIssuerShortName(String issuerShortName) {
        this.issuerShortName = issuerShortName;
    }

    public boolean isNetIndicator() {
        return netIndicator;
    }

    public void setNetIndicator(boolean netIndicator) {
        this.netIndicator = netIndicator;
    }

}

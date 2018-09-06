package za.co.global.domain.upload;

import com.gizbel.excel.annotations.ExcelBean;
import com.gizbel.excel.annotations.ExcelColumnHeader;

import javax.persistence.*;
import java.io.Serializable;

@ExcelBean
@Entity
public class DSU5_GIRREP4 implements Serializable {

    private static final long serialVersionUID = 5500452100911177632L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ExcelColumnHeader(columnHeader = "Asset ID", dataType = "string")
    @Column(name = "ASSET_ID")
    private String assetId;

    @ExcelColumnHeader(columnHeader = "Asset ID Type")
    @Column(name = "ASSET_ID_TYPE")
    private String assetIdType;

    @ExcelColumnHeader(columnHeader = "Asset Name")
    @Column(name = "ASSET_NAME")
    private String assetName;

    @ExcelColumnHeader(columnHeader = "Holdings", dataType = "string")
    @Column(name = "HOLDINGS")
    private String holdings;

    @ExcelColumnHeader(columnHeader = "Price", dataType = "string")
    @Column(name = "PRICE")
    private String price;

    @ExcelColumnHeader(columnHeader = "Eff Exposure", dataType = "string")
    @Column(name = "EFF_EXPOSURE")
    private String effExposure;

    @ExcelColumnHeader(columnHeader = "Inst. Type", dataType = "string")
    @Column(name = "INST_TYPE")
    private String instType;

    @ExcelColumnHeader(columnHeader = "Inst. Sub-type", dataType = "string")
    @Column(name = "INST_SUB_TYPE")
    private String instSubType;

    @ExcelColumnHeader(columnHeader = "Eff Weight (%)", dataType = "string")
    @Column(name = "EFF_WEIGHT")
    private String effWeight;

    @ExcelColumnHeader(columnHeader = "GIR Issuer", dataType = "string")
    @Column(name = "GIR_ISSUER")
    private String girIssuer;

    @ExcelColumnHeader(columnHeader = "Reg28_InstrType", dataType = "string")
    @Column(name = "REG28INSTR_TYPE")
    private String reg28InstrType;

    @ExcelColumnHeader(columnHeader = "Reg30_InstrType")
    @Column(name = "REG30INSTR_TYPE")
    private String reg30InstrType;

    @ExcelColumnHeader(columnHeader = "SARB Classification")
    @Column(name = "SARB_CLASSIFICATION")
    private String sarbClassification;

    @ExcelColumnHeader(columnHeader = "Africa Values", dataType = "string")
    @Column(name = "AFRICA_VALUES")
    private String africaValues;

    @ExcelColumnHeader(columnHeader = "Market Capitalization", dataType = "string")
    @Column(name = "MARKET_CAPITALIZATION")
    private String marketCapitalization;

    @ExcelColumnHeader(columnHeader = "Shares Outstanding")
    @Column(name = "SHARES_OUTSTANDING")
    private String sharesOutstanding;

    @ExcelColumnHeader(columnHeader = "Amount Issued")
    @Column(name = "AMOUNT_ISSUED")
    private String amountIssued;

    @ExcelColumnHeader(columnHeader = "ICB Industry", dataType = "string")
    @Column(name = "ICB_INDUSTRY")
    private String icbIndustry;

    @ExcelColumnHeader(columnHeader = "ICB Supersector", dataType = "string")
    @Column(name = "ICB_SUPER_SECTOR")
    private String icbSuperSector;




    @ExcelColumnHeader(columnHeader = "GIR Industry ICB")
    @Column(name = "GIR_INDUSTRY_ICB")
    private String girIndustryICB;

    @ExcelColumnHeader(columnHeader = "GIR Sector ICB")
    @Column(name = "GIR_SECTOR_ICB")
    private String girSectorICB;

    @ExcelColumnHeader(columnHeader = "GIR Subsector ICB")
    @Column(name = "GIR_SUB_SECTOR_ICB")
    private String girSubsectorICB;

    @ExcelColumnHeader(columnHeader = "GIR Supersector ICB")
    @Column(name = "GIR_SUPER_SECTOR_ICB")
    private String girSupersectorICB;

    @ExcelColumnHeader(columnHeader = "Sector")
    @Column(name = "SECTOR")
    private String sector;

    @ExcelColumnHeader(columnHeader = "Subsector")
    @Column(name = "SUB_SECTOR")
    private String subsector;

    @ExcelColumnHeader(columnHeader = "GICS Industry")
    @Column(name = "GICS_INDUSTRY")
    private String gicsIndustry;

    @ExcelColumnHeader(columnHeader = "GICS Industry Group")
    @Column(name = "GICS_INDUSTRY_GROUP")
    private String gicsIndustryGroup;

    @ExcelColumnHeader(columnHeader = "GICS Sector")
    @Column(name = "GICS_SECTOR")
    private String gicsSector;

    @ExcelColumnHeader(columnHeader = "GICS Sub-Industry")
    @Column(name = "GICS_SUB_INDUSTRY")
    private String gicsSubIndustry;

    @ExcelColumnHeader(columnHeader = "Maturity Date")
    @Column(name = "MATURITY_DATE")
    private String maturityDate;

    @ExcelColumnHeader(columnHeader = "Time to Maturity")
    @Column(name = "TIME_TO_MATURITY")
    private String timeToMaturity;

    @ExcelColumnHeader(columnHeader = "Weighted Average Life")
    @Column(name = "WEIGHTED_AVG_LIFE")
    private String weightedAverageLife;

    @ExcelColumnHeader(columnHeader = "Pricing Redemption Date")
    @Column(name = "PRICING_REDEMPTION_DATE")
    private String pricingRedemptionDate;

    @ExcelColumnHeader(columnHeader = "Current Yield (%)")
    @Column(name = "CURRENT_YIELD_IN_PERCENT")
    private String currentYield;

    @ExcelColumnHeader(columnHeader = "Coupon (%)")
    @Column(name = "COUPON_IN_PERCENTAGE")
    private String coupon;

    @ExcelColumnHeader(columnHeader = "First Coupon Date")
    @Column(name = "FIRST_COUPON_DATE")
    private String firstCouponDate;

    @ExcelColumnHeader(columnHeader = "Effective Duration")
    @Column(name = "EFFECTIVE_DURATION")
    private String effectiveDuration;

    @ExcelColumnHeader(columnHeader = "Macaulay Duration")
    @Column(name = "MACAULAY_DURATION")
    private String macaulayDuration;

    @ExcelColumnHeader(columnHeader = "Modified Duration")
    @Column(name = "MODIFIED_DURATION")
    private String modifiedDuration;

    @ExcelColumnHeader(columnHeader = "Price Currency")
    @Column(name = "PRICE_CURRENCY")
    private String priceCurrency;

    @ExcelColumnHeader(columnHeader = "Local Market", dataType = "string")
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

    @ExcelColumnHeader(columnHeader = "Issuer")
    @Column(name = "ISSUER")
    private String issuer;

    @ExcelColumnHeader(columnHeader = "Moody Rating")
    @Column(name = "MOODY_RATING")
    private String moodyRating;

    @ExcelColumnHeader(columnHeader = "Moody Long-Term Issuer Rating D")
    @Column(name = "MOODY_LONG_TERM_ISSUER_RATING_D")
    private String moodyLongTermIssuerRatingD;

    @ExcelColumnHeader(columnHeader = "Moody Long-Term Issuer Rating F")
    @Column(name = "MOODY_LONG_TERM_ISSUER_RATING_F")
    private String moodyLongTermIssuerRatingF;

    @ExcelColumnHeader(columnHeader = "S&P Long-Term Issuer Rating D")
    @Column(name = "SP_LONG_TERM_ISSUER_RATING_D")
    private String sAndPLongTermIssuerRatingD;

    @ExcelColumnHeader(columnHeader = "S&P Long-Term Issuer Rating F")
    @Column(name = "SP_LONG_TERM_ISSUER_RATING_F")
    private String sAndPLongTermIssuerRatingF;

    @ExcelColumnHeader(columnHeader = "Parent IssuerID")
    @Column(name = "PARENT_ISSUER_ID")
    private String parentIssuerID;

    @ExcelColumnHeader(columnHeader = "Parent Issuer Name")
    @Column(name = "PARENT_ISSUER_NAME")
    private String parentIssuerName;

    @ExcelColumnHeader(columnHeader = "Ultimate IssuerID")
    @Column(name = "ULTIMATE_ISSUER_ID")
    private String ultimateIssuerID;

    @ExcelColumnHeader(columnHeader = "Ultimate Issuer Name")
    @Column(name = "ULTIMATE_ISSUER_NAME")
    private String ultimateIssuerName;


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

    public String getAssetIdType() {
        return assetIdType;
    }

    public void setAssetIdType(String assetIdType) {
        this.assetIdType = assetIdType;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getHoldings() {
        return holdings;
    }

    public void setHoldings(String holdings) {
        this.holdings = holdings;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getEffExposure() {
        return effExposure;
    }

    public void setEffExposure(String effExposure) {
        this.effExposure = effExposure;
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

    public String getEffWeight() {
        return effWeight;
    }

    public void setEffWeight(String effWeight) {
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

    public String getReg30InstrType() {
        return reg30InstrType;
    }

    public void setReg30InstrType(String reg30InstrType) {
        this.reg30InstrType = reg30InstrType;
    }

    public String getSarbClassification() {
        return sarbClassification;
    }

    public void setSarbClassification(String sarbClassification) {
        this.sarbClassification = sarbClassification;
    }

    public String getAfricaValues() {
        return africaValues;
    }

    public void setAfricaValues(String africaValues) {
        this.africaValues = africaValues;
    }

    public String getMarketCapitalization() {
        return marketCapitalization;
    }

    public void setMarketCapitalization(String marketCapitalization) {
        this.marketCapitalization = marketCapitalization;
    }

    public String getSharesOutstanding() {
        return sharesOutstanding;
    }

    public void setSharesOutstanding(String sharesOutstanding) {
        this.sharesOutstanding = sharesOutstanding;
    }

    public String getAmountIssued() {
        return amountIssued;
    }

    public void setAmountIssued(String amountIssued) {
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

    public String getGirIndustryICB() {
        return girIndustryICB;
    }

    public void setGirIndustryICB(String girIndustryICB) {
        this.girIndustryICB = girIndustryICB;
    }

    public String getGirSectorICB() {
        return girSectorICB;
    }

    public void setGirSectorICB(String girSectorICB) {
        this.girSectorICB = girSectorICB;
    }

    public String getGirSubsectorICB() {
        return girSubsectorICB;
    }

    public void setGirSubsectorICB(String girSubsectorICB) {
        this.girSubsectorICB = girSubsectorICB;
    }

    public String getGirSupersectorICB() {
        return girSupersectorICB;
    }

    public void setGirSupersectorICB(String girSupersectorICB) {
        this.girSupersectorICB = girSupersectorICB;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getSubsector() {
        return subsector;
    }

    public void setSubsector(String subsector) {
        this.subsector = subsector;
    }

    public String getGicsIndustry() {
        return gicsIndustry;
    }

    public void setGicsIndustry(String gicsIndustry) {
        this.gicsIndustry = gicsIndustry;
    }

    public String getGicsIndustryGroup() {
        return gicsIndustryGroup;
    }

    public void setGicsIndustryGroup(String gicsIndustryGroup) {
        this.gicsIndustryGroup = gicsIndustryGroup;
    }

    public String getGicsSector() {
        return gicsSector;
    }

    public void setGicsSector(String gicsSector) {
        this.gicsSector = gicsSector;
    }

    public String getGicsSubIndustry() {
        return gicsSubIndustry;
    }

    public void setGicsSubIndustry(String gicsSubIndustry) {
        this.gicsSubIndustry = gicsSubIndustry;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getTimeToMaturity() {
        return timeToMaturity;
    }

    public void setTimeToMaturity(String timeToMaturity) {
        this.timeToMaturity = timeToMaturity;
    }

    public String getWeightedAverageLife() {
        return weightedAverageLife;
    }

    public void setWeightedAverageLife(String weightedAverageLife) {
        this.weightedAverageLife = weightedAverageLife;
    }

    public String getPricingRedemptionDate() {
        return pricingRedemptionDate;
    }

    public void setPricingRedemptionDate(String pricingRedemptionDate) {
        this.pricingRedemptionDate = pricingRedemptionDate;
    }

    public String getCurrentYield() {
        return currentYield;
    }

    public void setCurrentYield(String currentYield) {
        this.currentYield = currentYield;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getFirstCouponDate() {
        return firstCouponDate;
    }

    public void setFirstCouponDate(String firstCouponDate) {
        this.firstCouponDate = firstCouponDate;
    }

    public String getEffectiveDuration() {
        return effectiveDuration;
    }

    public void setEffectiveDuration(String effectiveDuration) {
        this.effectiveDuration = effectiveDuration;
    }

    public String getMacaulayDuration() {
        return macaulayDuration;
    }

    public void setMacaulayDuration(String macaulayDuration) {
        this.macaulayDuration = macaulayDuration;
    }

    public String getModifiedDuration() {
        return modifiedDuration;
    }

    public void setModifiedDuration(String modifiedDuration) {
        this.modifiedDuration = modifiedDuration;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
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

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getMoodyRating() {
        return moodyRating;
    }

    public void setMoodyRating(String moodyRating) {
        this.moodyRating = moodyRating;
    }

    public String getMoodyLongTermIssuerRatingD() {
        return moodyLongTermIssuerRatingD;
    }

    public void setMoodyLongTermIssuerRatingD(String moodyLongTermIssuerRatingD) {
        this.moodyLongTermIssuerRatingD = moodyLongTermIssuerRatingD;
    }

    public String getMoodyLongTermIssuerRatingF() {
        return moodyLongTermIssuerRatingF;
    }

    public void setMoodyLongTermIssuerRatingF(String moodyLongTermIssuerRatingF) {
        this.moodyLongTermIssuerRatingF = moodyLongTermIssuerRatingF;
    }

    public String getsAndPLongTermIssuerRatingD() {
        return sAndPLongTermIssuerRatingD;
    }

    public void setsAndPLongTermIssuerRatingD(String sAndPLongTermIssuerRatingD) {
        this.sAndPLongTermIssuerRatingD = sAndPLongTermIssuerRatingD;
    }

    public String getsAndPLongTermIssuerRatingF() {
        return sAndPLongTermIssuerRatingF;
    }

    public void setsAndPLongTermIssuerRatingF(String sAndPLongTermIssuerRatingF) {
        this.sAndPLongTermIssuerRatingF = sAndPLongTermIssuerRatingF;
    }

    public String getParentIssuerID() {
        return parentIssuerID;
    }

    public void setParentIssuerID(String parentIssuerID) {
        this.parentIssuerID = parentIssuerID;
    }

    public String getParentIssuerName() {
        return parentIssuerName;
    }

    public void setParentIssuerName(String parentIssuerName) {
        this.parentIssuerName = parentIssuerName;
    }

    public String getUltimateIssuerID() {
        return ultimateIssuerID;
    }

    public void setUltimateIssuerID(String ultimateIssuerID) {
        this.ultimateIssuerID = ultimateIssuerID;
    }

    public String getUltimateIssuerName() {
        return ultimateIssuerName;
    }

    public void setUltimateIssuerName(String ultimateIssuerName) {
        this.ultimateIssuerName = ultimateIssuerName;
    }
}

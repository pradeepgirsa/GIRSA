package za.co.global.domain.fileupload.system;

import com.gizbel.excel.annotations.ExcelBean;
import com.gizbel.excel.annotations.ExcelColumnHeader;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ExcelBean
@Entity
@Table(name = "barra_asset_info")
public class BarraAssetInfo implements Serializable {

    private static final long serialVersionUID = 5501452100911177632L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ExcelColumnHeader(columnHeader = "Asset ID")
    @Column(name = "ASSET_ID")
    private String assetId;

    @ExcelColumnHeader(columnHeader = "Asset ID Type")
    @Column(name = "ASSET_ID_TYPE")
    private String assetIdType;

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

    @ExcelColumnHeader(columnHeader = "Reg30_InstrType")
    @Column(name = "REG30INSTR_TYPE")
    private String reg30InstrType;

    @ExcelColumnHeader(columnHeader = "SARB Classification")
    @Column(name = "SARB_CLASSIFICATION")
    private String sarbClassification;

    @ExcelColumnHeader(columnHeader = "Africa Values", dataType = "big_decimal")
    @Column(name = "AFRICA_VALUES")
    private BigDecimal africaValues;

    @ExcelColumnHeader(columnHeader = "Market Capitalization", dataType = "big_decimal")
    @Column(name = "MARKET_CAPITALIZATION")
    private BigDecimal marketCapitalization;

    @ExcelColumnHeader(columnHeader = "Shares Outstanding", dataType = "big_decimal")
    @Column(name = "SHARES_OUTSTANDING")
    private BigDecimal sharesOutstanding;

    @ExcelColumnHeader(columnHeader = "Amount Issued", dataType = "big_decimal")
    @Column(name = "AMOUNT_ISSUED")
    private BigDecimal amountIssued;

    @ExcelColumnHeader(columnHeader = "ICB Industry")
    @Column(name = "ICB_INDUSTRY")
    private String icbIndustry;

    @ExcelColumnHeader(columnHeader = "ICB Supersector")
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

    @ExcelColumnHeader(columnHeader = "Maturity Date", dataType = "date")
    @Column(name = "MATURITY_DATE")
    private Date maturityDate;

    @ExcelColumnHeader(columnHeader = "Time to Maturity")
    @Column(name = "TIME_TO_MATURITY")
    private String timeToMaturity;

    @ExcelColumnHeader(columnHeader = "Weighted Average Life")
    @Column(name = "WEIGHTED_AVG_LIFE")
    private String weightedAverageLife;

    @ExcelColumnHeader(columnHeader = "Pricing Redemption Date", dataType = "date")
    @Column(name = "PRICING_REDEMPTION_DATE")
    private Date pricingRedemptionDate;

    @ExcelColumnHeader(columnHeader = "Current Yield (%)", dataType = "big_decimal")
    @Column(name = "CURRENT_YIELD_IN_PERCENT")
    private BigDecimal currentYield;

    @ExcelColumnHeader(columnHeader = "Coupon (%)", dataType = "big_decimal")
    @Column(name = "COUPON_IN_PERCENTAGE")
    private BigDecimal coupon;

    @ExcelColumnHeader(columnHeader = "First Coupon Date")
    @Column(name = "FIRST_COUPON_DATE") //TODO - check for date field
    private String firstCouponDate;

    @ExcelColumnHeader(columnHeader = "Effective Duration")
    @Column(name = "EFFECTIVE_DURATION")
    private String effectiveDuration;

    @ExcelColumnHeader(columnHeader = "Macaulay Duration")
    @Column(name = "MACAULAY_DURATION")
    private String macaulayDuration;

    @ExcelColumnHeader(columnHeader = "Modified Duration", dataType="big_decimal")
    @Column(name = "MODIFIED_DURATION")
    private BigDecimal modifiedDuration;

    @ExcelColumnHeader(columnHeader = "Price Currency")
    @Column(name = "PRICE_CURRENCY")
    private String priceCurrency;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public Date getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Date maturityDate) {
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

    public Date getPricingRedemptionDate() {
        return pricingRedemptionDate;
    }

    public void setPricingRedemptionDate(Date pricingRedemptionDate) {
        this.pricingRedemptionDate = pricingRedemptionDate;
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

    public BigDecimal getModifiedDuration() {
        return modifiedDuration;
    }

    public void setModifiedDuration(BigDecimal modifiedDuration) {
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

    public BigDecimal getHoldings() {
        return holdings;
    }

    public void setHoldings(BigDecimal holdings) {
        this.holdings = holdings;
    }

    public BigDecimal getEffExposure() {
        return effExposure;
    }

    public void setEffExposure(BigDecimal effExposure) {
        this.effExposure = effExposure;
    }

    public BigDecimal getEffWeight() {
        return effWeight;
    }

    public void setEffWeight(BigDecimal effWeight) {
        this.effWeight = effWeight;
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

    public BigDecimal getSharesOutstanding() {
        return sharesOutstanding;
    }

    public void setSharesOutstanding(BigDecimal sharesOutstanding) {
        this.sharesOutstanding = sharesOutstanding;
    }

    public BigDecimal getAmountIssued() {
        return amountIssued;
    }

    public void setAmountIssued(BigDecimal amountIssued) {
        this.amountIssued = amountIssued;
    }

    public BigDecimal getCurrentYield() {
        return currentYield;
    }

    public void setCurrentYield(BigDecimal currentYield) {
        this.currentYield = currentYield;
    }

    public BigDecimal getCoupon() {
        return coupon;
    }

    public void setCoupon(BigDecimal coupon) {
        this.coupon = coupon;
    }

    public boolean isNetIndicator() {
        return netIndicator;
    }

    public void setNetIndicator(boolean netIndicator) {
        this.netIndicator = netIndicator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BarraAssetInfo that = (BarraAssetInfo) o;

        if (assetId != null ? !assetId.equals(that.assetId) : that.assetId != null) return false;
        if (assetIdType != null ? !assetIdType.equals(that.assetIdType) : that.assetIdType != null) return false;
        if (assetName != null ? !assetName.equals(that.assetName) : that.assetName != null) return false;
        if (holdings != null ? !holdings.equals(that.holdings) : that.holdings != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (effExposure != null ? !effExposure.equals(that.effExposure) : that.effExposure != null) return false;
        if (instType != null ? !instType.equals(that.instType) : that.instType != null) return false;
        if (instSubType != null ? !instSubType.equals(that.instSubType) : that.instSubType != null) return false;
        if (effWeight != null ? !effWeight.equals(that.effWeight) : that.effWeight != null) return false;
        if (girIssuer != null ? !girIssuer.equals(that.girIssuer) : that.girIssuer != null) return false;
        if (reg28InstrType != null ? !reg28InstrType.equals(that.reg28InstrType) : that.reg28InstrType != null)
            return false;
        if (reg30InstrType != null ? !reg30InstrType.equals(that.reg30InstrType) : that.reg30InstrType != null)
            return false;
        if (sarbClassification != null ? !sarbClassification.equals(that.sarbClassification) : that.sarbClassification != null)
            return false;
        if (africaValues != null ? !africaValues.equals(that.africaValues) : that.africaValues != null) return false;
        if (marketCapitalization != null ? !marketCapitalization.equals(that.marketCapitalization) : that.marketCapitalization != null)
            return false;
        if (sharesOutstanding != null ? !sharesOutstanding.equals(that.sharesOutstanding) : that.sharesOutstanding != null)
            return false;
        if (amountIssued != null ? !amountIssued.equals(that.amountIssued) : that.amountIssued != null) return false;
        if (icbIndustry != null ? !icbIndustry.equals(that.icbIndustry) : that.icbIndustry != null) return false;
        if (icbSuperSector != null ? !icbSuperSector.equals(that.icbSuperSector) : that.icbSuperSector != null)
            return false;
        if (girIndustryICB != null ? !girIndustryICB.equals(that.girIndustryICB) : that.girIndustryICB != null)
            return false;
        if (girSectorICB != null ? !girSectorICB.equals(that.girSectorICB) : that.girSectorICB != null) return false;
        if (girSubsectorICB != null ? !girSubsectorICB.equals(that.girSubsectorICB) : that.girSubsectorICB != null)
            return false;
        if (girSupersectorICB != null ? !girSupersectorICB.equals(that.girSupersectorICB) : that.girSupersectorICB != null)
            return false;
        if (sector != null ? !sector.equals(that.sector) : that.sector != null) return false;
        if (subsector != null ? !subsector.equals(that.subsector) : that.subsector != null) return false;
        if (gicsIndustry != null ? !gicsIndustry.equals(that.gicsIndustry) : that.gicsIndustry != null) return false;
        if (gicsIndustryGroup != null ? !gicsIndustryGroup.equals(that.gicsIndustryGroup) : that.gicsIndustryGroup != null)
            return false;
        if (gicsSector != null ? !gicsSector.equals(that.gicsSector) : that.gicsSector != null) return false;
        if (gicsSubIndustry != null ? !gicsSubIndustry.equals(that.gicsSubIndustry) : that.gicsSubIndustry != null)
            return false;
        if (maturityDate != null ? !maturityDate.equals(that.maturityDate) : that.maturityDate != null) return false;
        if (timeToMaturity != null ? !timeToMaturity.equals(that.timeToMaturity) : that.timeToMaturity != null)
            return false;
        if (weightedAverageLife != null ? !weightedAverageLife.equals(that.weightedAverageLife) : that.weightedAverageLife != null)
            return false;
        if (pricingRedemptionDate != null ? !pricingRedemptionDate.equals(that.pricingRedemptionDate) : that.pricingRedemptionDate != null)
            return false;
        if (currentYield != null ? !currentYield.equals(that.currentYield) : that.currentYield != null) return false;
        if (coupon != null ? !coupon.equals(that.coupon) : that.coupon != null) return false;
        if (firstCouponDate != null ? !firstCouponDate.equals(that.firstCouponDate) : that.firstCouponDate != null)
            return false;
        if (effectiveDuration != null ? !effectiveDuration.equals(that.effectiveDuration) : that.effectiveDuration != null)
            return false;
        if (macaulayDuration != null ? !macaulayDuration.equals(that.macaulayDuration) : that.macaulayDuration != null)
            return false;
        if (modifiedDuration != null ? !modifiedDuration.equals(that.modifiedDuration) : that.modifiedDuration != null)
            return false;
        if (priceCurrency != null ? !priceCurrency.equals(that.priceCurrency) : that.priceCurrency != null)
            return false;
        if (localMarket != null ? !localMarket.equals(that.localMarket) : that.localMarket != null) return false;
        if (countryOfExposure != null ? !countryOfExposure.equals(that.countryOfExposure) : that.countryOfExposure != null)
            return false;
        if (countryOfIncorporation != null ? !countryOfIncorporation.equals(that.countryOfIncorporation) : that.countryOfIncorporation != null)
            return false;
        if (countryOfQuotation != null ? !countryOfQuotation.equals(that.countryOfQuotation) : that.countryOfQuotation != null)
            return false;
        if (issuer != null ? !issuer.equals(that.issuer) : that.issuer != null) return false;
        if (moodyRating != null ? !moodyRating.equals(that.moodyRating) : that.moodyRating != null) return false;
        if (moodyLongTermIssuerRatingD != null ? !moodyLongTermIssuerRatingD.equals(that.moodyLongTermIssuerRatingD) : that.moodyLongTermIssuerRatingD != null)
            return false;
        if (moodyLongTermIssuerRatingF != null ? !moodyLongTermIssuerRatingF.equals(that.moodyLongTermIssuerRatingF) : that.moodyLongTermIssuerRatingF != null)
            return false;
        if (sAndPLongTermIssuerRatingD != null ? !sAndPLongTermIssuerRatingD.equals(that.sAndPLongTermIssuerRatingD) : that.sAndPLongTermIssuerRatingD != null)
            return false;
        if (sAndPLongTermIssuerRatingF != null ? !sAndPLongTermIssuerRatingF.equals(that.sAndPLongTermIssuerRatingF) : that.sAndPLongTermIssuerRatingF != null)
            return false;
        if (parentIssuerID != null ? !parentIssuerID.equals(that.parentIssuerID) : that.parentIssuerID != null)
            return false;
        if (parentIssuerName != null ? !parentIssuerName.equals(that.parentIssuerName) : that.parentIssuerName != null)
            return false;
        if (ultimateIssuerID != null ? !ultimateIssuerID.equals(that.ultimateIssuerID) : that.ultimateIssuerID != null)
            return false;
        return ultimateIssuerName != null ? ultimateIssuerName.equals(that.ultimateIssuerName) : that.ultimateIssuerName == null;
    }

    @Override
    public int hashCode() {
        int result = assetId != null ? assetId.hashCode() : 0;
        result = 31 * result + (assetIdType != null ? assetIdType.hashCode() : 0);
        result = 31 * result + (assetName != null ? assetName.hashCode() : 0);
        result = 31 * result + (holdings != null ? holdings.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (effExposure != null ? effExposure.hashCode() : 0);
        result = 31 * result + (instType != null ? instType.hashCode() : 0);
        result = 31 * result + (instSubType != null ? instSubType.hashCode() : 0);
        result = 31 * result + (effWeight != null ? effWeight.hashCode() : 0);
        result = 31 * result + (girIssuer != null ? girIssuer.hashCode() : 0);
        result = 31 * result + (reg28InstrType != null ? reg28InstrType.hashCode() : 0);
        result = 31 * result + (reg30InstrType != null ? reg30InstrType.hashCode() : 0);
        result = 31 * result + (sarbClassification != null ? sarbClassification.hashCode() : 0);
        result = 31 * result + (africaValues != null ? africaValues.hashCode() : 0);
        result = 31 * result + (marketCapitalization != null ? marketCapitalization.hashCode() : 0);
        result = 31 * result + (sharesOutstanding != null ? sharesOutstanding.hashCode() : 0);
        result = 31 * result + (amountIssued != null ? amountIssued.hashCode() : 0);
        result = 31 * result + (icbIndustry != null ? icbIndustry.hashCode() : 0);
        result = 31 * result + (icbSuperSector != null ? icbSuperSector.hashCode() : 0);
        result = 31 * result + (girIndustryICB != null ? girIndustryICB.hashCode() : 0);
        result = 31 * result + (girSectorICB != null ? girSectorICB.hashCode() : 0);
        result = 31 * result + (girSubsectorICB != null ? girSubsectorICB.hashCode() : 0);
        result = 31 * result + (girSupersectorICB != null ? girSupersectorICB.hashCode() : 0);
        result = 31 * result + (sector != null ? sector.hashCode() : 0);
        result = 31 * result + (subsector != null ? subsector.hashCode() : 0);
        result = 31 * result + (gicsIndustry != null ? gicsIndustry.hashCode() : 0);
        result = 31 * result + (gicsIndustryGroup != null ? gicsIndustryGroup.hashCode() : 0);
        result = 31 * result + (gicsSector != null ? gicsSector.hashCode() : 0);
        result = 31 * result + (gicsSubIndustry != null ? gicsSubIndustry.hashCode() : 0);
        result = 31 * result + (maturityDate != null ? maturityDate.hashCode() : 0);
        result = 31 * result + (timeToMaturity != null ? timeToMaturity.hashCode() : 0);
        result = 31 * result + (weightedAverageLife != null ? weightedAverageLife.hashCode() : 0);
        result = 31 * result + (pricingRedemptionDate != null ? pricingRedemptionDate.hashCode() : 0);
        result = 31 * result + (currentYield != null ? currentYield.hashCode() : 0);
        result = 31 * result + (coupon != null ? coupon.hashCode() : 0);
        result = 31 * result + (firstCouponDate != null ? firstCouponDate.hashCode() : 0);
        result = 31 * result + (effectiveDuration != null ? effectiveDuration.hashCode() : 0);
        result = 31 * result + (macaulayDuration != null ? macaulayDuration.hashCode() : 0);
        result = 31 * result + (modifiedDuration != null ? modifiedDuration.hashCode() : 0);
        result = 31 * result + (priceCurrency != null ? priceCurrency.hashCode() : 0);
        result = 31 * result + (localMarket != null ? localMarket.hashCode() : 0);
        result = 31 * result + (countryOfExposure != null ? countryOfExposure.hashCode() : 0);
        result = 31 * result + (countryOfIncorporation != null ? countryOfIncorporation.hashCode() : 0);
        result = 31 * result + (countryOfQuotation != null ? countryOfQuotation.hashCode() : 0);
        result = 31 * result + (issuer != null ? issuer.hashCode() : 0);
        result = 31 * result + (moodyRating != null ? moodyRating.hashCode() : 0);
        result = 31 * result + (moodyLongTermIssuerRatingD != null ? moodyLongTermIssuerRatingD.hashCode() : 0);
        result = 31 * result + (moodyLongTermIssuerRatingF != null ? moodyLongTermIssuerRatingF.hashCode() : 0);
        result = 31 * result + (sAndPLongTermIssuerRatingD != null ? sAndPLongTermIssuerRatingD.hashCode() : 0);
        result = 31 * result + (sAndPLongTermIssuerRatingF != null ? sAndPLongTermIssuerRatingF.hashCode() : 0);
        result = 31 * result + (parentIssuerID != null ? parentIssuerID.hashCode() : 0);
        result = 31 * result + (parentIssuerName != null ? parentIssuerName.hashCode() : 0);
        result = 31 * result + (ultimateIssuerID != null ? ultimateIssuerID.hashCode() : 0);
        result = 31 * result + (ultimateIssuerName != null ? ultimateIssuerName.hashCode() : 0);
        return result;
    }

    public void setUltimateIssuerName(String ultimateIssuerName) {
        this.ultimateIssuerName = ultimateIssuerName;
    }


}

//package za.co.global.domain.upload;
//
//import com.gizbel.excel.annotations.ExcelBean;
//import com.gizbel.excel.annotations.ExcelColumnHeader;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//
//@ExcelBean
////@Entity
//public class DSU5 {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ExcelColumnHeader(columnHeader = "Fund ID")
//    private String assetId;
//
//    @ExcelColumnHeader(columnHeader = "Fund ID Type")
//    private String assetIdType;
//
//    @ExcelColumnHeader(columnHeader = "Fund Name")
//    private String assetName;
//
//    @ExcelColumnHeader(columnHeader = "Holdings")
//    private String holdings;
//
//    @ExcelColumnHeader(columnHeader = "Price")
//    private String price;
//
//    @ExcelColumnHeader(columnHeader = "Eff Exposure")
//    private String effExposure;
//
//    @ExcelColumnHeader(columnHeader = "Mkt Value")
//    private String mktValue;
//
//    @ExcelColumnHeader(columnHeader = "Inst. Type")
//    private String instType;
//
//    @ExcelColumnHeader(columnHeader = "Inst. Sub-type")
//    private String instSubType;
//
//    @ExcelColumnHeader(columnHeader = "Eff Weight (%)")
//    private String effWeight;
//
//    @ExcelColumnHeader(columnHeader = "GIR Issuer")
//    private String girIssuer;
//
//    @ExcelColumnHeader(columnHeader = "Reg28_InstrType")
//    private String reg28InstrType;
//
//    @ExcelColumnHeader(columnHeader = "Reg30_InstrType")
//    private String reg30InstrType;
//
//    @ExcelColumnHeader(columnHeader = "SARB Classification")
//    private String sarbClassification;
//
//    @ExcelColumnHeader(columnHeader = "Africa Values")
//    private String africaValues;
//
//    @ExcelColumnHeader(columnHeader = "Market Capitalization")
//    private String marketCapitalization;
//
//    @ExcelColumnHeader(columnHeader = "Shares Outstanding")
//    private String sharesOutstanding;
//
//    @ExcelColumnHeader(columnHeader = "Amount Issued")
//    private String amountIssued;
//
//    @ExcelColumnHeader(columnHeader = "ICB Industry")
//    private String icbIndustry;
//
//    @ExcelColumnHeader(columnHeader = "ICB Supersector")
//    private String icbSuperSector;
//
//    @ExcelColumnHeader(columnHeader = "GIR Industry ICB")
//    private String girIndustryICB;
//
//    @ExcelColumnHeader(columnHeader = "GIR Sector ICB")
//    private String girSectorICB;
//
//    @ExcelColumnHeader(columnHeader = "GIR Subsector ICB")
//    private String girSubsectorICB;
//
//    @ExcelColumnHeader(columnHeader = "GIR Supersector ICB")
//    private String girSupersectorICB;
//
//    @ExcelColumnHeader(columnHeader = "Sector")
//    private String sector;
//
//    @ExcelColumnHeader(columnHeader = "Subsector")
//    private String subsector;
//
//    @ExcelColumnHeader(columnHeader = "GICS Industry")
//    private String gicsIndustry;
//
//    @ExcelColumnHeader(columnHeader = "GICS Industry Group")
//    private String gicsIndustryGroup;
//
//    @ExcelColumnHeader(columnHeader = "GICS Sector")
//    private String gicsSector;
//
//    @ExcelColumnHeader(columnHeader = "GICS Sub-Industry")
//    private String gicsSubIndustry;
//
//    @ExcelColumnHeader(columnHeader = "Maturity Date")
//    private String maturityDate;
//
//    @ExcelColumnHeader(columnHeader = "Time to Maturity")
//    private String timeToMaturity;
//
//    @ExcelColumnHeader(columnHeader = "Weighted Average Life")
//    private String weightedAverageLife;
//
//    @ExcelColumnHeader(columnHeader = "Pricing Redemption Date")
//    private String pricingRedemptionDate;
//
//    @ExcelColumnHeader(columnHeader = "Current Yield (%)")
//    private String currentYield;
//
//    @ExcelColumnHeader(columnHeader = "Coupon (%)")
//    private String coupon;
//
//    @ExcelColumnHeader(columnHeader = "First Coupon Date")
//    private String firstCouponDate;
//
//    @ExcelColumnHeader(columnHeader = "Effective Duration")
//    private String effectiveDuration;
//
//    @ExcelColumnHeader(columnHeader = "Macaulay Duration")
//    private String macaulayDuration;
//
//    @ExcelColumnHeader(columnHeader = "Modified Duration")
//    private String modifiedDuration;
//
//    @ExcelColumnHeader(columnHeader = "Price Currency")
//    private String priceCurrency;
//
//    @ExcelColumnHeader(columnHeader = "Local Market")
//    private String localMarket;
//
//    @ExcelColumnHeader(columnHeader = "Country Of Exposure")
//    private String countryOfExposure;
//
//    @ExcelColumnHeader(columnHeader = "Country Of Incorporation")
//    private String countryOfIncorporation;
//
//    @ExcelColumnHeader(columnHeader = "Country Of Quotation")
//    private String countryOfQuotation;
//
//    @ExcelColumnHeader(columnHeader = "Issuer")
//    private String issuer;
//
//    @ExcelColumnHeader(columnHeader = "Moody Rating")
//    private String moodyRating;
//
//    @ExcelColumnHeader(columnHeader = "Moody Long-Term Issuer Rating D")
//    private String moodyLongTermIssuerRatingD;
//
//    @ExcelColumnHeader(columnHeader = "Moody Long-Term Issuer Rating F")
//    private String moodyLongTermIssuerRatingF;
//
//    @ExcelColumnHeader(columnHeader = "S&P Long-Term Issuer Rating D")
//    private String sAndPLongTermIssuerRatingD;
//
//    @ExcelColumnHeader(columnHeader = "S&P Long-Term Issuer Rating F")
//    private String sAndPLongTermIssuerRatingF;
//
//    @ExcelColumnHeader(columnHeader = "Parent IssuerID")
//    private String parentIssuerID;
//
//    @ExcelColumnHeader(columnHeader = "Parent Issuer Name")
//    private String parentIssuerName;
//
//    @ExcelColumnHeader(columnHeader = "Ultimate IssuerID")
//    private String ultimateIssuerID;
//
//    @ExcelColumnHeader(columnHeader = "Ultimate Issuer Name")
//    private String ultimateIssuerName;
//
//    public String getAssetId() {
//        return assetId;
//    }
//
//    public void setAssetId(String assetId) {
//        this.assetId = assetId;
//    }
//
//    public String getAssetIdType() {
//        return assetIdType;
//    }
//
//    public void setAssetIdType(String assetIdType) {
//        this.assetIdType = assetIdType;
//    }
//
//    public String getAssetName() {
//        return assetName;
//    }
//
//    public void setAssetName(String assetName) {
//        this.assetName = assetName;
//    }
//
//    public String getHoldings() {
//        return holdings;
//    }
//
//    public void setHoldings(String holdings) {
//        this.holdings = holdings;
//    }
//
//    public String getPrice() {
//        return price;
//    }
//
//    public void setPrice(String price) {
//        this.price = price;
//    }
//
//    public String getEffExposure() {
//        return effExposure;
//    }
//
//    public void setEffExposure(String effExposure) {
//        this.effExposure = effExposure;
//    }
//
//    public String getMktValue() {
//        return mktValue;
//    }
//
//    public void setMktValue(String mktValue) {
//        this.mktValue = mktValue;
//    }
//
//    public String getInstType() {
//        return instType;
//    }
//
//    public void setInstType(String instType) {
//        this.instType = instType;
//    }
//
//    public String getInstSubType() {
//        return instSubType;
//    }
//
//    public void setInstSubType(String instSubType) {
//        this.instSubType = instSubType;
//    }
//
//    public String getEffWeight() {
//        return effWeight;
//    }
//
//    public void setEffWeight(String effWeight) {
//        this.effWeight = effWeight;
//    }
//
//    public String getGirIssuer() {
//        return girIssuer;
//    }
//
//    public void setGirIssuer(String girIssuer) {
//        this.girIssuer = girIssuer;
//    }
//
//    public String getReg28InstrType() {
//        return reg28InstrType;
//    }
//
//    public void setReg28InstrType(String reg28InstrType) {
//        this.reg28InstrType = reg28InstrType;
//    }
//
//    public String getReg30InstrType() {
//        return reg30InstrType;
//    }
//
//    public void setReg30InstrType(String reg30InstrType) {
//        this.reg30InstrType = reg30InstrType;
//    }
//
//    public String getSarbClassification() {
//        return sarbClassification;
//    }
//
//    public void setSarbClassification(String sarbClassification) {
//        this.sarbClassification = sarbClassification;
//    }
//
//    public String getAfricaValues() {
//        return africaValues;
//    }
//
//    public void setAfricaValues(String africaValues) {
//        this.africaValues = africaValues;
//    }
//
//    public String getMarketCapitalization() {
//        return marketCapitalization;
//    }
//
//    public void setMarketCapitalization(String marketCapitalization) {
//        this.marketCapitalization = marketCapitalization;
//    }
//
//    public String getSharesOutstanding() {
//        return sharesOutstanding;
//    }
//
//    public void setSharesOutstanding(String sharesOutstanding) {
//        this.sharesOutstanding = sharesOutstanding;
//    }
//
//    public String getAmountIssued() {
//        return amountIssued;
//    }
//
//    public void setAmountIssued(String amountIssued) {
//        this.amountIssued = amountIssued;
//    }
//
//    public String getIcbIndustry() {
//        return icbIndustry;
//    }
//
//    public void setIcbIndustry(String icbIndustry) {
//        this.icbIndustry = icbIndustry;
//    }
//
//    public String getIcbSuperSector() {
//        return icbSuperSector;
//    }
//
//    public void setIcbSuperSector(String icbSuperSector) {
//        this.icbSuperSector = icbSuperSector;
//    }
//
//    public String getGirIndustryICB() {
//        return girIndustryICB;
//    }
//
//    public void setGirIndustryICB(String girIndustryICB) {
//        this.girIndustryICB = girIndustryICB;
//    }
//
//    public String getGirSectorICB() {
//        return girSectorICB;
//    }
//
//    public void setGirSectorICB(String girSectorICB) {
//        this.girSectorICB = girSectorICB;
//    }
//
//    public String getGirSubsectorICB() {
//        return girSubsectorICB;
//    }
//
//    public void setGirSubsectorICB(String girSubsectorICB) {
//        this.girSubsectorICB = girSubsectorICB;
//    }
//
//    public String getGirSupersectorICB() {
//        return girSupersectorICB;
//    }
//
//    public void setGirSupersectorICB(String girSupersectorICB) {
//        this.girSupersectorICB = girSupersectorICB;
//    }
//
//    public String getSector() {
//        return sector;
//    }
//
//    public void setSector(String sector) {
//        this.sector = sector;
//    }
//
//    public String getSubsector() {
//        return subsector;
//    }
//
//    public void setSubsector(String subsector) {
//        this.subsector = subsector;
//    }
//
//    public String getGicsIndustry() {
//        return gicsIndustry;
//    }
//
//    public void setGicsIndustry(String gicsIndustry) {
//        this.gicsIndustry = gicsIndustry;
//    }
//
//    public String getGicsIndustryGroup() {
//        return gicsIndustryGroup;
//    }
//
//    public void setGicsIndustryGroup(String gicsIndustryGroup) {
//        this.gicsIndustryGroup = gicsIndustryGroup;
//    }
//
//    public String getGicsSector() {
//        return gicsSector;
//    }
//
//    public void setGicsSector(String gicsSector) {
//        this.gicsSector = gicsSector;
//    }
//
//    public String getGicsSubIndustry() {
//        return gicsSubIndustry;
//    }
//
//    public void setGicsSubIndustry(String gicsSubIndustry) {
//        this.gicsSubIndustry = gicsSubIndustry;
//    }
//
//    public String getMaturityDate() {
//        return maturityDate;
//    }
//
//    public void setMaturityDate(String maturityDate) {
//        this.maturityDate = maturityDate;
//    }
//
//    public String getTimeToMaturity() {
//        return timeToMaturity;
//    }
//
//    public void setTimeToMaturity(String timeToMaturity) {
//        this.timeToMaturity = timeToMaturity;
//    }
//
//    public String getWeightedAverageLife() {
//        return weightedAverageLife;
//    }
//
//    public void setWeightedAverageLife(String weightedAverageLife) {
//        this.weightedAverageLife = weightedAverageLife;
//    }
//
//    public String getPricingRedemptionDate() {
//        return pricingRedemptionDate;
//    }
//
//    public void setPricingRedemptionDate(String pricingRedemptionDate) {
//        this.pricingRedemptionDate = pricingRedemptionDate;
//    }
//
//    public String getCurrentYield() {
//        return currentYield;
//    }
//
//    public void setCurrentYield(String currentYield) {
//        this.currentYield = currentYield;
//    }
//
//    public String getCoupon() {
//        return coupon;
//    }
//
//    public void setCoupon(String coupon) {
//        this.coupon = coupon;
//    }
//
//    public String getFirstCouponDate() {
//        return firstCouponDate;
//    }
//
//    public void setFirstCouponDate(String firstCouponDate) {
//        this.firstCouponDate = firstCouponDate;
//    }
//
//    public String getEffectiveDuration() {
//        return effectiveDuration;
//    }
//
//    public void setEffectiveDuration(String effectiveDuration) {
//        this.effectiveDuration = effectiveDuration;
//    }
//
//    public String getMacaulayDuration() {
//        return macaulayDuration;
//    }
//
//    public void setMacaulayDuration(String macaulayDuration) {
//        this.macaulayDuration = macaulayDuration;
//    }
//
//    public String getModifiedDuration() {
//        return modifiedDuration;
//    }
//
//    public void setModifiedDuration(String modifiedDuration) {
//        this.modifiedDuration = modifiedDuration;
//    }
//
//    public String getPriceCurrency() {
//        return priceCurrency;
//    }
//
//    public void setPriceCurrency(String priceCurrency) {
//        this.priceCurrency = priceCurrency;
//    }
//
//    public String getLocalMarket() {
//        return localMarket;
//    }
//
//    public void setLocalMarket(String localMarket) {
//        this.localMarket = localMarket;
//    }
//
//    public String getCountryOfExposure() {
//        return countryOfExposure;
//    }
//
//    public void setCountryOfExposure(String countryOfExposure) {
//        this.countryOfExposure = countryOfExposure;
//    }
//
//    public String getCountryOfIncorporation() {
//        return countryOfIncorporation;
//    }
//
//    public void setCountryOfIncorporation(String countryOfIncorporation) {
//        this.countryOfIncorporation = countryOfIncorporation;
//    }
//
//    public String getCountryOfQuotation() {
//        return countryOfQuotation;
//    }
//
//    public void setCountryOfQuotation(String countryOfQuotation) {
//        this.countryOfQuotation = countryOfQuotation;
//    }
//
//    public String getIssuer() {
//        return issuer;
//    }
//
//    public void setIssuer(String issuer) {
//        this.issuer = issuer;
//    }
//
//    public String getMoodyRating() {
//        return moodyRating;
//    }
//
//    public void setMoodyRating(String moodyRating) {
//        this.moodyRating = moodyRating;
//    }
//
//    public String getMoodyLongTermIssuerRatingD() {
//        return moodyLongTermIssuerRatingD;
//    }
//
//    public void setMoodyLongTermIssuerRatingD(String moodyLongTermIssuerRatingD) {
//        this.moodyLongTermIssuerRatingD = moodyLongTermIssuerRatingD;
//    }
//
//    public String getMoodyLongTermIssuerRatingF() {
//        return moodyLongTermIssuerRatingF;
//    }
//
//    public void setMoodyLongTermIssuerRatingF(String moodyLongTermIssuerRatingF) {
//        this.moodyLongTermIssuerRatingF = moodyLongTermIssuerRatingF;
//    }
//
//    public String getsAndPLongTermIssuerRatingD() {
//        return sAndPLongTermIssuerRatingD;
//    }
//
//    public void setsAndPLongTermIssuerRatingD(String sAndPLongTermIssuerRatingD) {
//        this.sAndPLongTermIssuerRatingD = sAndPLongTermIssuerRatingD;
//    }
//
//    public String getsAndPLongTermIssuerRatingF() {
//        return sAndPLongTermIssuerRatingF;
//    }
//
//    public void setsAndPLongTermIssuerRatingF(String sAndPLongTermIssuerRatingF) {
//        this.sAndPLongTermIssuerRatingF = sAndPLongTermIssuerRatingF;
//    }
//
//    public String getParentIssuerID() {
//        return parentIssuerID;
//    }
//
//    public void setParentIssuerID(String parentIssuerID) {
//        this.parentIssuerID = parentIssuerID;
//    }
//
//    public String getParentIssuerName() {
//        return parentIssuerName;
//    }
//
//    public void setParentIssuerName(String parentIssuerName) {
//        this.parentIssuerName = parentIssuerName;
//    }
//
//    public String getUltimateIssuerID() {
//        return ultimateIssuerID;
//    }
//
//    public void setUltimateIssuerID(String ultimateIssuerID) {
//        this.ultimateIssuerID = ultimateIssuerID;
//    }
//
//    public String getUltimateIssuerName() {
//        return ultimateIssuerName;
//    }
//
//    public void setUltimateIssuerName(String ultimateIssuerName) {
//        this.ultimateIssuerName = ultimateIssuerName;
//    }
//}

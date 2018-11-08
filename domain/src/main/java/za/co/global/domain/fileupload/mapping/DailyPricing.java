package za.co.global.domain.fileupload.mapping;

import com.gizbel.excel.annotations.ExcelBean;
import com.gizbel.excel.annotations.ExcelColumnHeader;
import za.co.global.domain.excel.GIRSAExCelColumnHeader;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "daily_pricing")
@ExcelBean
public class DailyPricing implements Serializable {

    private static final long serialVersionUID = 3532447784821187632L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "issuer", unique = true, nullable = false)
    @ExcelColumnHeader(columnHeader = "Issuer")
    private String issuer;

    @Column(name = "bond_code")
    @ExcelColumnHeader(columnHeader = "Bond code")
    private String bondCode;

    @Column(name = "type")
    @ExcelColumnHeader(columnHeader = "Type")
    private String type;

    @Column(name = "features")
    @ExcelColumnHeader(columnHeader = "Features")
    private String features;

    @Column(name = "moodys_rating")
    @ExcelColumnHeader(columnHeader = "Moodys Rating")
    private String moodys;

    @Column(name = "fitch_rating")
    @ExcelColumnHeader(columnHeader = "Fitch Rating")
    private String fitch;

    @Column(name = "standard_and_poor_rating")
    @ExcelColumnHeader(columnHeader = "Standard And Poor's Rating")
    private String standardAndPoor;

    @Column(name = "global_rating")
    @ExcelColumnHeader(columnHeader = "Global Rating")
    private String global;

    @Column(name = "summary_rating")
    @ExcelColumnHeader(columnHeader = "Summary rating")
    private String summaryRating;

    @Column(name = "issue_size")
    @ExcelColumnHeader(columnHeader = "Issue size (Rbn)", dataType = "big_decimal")
    private BigDecimal issueSize;

    @Column(name = "coupon")
    @ExcelColumnHeader(columnHeader = "Coupon (%)", dataType = "big_decimal")
    private BigDecimal coupon;

    @Column(name = "issue_date")
    @ExcelColumnHeader(columnHeader = "Issue date", dataType = "date")
    private Date issueDate;

    @Column(name = "maturity_or_call_date")
    @ExcelColumnHeader(columnHeader = "Maturity/ call date", dataType = "date")
    private Date maturityOrCallDate;

    @Column(name = "govi_benchmark")
    @ExcelColumnHeader(columnHeader = "Govi benchmark")
    private String goviBenchmark;

    @Column(name = "spread_at_issue")
    @ExcelColumnHeader(columnHeader = "Spread at issue")
    private String spreadAtIssue;

    @Column(name = "spread_to_govi")
    @GIRSAExCelColumnHeader(columnHeader = "Spread To Govi", isContain = true)
    private String spreadToGovi;

    @Column(name = "spread_to_govi_one_day_change")
    @ExcelColumnHeader(columnHeader = "Spread To Govi 1-day change")
    private String spreadToGovi1DayChange;

    @Column(name = "spread_to_jibar_or_asw")
    @GIRSAExCelColumnHeader(columnHeader = "Spread to Jibar/ASW", isContain = true)
    private String spreadToJibarOrASW;

    @Column(name = "spread_to_jibar_or_asw_one_day_change")
    @ExcelColumnHeader(columnHeader = "Spread to Jibar/ASW 1-day change")
    private String spreadToJibarOrASW1DayChange;

    @Column(name = "current_yield")
    @ExcelColumnHeader(columnHeader = "Current yield (%)", dataType = "big_decimal")
    private BigDecimal currentYield;

    @Column(name = "liquidity_no_of_trades")
    @ExcelColumnHeader(columnHeader = "Liquidity No. of trades")
    private String liquidityNoOfTrade;

    @Column(name = "liquidity_nominal_traded")
    @ExcelColumnHeader(columnHeader = "Liquidity Nominal traded (Rm)")
    private String liquidityNominalTraded;

    @Column(name = "sbr_fair_value")
    @ExcelColumnHeader(columnHeader = "SBR's Fair value spread to Jibar", dataType = "big_decimal")
    private BigDecimal sbrFairValue;

    @Column(name = "last_traded_date")
    @ExcelColumnHeader(columnHeader = "Last traded date", dataType = "date")
    private Date lastTradedDate;

    @Column(name = "last_mtm_change_date")
    @ExcelColumnHeader(columnHeader = "Last MTM Change Date", dataType = "date")
    private Date lastMTMChangeDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getBondCode() {
        return bondCode;
    }

    public void setBondCode(String bondCode) {
        this.bondCode = bondCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getMoodys() {
        return moodys;
    }

    public void setMoodys(String moodys) {
        this.moodys = moodys;
    }

    public String getFitch() {
        return fitch;
    }

    public void setFitch(String fitch) {
        this.fitch = fitch;
    }

    public String getGlobal() {
        return global;
    }

    public void setGlobal(String global) {
        this.global = global;
    }

    public String getSummaryRating() {
        return summaryRating;
    }

    public void setSummaryRating(String summaryRating) {
        this.summaryRating = summaryRating;
    }

    public BigDecimal getIssueSize() {
        return issueSize;
    }

    public void setIssueSize(BigDecimal issueSize) {
        this.issueSize = issueSize;
    }

    public BigDecimal getCoupon() {
        return coupon;
    }

    public void setCoupon(BigDecimal coupon) {
        this.coupon = coupon;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getMaturityOrCallDate() {
        return maturityOrCallDate;
    }

    public void setMaturityOrCallDate(Date maturityOrCallDate) {
        this.maturityOrCallDate = maturityOrCallDate;
    }

    public String getGoviBenchmark() {
        return goviBenchmark;
    }

    public void setGoviBenchmark(String goviBenchmark) {
        this.goviBenchmark = goviBenchmark;
    }

    public String getSpreadAtIssue() {
        return spreadAtIssue;
    }

    public void setSpreadAtIssue(String spreadAtIssue) {
        this.spreadAtIssue = spreadAtIssue;
    }

    public String getLiquidityNominalTraded() {
        return liquidityNominalTraded;
    }

    public void setLiquidityNominalTraded(String liquidityNominalTraded) {
        this.liquidityNominalTraded = liquidityNominalTraded;
    }

    public String getStandardAndPoor() {
        return standardAndPoor;
    }

    public void setStandardAndPoor(String standardAndPoor) {
        this.standardAndPoor = standardAndPoor;
    }

    public String getSpreadToGovi() {
        return spreadToGovi;
    }

    public void setSpreadToGovi(String spreadToGovi) {
        this.spreadToGovi = spreadToGovi;
    }

    public String getSpreadToJibarOrASW() {
        return spreadToJibarOrASW;
    }

    public void setSpreadToJibarOrASW(String spreadToJibarOrASW) {
        this.spreadToJibarOrASW = spreadToJibarOrASW;
    }

    public Date getLastTradedDate() {
        return lastTradedDate;
    }

    public void setLastTradedDate(Date lastTradedDate) {
        this.lastTradedDate = lastTradedDate;
    }

    public String getLiquidityNoOfTrade() {
        return liquidityNoOfTrade;
    }

    public void setLiquidityNoOfTrade(String liquidityNoOfTrade) {
        this.liquidityNoOfTrade = liquidityNoOfTrade;
    }

    public Date getLastMTMChangeDate() {
        return lastMTMChangeDate;
    }

    public void setLastMTMChangeDate(Date lastMTMChangeDate) {
        this.lastMTMChangeDate = lastMTMChangeDate;
    }

    public BigDecimal getCurrentYield() {
        return currentYield;
    }

    public void setCurrentYield(BigDecimal currentYield) {
        this.currentYield = currentYield;
    }

    public BigDecimal getSbrFairValue() {
        return sbrFairValue;
    }

    public void setSbrFairValue(BigDecimal sbrFairValue) {
        this.sbrFairValue = sbrFairValue;
    }

    public String getSpreadToGovi1DayChange() {
        return spreadToGovi1DayChange;
    }

    public void setSpreadToGovi1DayChange(String spreadToGovi1DayChange) {
        this.spreadToGovi1DayChange = spreadToGovi1DayChange;
    }

    public String getSpreadToJibarOrASW1DayChange() {
        return spreadToJibarOrASW1DayChange;
    }

    public void setSpreadToJibarOrASW1DayChange(String spreadToJibarOrASW1DayChange) {
        this.spreadToJibarOrASW1DayChange = spreadToJibarOrASW1DayChange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyPricing that = (DailyPricing) o;
        return Objects.equals(issuer, that.issuer) &&
                Objects.equals(bondCode, that.bondCode) &&
                Objects.equals(type, that.type) &&
                Objects.equals(features, that.features) &&
                Objects.equals(moodys, that.moodys) &&
                Objects.equals(fitch, that.fitch) &&
                Objects.equals(standardAndPoor, that.standardAndPoor) &&
                Objects.equals(global, that.global) &&
                Objects.equals(summaryRating, that.summaryRating) &&
                Objects.equals(issueSize, that.issueSize) &&
                Objects.equals(coupon, that.coupon) &&
                Objects.equals(issueDate, that.issueDate) &&
                Objects.equals(maturityOrCallDate, that.maturityOrCallDate) &&
                Objects.equals(goviBenchmark, that.goviBenchmark) &&
                Objects.equals(spreadAtIssue, that.spreadAtIssue) &&
                Objects.equals(currentYield, that.currentYield) &&
                Objects.equals(liquidityNoOfTrade, that.liquidityNoOfTrade) &&
                Objects.equals(liquidityNominalTraded, that.liquidityNominalTraded) &&
                Objects.equals(sbrFairValue, that.sbrFairValue) &&
                Objects.equals(lastTradedDate, that.lastTradedDate) &&
                Objects.equals(lastMTMChangeDate, that.lastMTMChangeDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(issuer, bondCode, type, features, moodys, fitch, standardAndPoor, global, summaryRating, issueSize, coupon, issueDate, maturityOrCallDate, goviBenchmark, spreadAtIssue,
                currentYield, liquidityNoOfTrade, liquidityNominalTraded, sbrFairValue, lastTradedDate, lastMTMChangeDate);
    }
}

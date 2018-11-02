package za.co.global.domain.fileupload.mapping;

import com.gizbel.excel.annotations.ExcelBean;
import com.gizbel.excel.annotations.ExcelColumnHeader;

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

    @Column(name = "rating_moodys")
    @ExcelColumnHeader(columnHeader = "Moodys Rating")
    private String moodys;

    @Column(name = "rating_fitch")
    @ExcelColumnHeader(columnHeader = "Fitch Rating")
    private String fitch;

    @Column(name = "rating_s_and_p")
    @ExcelColumnHeader(columnHeader = "SAndP Rating")
    private String sAndP;

    @Column(name = "rating_global")
    @ExcelColumnHeader(columnHeader = "Global Rating")
    private String global;

    @Column(name = "summary_rating")
    @ExcelColumnHeader(columnHeader = "Summary rating")
    private String summaryRating;

    @Column(name = "issue_size")
    @ExcelColumnHeader(columnHeader = "Issue size (Rbn)")
    private String issueSize;

    @Column(name = "coupon")
    @ExcelColumnHeader(columnHeader = "Coupon (%)")
    private BigDecimal coupon;

    @Column(name = "issue_date")
    @ExcelColumnHeader(columnHeader = "Issue date")
    private Date issueDate;

    @Column(name = "maturity_or_call_date")
    @ExcelColumnHeader(columnHeader = "Maturity/ call date")
    private Date maturityOrCallDate;

    @Column(name = "govi_benchmark")
    @ExcelColumnHeader(columnHeader = "Govi benchmark")
    private String goviBenchmark;

    @Column(name = "spread_at_issue")
    @ExcelColumnHeader(columnHeader = "Spread at issue")
    private String spreadAtIssue;

    @Column(name = "spread_to_govi_current_day")
    @ExcelColumnHeader(columnHeader = "Spread To Govi Current Day")
    private String spreadToGoviCurrentDay;

    @Column(name = "spread_to_govi_next_day")
    @ExcelColumnHeader(columnHeader = "Spread To Govi Next Day")
    private String spreadToGoviNextDay;

    @Column(name = "spread_to_govi_one_day_change")
    @ExcelColumnHeader(columnHeader = "Spread To Govi 1-day change")
    private String spreadToGoviOneDayChange;

    @Column(name = "spread_to_jibar_or_asw_current_day")
    @ExcelColumnHeader(columnHeader = "Spread to Jibar/ASW Current Day")
    private String spreadToJibarOrASWCurrentDay;

    @Column(name = "spread_to_jibar_or_asw_next_day")
    @ExcelColumnHeader(columnHeader = "Spread to Jibar/ASW Next Day")
    private String spreadToJibarOrASWNextDay;

    @Column(name = "spread_to_jibar_or_asw_one_day_change")
    @ExcelColumnHeader(columnHeader = "Spread to Jibar/ASW 1-day change")
    private String spreadToJibarOrASWOneDayChange;

    @Column(name = "current_yield")
    @ExcelColumnHeader(columnHeader = "Current yield (%)")
    private BigDecimal currentYield;

    @Column(name = "liquidity_no_of_trades")
    @ExcelColumnHeader(columnHeader = "Liquidity No. of trades")
    private String liquidityNoOfTrade;

    @Column(name = "liquidity_nominal_traded")
    @ExcelColumnHeader(columnHeader = "Liquidity Nominal traded (Rm)")
    private String liquidityNominalTraded;

    @Column(name = "sbr_fair_value")
    @ExcelColumnHeader(columnHeader = "SBR's Fair value spread to Jibar")
    private BigDecimal sbrFairValue;

    @Column(name = "last_traded_date")
    @ExcelColumnHeader(columnHeader = "Last traded date")
    private Date lastTradedDate;

    @Column(name = "last_mtm_change_date")
    @ExcelColumnHeader(columnHeader = "Last MTM Change Date")
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

    public String getsAndP() {
        return sAndP;
    }

    public void setsAndP(String sAndP) {
        this.sAndP = sAndP;
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

    public String getIssueSize() {
        return issueSize;
    }

    public void setIssueSize(String issueSize) {
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

    public String getSpreadToGoviCurrentDay() {
        return spreadToGoviCurrentDay;
    }

    public void setSpreadToGoviCurrentDay(String spreadToGoviCurrentDay) {
        this.spreadToGoviCurrentDay = spreadToGoviCurrentDay;
    }

    public String getSpreadToGoviNextDay() {
        return spreadToGoviNextDay;
    }

    public void setSpreadToGoviNextDay(String spreadToGoviNextDay) {
        this.spreadToGoviNextDay = spreadToGoviNextDay;
    }

    public String getSpreadToGoviOneDayChange() {
        return spreadToGoviOneDayChange;
    }

    public void setSpreadToGoviOneDayChange(String spreadToGoviOneDayChange) {
        this.spreadToGoviOneDayChange = spreadToGoviOneDayChange;
    }

    public String getSpreadToJibarOrASWCurrentDay() {
        return spreadToJibarOrASWCurrentDay;
    }

    public void setSpreadToJibarOrASWCurrentDay(String spreadToJibarOrASWCurrentDay) {
        this.spreadToJibarOrASWCurrentDay = spreadToJibarOrASWCurrentDay;
    }

    public String getSpreadToJibarOrASWNextDay() {
        return spreadToJibarOrASWNextDay;
    }

    public void setSpreadToJibarOrASWNextDay(String spreadToJibarOrASWNextDay) {
        this.spreadToJibarOrASWNextDay = spreadToJibarOrASWNextDay;
    }

    public String getSpreadToJibarOrASWOneDayChange() {
        return spreadToJibarOrASWOneDayChange;
    }

    public void setSpreadToJibarOrASWOneDayChange(String spreadToJibarOrASWOneDayChange) {
        this.spreadToJibarOrASWOneDayChange = spreadToJibarOrASWOneDayChange;
    }

    public String getLiquidityNominalTraded() {
        return liquidityNominalTraded;
    }

    public void setLiquidityNominalTraded(String liquidityNominalTraded) {
        this.liquidityNominalTraded = liquidityNominalTraded;
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
                Objects.equals(sAndP, that.sAndP) &&
                Objects.equals(global, that.global) &&
                Objects.equals(summaryRating, that.summaryRating) &&
                Objects.equals(issueSize, that.issueSize) &&
                Objects.equals(coupon, that.coupon) &&
                Objects.equals(issueDate, that.issueDate) &&
                Objects.equals(maturityOrCallDate, that.maturityOrCallDate) &&
                Objects.equals(goviBenchmark, that.goviBenchmark) &&
                Objects.equals(spreadAtIssue, that.spreadAtIssue) &&
                Objects.equals(spreadToGoviCurrentDay, that.spreadToGoviCurrentDay) &&
                Objects.equals(spreadToGoviNextDay, that.spreadToGoviNextDay) &&
                Objects.equals(spreadToGoviOneDayChange, that.spreadToGoviOneDayChange) &&
                Objects.equals(spreadToJibarOrASWCurrentDay, that.spreadToJibarOrASWCurrentDay) &&
                Objects.equals(spreadToJibarOrASWNextDay, that.spreadToJibarOrASWNextDay) &&
                Objects.equals(spreadToJibarOrASWOneDayChange, that.spreadToJibarOrASWOneDayChange) &&
                Objects.equals(currentYield, that.currentYield) &&
                Objects.equals(liquidityNoOfTrade, that.liquidityNoOfTrade) &&
                Objects.equals(liquidityNominalTraded, that.liquidityNominalTraded) &&
                Objects.equals(sbrFairValue, that.sbrFairValue) &&
                Objects.equals(lastTradedDate, that.lastTradedDate) &&
                Objects.equals(lastMTMChangeDate, that.lastMTMChangeDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(issuer, bondCode, type, features, moodys, fitch, sAndP, global, summaryRating, issueSize, coupon, issueDate, maturityOrCallDate, goviBenchmark, spreadAtIssue, spreadToGoviCurrentDay, spreadToGoviNextDay, spreadToGoviOneDayChange, spreadToJibarOrASWCurrentDay, spreadToJibarOrASWNextDay, spreadToJibarOrASWOneDayChange, currentYield, liquidityNoOfTrade, liquidityNominalTraded, sbrFairValue, lastTradedDate, lastMTMChangeDate);
    }
}

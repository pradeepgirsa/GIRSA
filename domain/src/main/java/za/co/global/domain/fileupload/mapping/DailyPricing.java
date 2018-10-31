package za.co.global.domain.fileupload.mapping;

import com.gizbel.excel.annotations.ExcelBean;
import com.gizbel.excel.annotations.ExcelColumnHeader;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
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

    @Column(name = "moodys")
    @ExcelColumnHeader(columnHeader = "Moodys")
    private String moodys;

    @Column(name = "fitch")
    @ExcelColumnHeader(columnHeader = "Fitch")
    private String fitch;

    @Column(name = "s_and_p")
    @ExcelColumnHeader(columnHeader = "SAndP")
    private String sAndP;

    @Column(name = "global")
    @ExcelColumnHeader(columnHeader = "Global")
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
    private String issueDate;

    @Column(name = "maturity_or_call_date")
    @ExcelColumnHeader(columnHeader = "Maturity/ call date")
    private String maturityOrCallDate;

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

    @Column(name = "liquidity_no_of_trades")
    @ExcelColumnHeader(columnHeader = "Liquidity No. of trades")
    private String liquidityNoOfTrades;

    @Column(name = "liquidity_nominal_traded")
    @ExcelColumnHeader(columnHeader = "Liquidity Nominal traded (Rm)")
    private String liquidityNominalTraded;

    @Column(name = "last_traded_date")
    @ExcelColumnHeader(columnHeader = "Last traded date")
    private String lastTradedDate;

    @Column(name = "liquidity_no_of_trades")
    @ExcelColumnHeader(columnHeader = "Liquidity No. of trades")
    private String liquidityNoOfTrade;

    @Column(name = "last_mtm_change_date")
    @ExcelColumnHeader(columnHeader = "Last MTM Change Date")
    private String lastMTMChangeDate;

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

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getMaturityOrCallDate() {
        return maturityOrCallDate;
    }

    public void setMaturityOrCallDate(String maturityOrCallDate) {
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

    public String getLiquidityNoOfTrades() {
        return liquidityNoOfTrades;
    }

    public void setLiquidityNoOfTrades(String liquidityNoOfTrades) {
        this.liquidityNoOfTrades = liquidityNoOfTrades;
    }

    public String getLiquidityNominalTraded() {
        return liquidityNominalTraded;
    }

    public void setLiquidityNominalTraded(String liquidityNominalTraded) {
        this.liquidityNominalTraded = liquidityNominalTraded;
    }

    public String getLastTradedDate() {
        return lastTradedDate;
    }

    public void setLastTradedDate(String lastTradedDate) {
        this.lastTradedDate = lastTradedDate;
    }

    public String getLiquidityNoOfTrade() {
        return liquidityNoOfTrade;
    }

    public void setLiquidityNoOfTrade(String liquidityNoOfTrade) {
        this.liquidityNoOfTrade = liquidityNoOfTrade;
    }

    public String getLastMTMChangeDate() {
        return lastMTMChangeDate;
    }

    public void setLastMTMChangeDate(String lastMTMChangeDate) {
        this.lastMTMChangeDate = lastMTMChangeDate;
    }
}

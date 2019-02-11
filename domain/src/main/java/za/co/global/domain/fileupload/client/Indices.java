package za.co.global.domain.fileupload.client;

import com.gizbel.excel.annotations.ExcelBean;
import com.gizbel.excel.annotations.ExcelColumnHeader;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "indices")
@ExcelBean
public class Indices implements Serializable {

    private static final long serialVersionUID = 3410446784521187632L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "security", unique = true, nullable = false)
    @ExcelColumnHeader(columnHeader = "Security")
    private String security;

    @Column(name = "description")
    @ExcelColumnHeader(columnHeader = "Description")
    private String description;

    @Column(name = "exch")
    @ExcelColumnHeader(columnHeader = "Exch")
    private String exch;

    @Column(name = "r")
    @ExcelColumnHeader(columnHeader = "R")
    private String r;

    @Column(name = "market_cap")
    @ExcelColumnHeader(columnHeader = "MktCap", dataType = "big_decimal")
    private BigDecimal marketCap;

    @Column(name = "market_cap_live")
    @ExcelColumnHeader(columnHeader = "MktCapLive", dataType = "big_decimal")
    private BigDecimal marketCapLive;

    @Column(name = "index_percentage")
    @ExcelColumnHeader(columnHeader = "Index%", dataType = "big_decimal")
    private BigDecimal indexPercentage;

    @Column(name = "index_price")
    @ExcelColumnHeader(columnHeader = "IndexPrice", dataType = "big_decimal")
    private BigDecimal indexPrice;

    @Column(name = "index_points")
    @ExcelColumnHeader(columnHeader = "IndexPts", dataType = "big_decimal")
    private BigDecimal indexPoints;

    @Column(name = "yld_hist")
    @ExcelColumnHeader(columnHeader = "YldHist%", dataType = "big_decimal")
    private BigDecimal yldHist;

    @Column(name = "iwf")
    @ExcelColumnHeader(columnHeader = "IWF", dataType = "big_decimal")
    private BigDecimal iwf;

    @Column(name = "issue")
    @ExcelColumnHeader(columnHeader = "#Issue", dataType = "big_decimal")
    private BigDecimal issue;

    @Column(name = "bid")
    @ExcelColumnHeader(columnHeader = "Bid", dataType = "big_decimal")
    private BigDecimal bid;

    @Column(name = "ask")
    @ExcelColumnHeader(columnHeader = "Ask", dataType = "big_decimal")
    private BigDecimal ask;

    @Column(name = "last")
    @ExcelColumnHeader(columnHeader = "Last", dataType = "big_decimal")
    private BigDecimal last;

    @Column(name = "positive_or_negative")
    @ExcelColumnHeader(columnHeader = "+/-", dataType = "big_decimal")
    private BigDecimal positiveOrNegative;

    @Column(name = "sub_industry")
    @ExcelColumnHeader(columnHeader = "SubIndustry")
    private String subIndustry;

    @Column(name = "gics_code")
    @ExcelColumnHeader(columnHeader = "GICSCode")
    private String gicsCode;

    @Column(name = "pe_ratio")
    @ExcelColumnHeader(columnHeader = "PERatio", dataType = "big_decimal")
    private BigDecimal peRatio;

    @Column(name = "type")
    private String type; //TODO - refactor to specific name

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExch() {
        return exch;
    }

    public void setExch(String exch) {
        this.exch = exch;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public BigDecimal getIndexPoints() {
        return indexPoints;
    }

    public void setIndexPoints(BigDecimal indexPoints) {
        this.indexPoints = indexPoints;
    }

    public BigDecimal getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(BigDecimal marketCap) {
        this.marketCap = marketCap;
    }

    public BigDecimal getMarketCapLive() {
        return marketCapLive;
    }

    public void setMarketCapLive(BigDecimal marketCapLive) {
        this.marketCapLive = marketCapLive;
    }

    public BigDecimal getIndexPercentage() {
        return indexPercentage;
    }

    public void setIndexPercentage(BigDecimal indexPercentage) {
        this.indexPercentage = indexPercentage;
    }

    public BigDecimal getIndexPrice() {
        return indexPrice;
    }

    public void setIndexPrice(BigDecimal indexPrice) {
        this.indexPrice = indexPrice;
    }

    public BigDecimal getYldHist() {
        return yldHist;
    }

    public void setYldHist(BigDecimal yldHist) {
        this.yldHist = yldHist;
    }

    public BigDecimal getIwf() {
        return iwf;
    }

    public void setIwf(BigDecimal iwf) {
        this.iwf = iwf;
    }

    public void setPeRatio(BigDecimal peRatio) {
        this.peRatio = peRatio;
    }

    public BigDecimal getPeRatio() {
        return peRatio;
    }

    public BigDecimal getIssue() {
        return issue;
    }

    public void setIssue(BigDecimal issue) {
        this.issue = issue;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public void setAsk(BigDecimal ask) {
        this.ask = ask;
    }

    public BigDecimal getLast() {
        return last;
    }

    public void setLast(BigDecimal last) {
        this.last = last;
    }

    public BigDecimal getPositiveOrNegative() {
        return positiveOrNegative;
    }

    public void setPositiveOrNegative(BigDecimal positiveOrNegative) {
        this.positiveOrNegative = positiveOrNegative;
    }

    public String getSubIndustry() {
        return subIndustry;
    }

    public void setSubIndustry(String subIndustry) {
        this.subIndustry = subIndustry;
    }

    public String getGicsCode() {
        return gicsCode;
    }

    public void setGicsCode(String gicsCode) {
        this.gicsCode = gicsCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Indices indices = (Indices) o;
        return Objects.equals(id, indices.id) &&
                Objects.equals(security, indices.security) &&
                Objects.equals(description, indices.description) &&
                Objects.equals(exch, indices.exch) &&
                Objects.equals(r, indices.r) &&
                Objects.equals(marketCap, indices.marketCap) &&
                Objects.equals(marketCapLive, indices.marketCapLive) &&
                Objects.equals(indexPercentage, indices.indexPercentage) &&
                Objects.equals(indexPrice, indices.indexPrice) &&
                Objects.equals(indexPoints, indices.indexPoints) &&
                Objects.equals(yldHist, indices.yldHist) &&
                Objects.equals(iwf, indices.iwf) &&
                Objects.equals(issue, indices.issue) &&
                Objects.equals(bid, indices.bid) &&
                Objects.equals(ask, indices.ask) &&
                Objects.equals(last, indices.last) &&
                Objects.equals(positiveOrNegative, indices.positiveOrNegative) &&
                Objects.equals(subIndustry, indices.subIndustry) &&
                Objects.equals(gicsCode, indices.gicsCode) &&
                Objects.equals(peRatio, indices.peRatio);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, security, description, exch, r, marketCap, marketCapLive, indexPercentage, indexPrice, indexPoints, yldHist, iwf, issue, bid, ask, last, positiveOrNegative, subIndustry, gicsCode, peRatio);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

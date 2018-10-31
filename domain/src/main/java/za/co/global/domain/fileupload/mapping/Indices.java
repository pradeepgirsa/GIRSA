package za.co.global.domain.fileupload.mapping;

import com.gizbel.excel.annotations.ExcelBean;
import com.gizbel.excel.annotations.ExcelColumnHeader;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "reg28_instr_type")
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
    @ExcelColumnHeader(columnHeader = "MktCap")
    private BigDecimal marketCap;

    @Column(name = "market_cap_live")
    @ExcelColumnHeader(columnHeader = "MktCapLive")
    private BigDecimal marketCapLive;

    @Column(name = "index")
    @ExcelColumnHeader(columnHeader = "Index%")
    private BigDecimal index;

    @Column(name = "index_price")
    @ExcelColumnHeader(columnHeader = "IndexPrice")
    private BigDecimal indexPrice;

    @Column(name = "index_points")
    @ExcelColumnHeader(columnHeader = "IndexPts")
    private String indexPoints;

    @Column(name = "yld_hist")
    @ExcelColumnHeader(columnHeader = "YldHist%")
    private BigDecimal yldHist;

    @Column(name = "iwf")
    @ExcelColumnHeader(columnHeader = "IWF")
    private BigDecimal iwf;

    @Column(name = "issue")
    @ExcelColumnHeader(columnHeader = "#Issue")
    private String issue;

    @Column(name = "bid")
    @ExcelColumnHeader(columnHeader = "Bid")
    private String bid;

    @Column(name = "ask")
    @ExcelColumnHeader(columnHeader = "Ask")
    private String ask;

    @Column(name = "last")
    @ExcelColumnHeader(columnHeader = "Last")
    private String last;

    @Column(name = "positive_or_negative")
    @ExcelColumnHeader(columnHeader = "+/-")
    private String positiveOrNegative;

    @Column(name = "sub_industry")
    @ExcelColumnHeader(columnHeader = "SubIndustry")
    private String subIndustry;

    @Column(name = "gics_code")
    @ExcelColumnHeader(columnHeader = "GICSCode")
    private String gicsCode;

    @Column(name = "pe_ratio")
    @ExcelColumnHeader(columnHeader = "PERatio")
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

    public String getIndexPoints() {
        return indexPoints;
    }

    public void setIndexPoints(String indexPoints) {
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

    public BigDecimal getIndex() {
        return index;
    }

    public void setIndex(BigDecimal index) {
        this.index = index;
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

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getPositiveOrNegative() {
        return positiveOrNegative;
    }

    public void setPositiveOrNegative(String positiveOrNegative) {
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
                Objects.equals(index, indices.index) &&
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

        return Objects.hash(id, security, description, exch, r, marketCap, marketCapLive, index, indexPrice, indexPoints, yldHist, iwf, issue, bid, ask, last, positiveOrNegative, subIndustry, gicsCode, peRatio);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

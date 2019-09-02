package za.co.global.domain.fileupload.client;

import com.gizbel.excel.annotations.ExcelBean;
import com.gizbel.excel.annotations.ExcelColumnHeader;
import za.co.global.domain.client.Client;
import za.co.global.domain.report.ReportData;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "instrument_data")
@ExcelBean
public class InstrumentData implements Serializable {

    private static final long serialVersionUID = 5521346841911277832L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "portfolio_code", nullable = false)
    @ExcelColumnHeader(columnHeader = "Portfolio Code")
    private String portfolioCode;

    @Column(name = "portfolio_name", nullable = false)
    @ExcelColumnHeader(columnHeader = "Portfolio Name")
    private String portfolioName;

    @Column(name = "market_value_total", nullable = false)
    @ExcelColumnHeader(columnHeader = "MV Total", dataType = "big_decimal")
    private BigDecimal marketValueTotal;

    @Column(name = "institutional_total")
    @ExcelColumnHeader(columnHeader = "Institutional Total", dataType = "big_decimal")
    private BigDecimal institutionTotal;

    @Column(name = "no_of_accounts")
    @ExcelColumnHeader(columnHeader = "Number Of Accounts", dataType = "big_decimal")
    private BigDecimal noOfAccounts;

    @Column(name = "instrument_code", nullable = false)
    @ExcelColumnHeader(columnHeader = "Instrument Code")
    private String instrumentCode;

    @Column(name = "instrument_description", nullable = false)
    @ExcelColumnHeader(columnHeader = "Instrument Description")
    private String instrumentDescription;

    @Column(name = "nominal_units", nullable = false)
    @ExcelColumnHeader(columnHeader = "Nominal", dataType = "big_decimal")
    private BigDecimal nominalUnits;

    @Column(name = "current_book_value", nullable = false)
    @ExcelColumnHeader(columnHeader = "Book Value Base Current", dataType = "big_decimal")
    private BigDecimal currentBookValue;

    @Column(name = "instrument_currency", nullable = false)
    @ExcelColumnHeader(columnHeader = "Issue Currency")
    private String instrumentCurrency;

    @Column(name = "current_market_value", nullable = false)
    @ExcelColumnHeader(columnHeader = "Current Market Value (Base)", dataType = "big_decimal")
    private BigDecimal currentMarketValue;

    @Column(name = "trade_date")
    @ExcelColumnHeader(columnHeader = "Trade Date", dataType = "date")
    private Date tradeDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "report_data_id", insertable = false, updatable = false)
//    private ReportData reportData;

    @Column(name = "updated_date")
    private Date updatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPortfolioCode() {
        return portfolioCode;
    }

    public void setPortfolioCode(String portfolioCode) {
        this.portfolioCode = portfolioCode;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public BigDecimal getMarketValueTotal() {
        return marketValueTotal;
    }

    public void setMarketValueTotal(BigDecimal marketValueTotal) {
        this.marketValueTotal = marketValueTotal;
    }

    public BigDecimal getInstitutionTotal() {
        return institutionTotal;
    }

    public void setInstitutionTotal(BigDecimal institutionTotal) {
        this.institutionTotal = institutionTotal;
    }

    public BigDecimal getNoOfAccounts() {
        return noOfAccounts;
    }

    public void setNoOfAccounts(BigDecimal noOfAccounts) {
        this.noOfAccounts = noOfAccounts;
    }

    public String getInstrumentCode() {
        return instrumentCode;
    }

    public void setInstrumentCode(String instrumentCode) {
        this.instrumentCode = instrumentCode;
    }

    public String getInstrumentDescription() {
        return instrumentDescription;
    }

    public void setInstrumentDescription(String instrumentDescription) {
        this.instrumentDescription = instrumentDescription;
    }

    public BigDecimal getNominalUnits() {
        return nominalUnits;
    }

    public void setNominalUnits(BigDecimal nominalUnits) {
        this.nominalUnits = nominalUnits;
    }

    public BigDecimal getCurrentBookValue() {
        return currentBookValue;
    }

    public void setCurrentBookValue(BigDecimal currentBookValue) {
        this.currentBookValue = currentBookValue;
    }

    public String getInstrumentCurrency() {
        return instrumentCurrency;
    }

    public void setInstrumentCurrency(String instrumentCurrency) {
        this.instrumentCurrency = instrumentCurrency;
    }

    public BigDecimal getCurrentMarketValue() {
        return currentMarketValue;
    }

    public void setCurrentMarketValue(BigDecimal currentMarketValue) {
        this.currentMarketValue = currentMarketValue;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

//    public ReportData getReportData() {
//        return reportData;
//    }
//
//    public void setReportData(ReportData reportData) {
//        this.reportData = reportData;
//    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InstrumentData that = (InstrumentData) o;

        if (portfolioCode != null ? !portfolioCode.equals(that.portfolioCode) : that.portfolioCode != null)
            return false;
        if (portfolioName != null ? !portfolioName.equals(that.portfolioName) : that.portfolioName != null)
            return false;
        if (marketValueTotal != null ? !marketValueTotal.equals(that.marketValueTotal) : that.marketValueTotal != null) return false;
        if (institutionTotal != null ? !institutionTotal.equals(that.institutionTotal) : that.institutionTotal != null)
            return false;
        if (noOfAccounts != null ? !noOfAccounts.equals(that.noOfAccounts) : that.noOfAccounts != null) return false;
        if (instrumentCode != null ? !instrumentCode.equals(that.instrumentCode) : that.instrumentCode != null)
            return false;
        if (instrumentDescription != null ? !instrumentDescription.equals(that.instrumentDescription) : that.instrumentDescription != null)
            return false;
        if (nominalUnits != null ? !nominalUnits.equals(that.nominalUnits) : that.nominalUnits != null) return false;
        if (currentBookValue != null ? !currentBookValue.equals(that.currentBookValue) : that.currentBookValue != null)
            return false;
        if (instrumentCurrency != null ? !instrumentCurrency.equals(that.instrumentCurrency) : that.instrumentCurrency != null)
            return false;
        if (currentMarketValue != null ? !currentMarketValue.equals(that.currentMarketValue) : that.currentMarketValue != null)
            return false;
        if (tradeDate != null ? !tradeDate.equals(that.tradeDate) : that.tradeDate != null) return false;
        if (client != null ? !client.equals(that.client) : that.client != null) return false;
//        if (reportData != null ? !reportData.equals(that.reportData) : that.reportData != null) return false;
        return updatedDate != null ? updatedDate.equals(that.updatedDate) : that.updatedDate == null;
    }

    @Override
    public int hashCode() {
        int result = portfolioCode != null ? portfolioCode.hashCode() : 0;
        result = 31 * result + (portfolioName != null ? portfolioName.hashCode() : 0);
        result = 31 * result + (marketValueTotal != null ? marketValueTotal.hashCode() : 0);
        result = 31 * result + (institutionTotal != null ? institutionTotal.hashCode() : 0);
        result = 31 * result + (noOfAccounts != null ? noOfAccounts.hashCode() : 0);
        result = 31 * result + (instrumentCode != null ? instrumentCode.hashCode() : 0);
        result = 31 * result + (instrumentDescription != null ? instrumentDescription.hashCode() : 0);
        result = 31 * result + (nominalUnits != null ? nominalUnits.hashCode() : 0);
        result = 31 * result + (currentBookValue != null ? currentBookValue.hashCode() : 0);
        result = 31 * result + (instrumentCurrency != null ? instrumentCurrency.hashCode() : 0);
        result = 31 * result + (currentMarketValue != null ? currentMarketValue.hashCode() : 0);
        result = 31 * result + (tradeDate != null ? tradeDate.hashCode() : 0);
        result = 31 * result + (client != null ? client.hashCode() : 0);
//        result = 31 * result + (reportData != null ? reportData.hashCode() : 0);
        result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "InstrumentData{" +
                "id=" + id +
                ", portfolioCode='" + portfolioCode + '\'' +
                ", portfolioName='" + portfolioName + '\'' +
                ", marketValueTotal=" + marketValueTotal +
                ", institutionTotal=" + institutionTotal +
                ", noOfAccounts=" + noOfAccounts +
                ", instrumentCode='" + instrumentCode + '\'' +
                ", instrumentDescription='" + instrumentDescription + '\'' +
                ", nominalUnits=" + nominalUnits +
                ", currentBookValue=" + currentBookValue +
                ", instrumentCurrency='" + instrumentCurrency + '\'' +
                ", currentMarketValue=" + currentMarketValue +
                ", tradeDate=" + tradeDate +
                ", client=" + client +
//                ", reportData=" + reportData +
                ", updatedDate=" + updatedDate +
                '}';
    }
}

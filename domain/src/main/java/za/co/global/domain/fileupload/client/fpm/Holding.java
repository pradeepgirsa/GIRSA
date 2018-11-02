package za.co.global.domain.fileupload.client.fpm;

import za.co.global.domain.client.Client;
import za.co.global.domain.report.ReportData;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "holding")
public class Holding implements Serializable {

    private static final long serialVersionUID = 5500456840911111232L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "portfolio_code")
    private String portfolioCode;

    @Column(name = "portfolio_name")
    private String portfolioName;

    @Column(name = "currency")
    private String currency;

    @Column(name = "net_base_current_book_value")
    private BigDecimal netBaseCurrentBookValue;

    @Column(name = "net_base_prior_market_value")
    private BigDecimal netBasePriorMarketValue;

    @Column(name = "net_base_current_market_value")
    private BigDecimal netBaseCurrentMarketValue;

    @Column(name = "net_percent_of_market_value")
    private BigDecimal netPercentOfMarketValue;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "holding_id", referencedColumnName = "ID", nullable = false)
    private List<HoldingCategory> holdingCategories = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = true, insertable = false, updatable = false)
    private Client client;

//    @Column
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "report_data_id", nullable = true, insertable = false, updatable = false)
//    private ReportData reportData;

    @Column(name = "updated_date")
    private Date updatedDate;

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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<HoldingCategory> getHoldingCategories() {
        return holdingCategories;
    }

    public void setHoldingCategories(List<HoldingCategory> holdingCategories) {
        this.holdingCategories = holdingCategories;
    }

    public BigDecimal getNetBaseCurrentBookValue() {
        return netBaseCurrentBookValue;
    }

    public void setNetBaseCurrentBookValue(BigDecimal netBaseCurrentBookValue) {
        this.netBaseCurrentBookValue = netBaseCurrentBookValue;
    }

    public BigDecimal getNetBasePriorMarketValue() {
        return netBasePriorMarketValue;
    }

    public void setNetBasePriorMarketValue(BigDecimal netBasePriorMarketValue) {
        this.netBasePriorMarketValue = netBasePriorMarketValue;
    }

    public BigDecimal getNetBaseCurrentMarketValue() {
        return netBaseCurrentMarketValue;
    }

    public void setNetBaseCurrentMarketValue(BigDecimal netBaseCurrentMarketValue) {
        this.netBaseCurrentMarketValue = netBaseCurrentMarketValue;
    }

    public BigDecimal getNetPercentOfMarketValue() {
        return netPercentOfMarketValue;
    }

    public void setNetPercentOfMarketValue(BigDecimal netPercentOfMarketValue) {
        this.netPercentOfMarketValue = netPercentOfMarketValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

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

        Holding holding = (Holding) o;

        if (portfolioCode != null ? !portfolioCode.equals(holding.portfolioCode) : holding.portfolioCode != null)
            return false;
        if (portfolioName != null ? !portfolioName.equals(holding.portfolioName) : holding.portfolioName != null)
            return false;
        if (currency != null ? !currency.equals(holding.currency) : holding.currency != null) return false;
        if (netBaseCurrentBookValue != null ? !netBaseCurrentBookValue.equals(holding.netBaseCurrentBookValue) : holding.netBaseCurrentBookValue != null)
            return false;
        if (netBasePriorMarketValue != null ? !netBasePriorMarketValue.equals(holding.netBasePriorMarketValue) : holding.netBasePriorMarketValue != null)
            return false;
        if (netBaseCurrentMarketValue != null ? !netBaseCurrentMarketValue.equals(holding.netBaseCurrentMarketValue) : holding.netBaseCurrentMarketValue != null)
            return false;
        if (netPercentOfMarketValue != null ? !netPercentOfMarketValue.equals(holding.netPercentOfMarketValue) : holding.netPercentOfMarketValue != null)
            return false;
        if (holdingCategories != null ? !holdingCategories.equals(holding.holdingCategories) : holding.holdingCategories != null)
            return false;
        if (client != null ? !client.equals(holding.client) : holding.client != null) return false;
        return updatedDate != null ? updatedDate.equals(holding.updatedDate) : holding.updatedDate == null;
    }

    @Override
    public int hashCode() {
        int result = portfolioCode != null ? portfolioCode.hashCode() : 0;
        result = 31 * result + (portfolioName != null ? portfolioName.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (netBaseCurrentBookValue != null ? netBaseCurrentBookValue.hashCode() : 0);
        result = 31 * result + (netBasePriorMarketValue != null ? netBasePriorMarketValue.hashCode() : 0);
        result = 31 * result + (netBaseCurrentMarketValue != null ? netBaseCurrentMarketValue.hashCode() : 0);
        result = 31 * result + (netPercentOfMarketValue != null ? netPercentOfMarketValue.hashCode() : 0);
        result = 31 * result + (holdingCategories != null ? holdingCategories.hashCode() : 0);
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
        return result;
    }
}

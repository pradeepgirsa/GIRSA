package za.co.global.domain.upload;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
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
    private List<HoldingCategory> holdingCategories;

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
}

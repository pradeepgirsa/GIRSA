package za.co.global.domain.upload;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "instrument")
public class Instrument implements Serializable {

    private static final long serialVersionUID = 5500456840911171132L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "holding_category_id", nullable = false)
    private HoldingCategory holdingCategory;

    @Column(name = "instrument_code")
    private String instrumentCode;

    @Column(name = "instrument_description")
    private String instrumentDescription;

    @Column(name = "issue_currency")
    private String issueCurrency;

    @Column(name = "nominal_value")
    private BigDecimal nominalValue;

    @Column(name = "base_price")
    private BigDecimal basePrice;

    @Column(name = "holding_price")
    private BigDecimal holdingPrice;

    @Column(name = "percent_of_change_base_price")
    private BigDecimal percentOfChangeBaseChange;

    @Column(name = "current_book_value")
    private BigDecimal currentBookValue;

    @Column(name = "base_current_book_value")
    private BigDecimal baseCurrentBookValue;

    @Column(name = "base_prior_market_value")
    private BigDecimal basePriorMarketValue;

    @Column(name = "base_current_market_value")
    private BigDecimal baseCurrentMarketValue;

    @Column(name = "base_change_market_value")
    private BigDecimal baseChangeMarketValue;

    @Column(name = "percent_of_change_mkt_value")
    private BigDecimal percentOfChangeMarketValue;

    @Column(name = "percent_of_market_value")
    private BigDecimal percentOfMarketValue;

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

    public String getIssueCurrency() {
        return issueCurrency;
    }

    public void setIssueCurrency(String issueCurrency) {
        this.issueCurrency = issueCurrency;
    }

    public BigDecimal getNominalValue() {
        return nominalValue;
    }

    public void setNominalValue(BigDecimal nominalValue) {
        this.nominalValue = nominalValue;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public BigDecimal getHoldingPrice() {
        return holdingPrice;
    }

    public void setHoldingPrice(BigDecimal holdingPrice) {
        this.holdingPrice = holdingPrice;
    }

    public BigDecimal getPercentOfChangeBaseChange() {
        return percentOfChangeBaseChange;
    }

    public void setPercentOfChangeBaseChange(BigDecimal percentOfChangeBaseChange) {
        this.percentOfChangeBaseChange = percentOfChangeBaseChange;
    }

    public BigDecimal getCurrentBookValue() {
        return currentBookValue;
    }

    public void setCurrentBookValue(BigDecimal currentBookValue) {
        this.currentBookValue = currentBookValue;
    }

    public BigDecimal getBaseCurrentBookValue() {
        return baseCurrentBookValue;
    }

    public void setBaseCurrentBookValue(BigDecimal baseCurrentBookValue) {
        this.baseCurrentBookValue = baseCurrentBookValue;
    }

    public BigDecimal getBasePriorMarketValue() {
        return basePriorMarketValue;
    }

    public void setBasePriorMarketValue(BigDecimal basePriorMarketValue) {
        this.basePriorMarketValue = basePriorMarketValue;
    }

    public BigDecimal getBaseCurrentMarketValue() {
        return baseCurrentMarketValue;
    }

    public void setBaseCurrentMarketValue(BigDecimal baseCurrentMarketValue) {
        this.baseCurrentMarketValue = baseCurrentMarketValue;
    }

    public BigDecimal getBaseChangeMarketValue() {
        return baseChangeMarketValue;
    }

    public void setBaseChangeMarketValue(BigDecimal baseChangeMarketValue) {
        this.baseChangeMarketValue = baseChangeMarketValue;
    }

    public BigDecimal getPercentOfChangeMarketValue() {
        return percentOfChangeMarketValue;
    }

    public void setPercentOfChangeMarketValue(BigDecimal percentOfChangeMarketValue) {
        this.percentOfChangeMarketValue = percentOfChangeMarketValue;
    }

    public BigDecimal getPercentOfMarketValue() {
        return percentOfMarketValue;
    }

    public void setPercentOfMarketValue(BigDecimal percentOfMarketValue) {
        this.percentOfMarketValue = percentOfMarketValue;
    }
}

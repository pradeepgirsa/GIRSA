package za.co.global.domain.fileupload.client.fpm;

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
    @JoinColumn(name = "holding_category_id", nullable = false, insertable = false, updatable = false)
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HoldingCategory getHoldingCategory() {
        return holdingCategory;
    }

    public void setHoldingCategory(HoldingCategory holdingCategory) {
        this.holdingCategory = holdingCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Instrument that = (Instrument) o;

        if (holdingCategory != null ? !holdingCategory.equals(that.holdingCategory) : that.holdingCategory != null)
            return false;
        if (instrumentCode != null ? !instrumentCode.equals(that.instrumentCode) : that.instrumentCode != null)
            return false;
        if (instrumentDescription != null ? !instrumentDescription.equals(that.instrumentDescription) : that.instrumentDescription != null)
            return false;
        if (issueCurrency != null ? !issueCurrency.equals(that.issueCurrency) : that.issueCurrency != null)
            return false;
        if (nominalValue != null ? !nominalValue.equals(that.nominalValue) : that.nominalValue != null) return false;
        if (basePrice != null ? !basePrice.equals(that.basePrice) : that.basePrice != null) return false;
        if (holdingPrice != null ? !holdingPrice.equals(that.holdingPrice) : that.holdingPrice != null) return false;
        if (percentOfChangeBaseChange != null ? !percentOfChangeBaseChange.equals(that.percentOfChangeBaseChange) : that.percentOfChangeBaseChange != null)
            return false;
        if (currentBookValue != null ? !currentBookValue.equals(that.currentBookValue) : that.currentBookValue != null)
            return false;
        if (baseCurrentBookValue != null ? !baseCurrentBookValue.equals(that.baseCurrentBookValue) : that.baseCurrentBookValue != null)
            return false;
        if (basePriorMarketValue != null ? !basePriorMarketValue.equals(that.basePriorMarketValue) : that.basePriorMarketValue != null)
            return false;
        if (baseCurrentMarketValue != null ? !baseCurrentMarketValue.equals(that.baseCurrentMarketValue) : that.baseCurrentMarketValue != null)
            return false;
        if (baseChangeMarketValue != null ? !baseChangeMarketValue.equals(that.baseChangeMarketValue) : that.baseChangeMarketValue != null)
            return false;
        if (percentOfChangeMarketValue != null ? !percentOfChangeMarketValue.equals(that.percentOfChangeMarketValue) : that.percentOfChangeMarketValue != null)
            return false;
        return percentOfMarketValue != null ? percentOfMarketValue.equals(that.percentOfMarketValue) : that.percentOfMarketValue == null;
    }

    @Override
    public int hashCode() {
        int result = holdingCategory != null ? holdingCategory.hashCode() : 0;
        result = 31 * result + (instrumentCode != null ? instrumentCode.hashCode() : 0);
        result = 31 * result + (instrumentDescription != null ? instrumentDescription.hashCode() : 0);
        result = 31 * result + (issueCurrency != null ? issueCurrency.hashCode() : 0);
        result = 31 * result + (nominalValue != null ? nominalValue.hashCode() : 0);
        result = 31 * result + (basePrice != null ? basePrice.hashCode() : 0);
        result = 31 * result + (holdingPrice != null ? holdingPrice.hashCode() : 0);
        result = 31 * result + (percentOfChangeBaseChange != null ? percentOfChangeBaseChange.hashCode() : 0);
        result = 31 * result + (currentBookValue != null ? currentBookValue.hashCode() : 0);
        result = 31 * result + (baseCurrentBookValue != null ? baseCurrentBookValue.hashCode() : 0);
        result = 31 * result + (basePriorMarketValue != null ? basePriorMarketValue.hashCode() : 0);
        result = 31 * result + (baseCurrentMarketValue != null ? baseCurrentMarketValue.hashCode() : 0);
        result = 31 * result + (baseChangeMarketValue != null ? baseChangeMarketValue.hashCode() : 0);
        result = 31 * result + (percentOfChangeMarketValue != null ? percentOfChangeMarketValue.hashCode() : 0);
        result = 31 * result + (percentOfMarketValue != null ? percentOfMarketValue.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "id=" + id +
                ", holdingCategory=" + holdingCategory +
                ", instrumentCode='" + instrumentCode + '\'' +
                ", instrumentDescription='" + instrumentDescription + '\'' +
                ", issueCurrency='" + issueCurrency + '\'' +
                ", nominalValue=" + nominalValue +
                ", basePrice=" + basePrice +
                ", holdingPrice=" + holdingPrice +
                ", percentOfChangeBaseChange=" + percentOfChangeBaseChange +
                ", currentBookValue=" + currentBookValue +
                ", baseCurrentBookValue=" + baseCurrentBookValue +
                ", basePriorMarketValue=" + basePriorMarketValue +
                ", baseCurrentMarketValue=" + baseCurrentMarketValue +
                ", baseChangeMarketValue=" + baseChangeMarketValue +
                ", percentOfChangeMarketValue=" + percentOfChangeMarketValue +
                ", percentOfMarketValue=" + percentOfMarketValue +
                '}';
    }
}

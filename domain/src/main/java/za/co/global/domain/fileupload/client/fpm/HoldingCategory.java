package za.co.global.domain.fileupload.client.fpm;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Table(name = "holding_category")
@Entity
public class HoldingCategory implements Serializable {

    private static final long serialVersionUID = 5500456840911112332L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "holding_id", nullable = false, insertable = false, updatable = false)
    private Holding holding;

    @Column(name = "category")
    private String category;

    @Column(name = "t_base_current_book_value")
    private BigDecimal totalBaseCurrentBookValue;

    @Column(name = "t_base_prior_market_value")
    private BigDecimal totalBasePriorMarketValue;

    @Column(name = "t_base_current_market_value")
    private BigDecimal totalBaseCurrentMarketValue;

    @Column(name = "t_percent_of_market_value")
    private BigDecimal totalPercentOfMarketValue;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "holding_category_id", referencedColumnName = "ID", nullable = false)
    private List<Instrument> instruments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Holding getHolding() {
        return holding;
    }

    public void setHolding(Holding holding) {
        this.holding = holding;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getTotalBaseCurrentBookValue() {
        return totalBaseCurrentBookValue;
    }

    public void setTotalBaseCurrentBookValue(BigDecimal totalBaseCurrentBookValue) {
        this.totalBaseCurrentBookValue = totalBaseCurrentBookValue;
    }

    public BigDecimal getTotalBasePriorMarketValue() {
        return totalBasePriorMarketValue;
    }

    public void setTotalBasePriorMarketValue(BigDecimal totalBasePriorMarketValue) {
        this.totalBasePriorMarketValue = totalBasePriorMarketValue;
    }

    public BigDecimal getTotalBaseCurrentMarketValue() {
        return totalBaseCurrentMarketValue;
    }

    public void setTotalBaseCurrentMarketValue(BigDecimal totalBaseCurrentMarketValue) {
        this.totalBaseCurrentMarketValue = totalBaseCurrentMarketValue;
    }

    public BigDecimal getTotalPercentOfMarketValue() {
        return totalPercentOfMarketValue;
    }

    public void setTotalPercentOfMarketValue(BigDecimal totalPercentOfMarketValue) {
        this.totalPercentOfMarketValue = totalPercentOfMarketValue;
    }

    public List<Instrument> getInstruments() {
        return instruments;
    }

    public void setInstruments(List<Instrument> instruments) {
        this.instruments = instruments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HoldingCategory that = (HoldingCategory) o;

        if (holding != null ? !holding.equals(that.holding) : that.holding != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (totalBaseCurrentBookValue != null ? !totalBaseCurrentBookValue.equals(that.totalBaseCurrentBookValue) : that.totalBaseCurrentBookValue != null)
            return false;
        if (totalBasePriorMarketValue != null ? !totalBasePriorMarketValue.equals(that.totalBasePriorMarketValue) : that.totalBasePriorMarketValue != null)
            return false;
        if (totalBaseCurrentMarketValue != null ? !totalBaseCurrentMarketValue.equals(that.totalBaseCurrentMarketValue) : that.totalBaseCurrentMarketValue != null)
            return false;
        if (totalPercentOfMarketValue != null ? !totalPercentOfMarketValue.equals(that.totalPercentOfMarketValue) : that.totalPercentOfMarketValue != null)
            return false;
        return instruments != null ? instruments.equals(that.instruments) : that.instruments == null;
    }

    @Override
    public int hashCode() {
        int result = holding != null ? holding.hashCode() : 0;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (totalBaseCurrentBookValue != null ? totalBaseCurrentBookValue.hashCode() : 0);
        result = 31 * result + (totalBasePriorMarketValue != null ? totalBasePriorMarketValue.hashCode() : 0);
        result = 31 * result + (totalBaseCurrentMarketValue != null ? totalBaseCurrentMarketValue.hashCode() : 0);
        result = 31 * result + (totalPercentOfMarketValue != null ? totalPercentOfMarketValue.hashCode() : 0);
        result = 31 * result + (instruments != null ? instruments.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HoldingCategory{" +
                "id=" + id +
                ", holding=" + holding +
                ", category='" + category + '\'' +
                ", totalBaseCurrentBookValue=" + totalBaseCurrentBookValue +
                ", totalBasePriorMarketValue=" + totalBasePriorMarketValue +
                ", totalBaseCurrentMarketValue=" + totalBaseCurrentMarketValue +
                ", totalPercentOfMarketValue=" + totalPercentOfMarketValue +
                ", instruments=" + instruments +
                '}';
    }
}

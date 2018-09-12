package za.co.global.domain.upload;

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
    @JoinColumn(name = "holding_id", nullable = false)
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
}

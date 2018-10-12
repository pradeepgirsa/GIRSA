package za.co.global.domain.fileupload.mapping;

import com.gizbel.excel.annotations.ExcelBean;
import com.gizbel.excel.annotations.ExcelColumnHeader;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "issuer_mapping")
@ExcelBean
public class IssuerMappings implements Serializable {

    private static final long serialVersionUID = 5410446232211177632L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "daily_pricing_issuer_name", unique = true, nullable = false)
    @ExcelColumnHeader(columnHeader = "Daily Pricing Issuer Name")
    private String dailyPricingIssuerName;

    @Column(name = "barra_gir_Issuer_name")
    @ExcelColumnHeader(columnHeader = "BARRA GIR Issuer Name")
    private String barraGIRIssuerName;

    @Column(name = "barra_code")
    @ExcelColumnHeader(columnHeader = "BARRA CODE (IF LISTED)")
    private String barraCode;

    @Column(name = "issuer_code")
    @ExcelColumnHeader(columnHeader = "Issuer Code")
    private String issuerCode;

    @Column(name = "market_capitalisation")
    @ExcelColumnHeader(columnHeader = "Market Capitalisation")
    private String marketCapitalisation;

    @Column(name = "capital_reserves")
    @ExcelColumnHeader(columnHeader = "Capital & Reserves")
    private BigDecimal capitalReserves;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDailyPricingIssuerName() {
        return dailyPricingIssuerName;
    }

    public void setDailyPricingIssuerName(String dailyPricingIssuerName) {
        this.dailyPricingIssuerName = dailyPricingIssuerName;
    }

    public String getBarraGIRIssuerName() {
        return barraGIRIssuerName;
    }

    public void setBarraGIRIssuerName(String barraGIRIssuerName) {
        this.barraGIRIssuerName = barraGIRIssuerName;
    }

    public String getBarraCode() {
        return barraCode;
    }

    public void setBarraCode(String barraCode) {
        this.barraCode = barraCode;
    }

    public String getIssuerCode() {
        return issuerCode;
    }

    public void setIssuerCode(String issuerCode) {
        this.issuerCode = issuerCode;
    }

    public String getMarketCapitalisation() {
        return marketCapitalisation;
    }

    public void setMarketCapitalisation(String marketCapitalisation) {
        this.marketCapitalisation = marketCapitalisation;
    }

    public BigDecimal getCapitalReserves() {
        return capitalReserves;
    }

    public void setCapitalReserves(BigDecimal capitalReserves) {
        this.capitalReserves = capitalReserves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssuerMappings that = (IssuerMappings) o;

        if (dailyPricingIssuerName != null ? !dailyPricingIssuerName.equals(that.dailyPricingIssuerName) : that.dailyPricingIssuerName != null)
            return false;
        if (barraGIRIssuerName != null ? !barraGIRIssuerName.equals(that.barraGIRIssuerName) : that.barraGIRIssuerName != null)
            return false;
        if (barraCode != null ? !barraCode.equals(that.barraCode) : that.barraCode != null) return false;
        if (issuerCode != null ? !issuerCode.equals(that.issuerCode) : that.issuerCode != null) return false;
        if (marketCapitalisation != null ? !marketCapitalisation.equals(that.marketCapitalisation) : that.marketCapitalisation != null)
            return false;
        return capitalReserves != null ? capitalReserves.equals(that.capitalReserves) : that.capitalReserves == null;
    }

    @Override
    public int hashCode() {
        int result = dailyPricingIssuerName != null ? dailyPricingIssuerName.hashCode() : 0;
        result = 31 * result + (barraGIRIssuerName != null ? barraGIRIssuerName.hashCode() : 0);
        result = 31 * result + (barraCode != null ? barraCode.hashCode() : 0);
        result = 31 * result + (issuerCode != null ? issuerCode.hashCode() : 0);
        result = 31 * result + (marketCapitalisation != null ? marketCapitalisation.hashCode() : 0);
        result = 31 * result + (capitalReserves != null ? capitalReserves.hashCode() : 0);
        return result;
    }
}

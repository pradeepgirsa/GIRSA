package za.co.global.domain.fileupload.client;

import com.gizbel.excel.annotations.ExcelBean;
import com.gizbel.excel.annotations.ExcelColumnHeader;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "institutional_details")
@ExcelBean
public class InstitutionalDetails implements Serializable {

    private static final long serialVersionUID = 5510446840911177632L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fund_name", nullable = false)
    @ExcelColumnHeader(columnHeader = "FundName")
    private String fundName;

    @Column(name = "fund_code", unique = true, nullable = false)
    @ExcelColumnHeader(columnHeader = "JSE Code")
    private String fundCode;

    @Column(name = "asset_Market_value")
    @ExcelColumnHeader(columnHeader = "AssetMV", dataType = "big_decimal")
    private BigDecimal assetMktValue;

    @Column(name = "split")
    @ExcelColumnHeader(columnHeader = "InstSplit", dataType = "big_decimal")
    private BigDecimal split;

    @Column(name = "percentage")
    @ExcelColumnHeader(columnHeader = "Institutional %", dataType = "big_decimal")
    private BigDecimal percentage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public BigDecimal getAssetMktValue() {
        return assetMktValue;
    }

    public void setAssetMktValue(BigDecimal assetMktValue) {
        this.assetMktValue = assetMktValue;
    }

    public BigDecimal getSplit() {
        return split;
    }

    public void setSplit(BigDecimal split) {
        this.split = split;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InstitutionalDetails that = (InstitutionalDetails) o;

        if (fundName != null ? !fundName.equals(that.fundName) : that.fundName != null) return false;
        if (fundCode != null ? !fundCode.equals(that.fundCode) : that.fundCode != null) return false;
        if (assetMktValue != null ? !assetMktValue.equals(that.assetMktValue) : that.assetMktValue != null)
            return false;
        if (split != null ? !split.equals(that.split) : that.split != null) return false;
        return percentage != null ? percentage.equals(that.percentage) : that.percentage == null;
    }

    @Override
    public int hashCode() {
        int result = fundName != null ? fundName.hashCode() : 0;
        result = 31 * result + (fundCode != null ? fundCode.hashCode() : 0);
        result = 31 * result + (assetMktValue != null ? assetMktValue.hashCode() : 0);
        result = 31 * result + (split != null ? split.hashCode() : 0);
        result = 31 * result + (percentage != null ? percentage.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "InstitutionalDetails{" +
                "id=" + id +
                ", fundName='" + fundName + '\'' +
                ", fundCode='" + fundCode + '\'' +
                ", assetMktValue=" + assetMktValue +
                ", split=" + split +
                ", percentage=" + percentage +
                '}';
    }
}

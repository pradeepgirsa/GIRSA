package za.co.global.domain.fileupload.mapping;

import com.gizbel.excel.annotations.ExcelBean;
import com.gizbel.excel.annotations.ExcelColumnHeader;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sarb_classification_mapping")
@ExcelBean
public class SARBClassificationMapping implements Serializable {

    private static final long serialVersionUID = 5415846835211187632L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sarb_classification", unique = true, nullable = false)
    @ExcelColumnHeader(columnHeader = "SARB Classification")
    @NaturalId
    private String sarbClassification;

    @Column(name = "asset_class")
    @ExcelColumnHeader(columnHeader = "Asset Class")
    private String assetClass;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSarbClassification() {
        return sarbClassification;
    }

    public void setSarbClassification(String sarbClassification) {
        this.sarbClassification = sarbClassification;
    }

    public String getAssetClass() {
        return assetClass;
    }

    public void setAssetClass(String assetClass) {
        this.assetClass = assetClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SARBClassificationMapping that = (SARBClassificationMapping) o;

        if (sarbClassification != null ? !sarbClassification.equals(that.sarbClassification) : that.sarbClassification != null)
            return false;
        return assetClass != null ? assetClass.equals(that.assetClass) : that.assetClass == null;
    }

    @Override
    public int hashCode() {
        int result = sarbClassification != null ? sarbClassification.hashCode() : 0;
        result = 31 * result + (assetClass != null ? assetClass.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SARBClassificationMapping{" +
                "id=" + id +
                ", sarbClassification='" + sarbClassification + '\'' +
                ", assetClass='" + assetClass + '\'' +
                '}';
    }
}

package za.co.global.domain.fileupload.mapping;

import com.gizbel.excel.annotations.ExcelBean;
import com.gizbel.excel.annotations.ExcelColumnHeader;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "additional_classification")
@ExcelBean
public class AdditionalClassification implements Serializable {

    private static final long serialVersionUID = 5511446840911177632L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "industry", nullable = false)
    @ExcelColumnHeader(columnHeader = "Industry")
    private String industry;

    @Column(name = "super_sector", nullable = false)
    @ExcelColumnHeader(columnHeader = "Supersector")
    private String superSector;

    @Column(name = "sub_sector", nullable = false)
    @ExcelColumnHeader(columnHeader = "Subsector")
    private String subSector;

    @Column(name = "sector", nullable = false)
    @ExcelColumnHeader(columnHeader = "Sector")
    private String sector;

    @Column(name = "alpha_code", nullable = false)
    @ExcelColumnHeader(columnHeader = "Alpha Code")
    private String alphaCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getSuperSector() {
        return superSector;
    }

    public void setSuperSector(String superSector) {
        this.superSector = superSector;
    }

    public String getSubSector() {
        return subSector;
    }

    public void setSubSector(String subSector) {
        this.subSector = subSector;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getAlphaCode() {
        return alphaCode;
    }

    public void setAlphaCode(String alphaCode) {
        this.alphaCode = alphaCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdditionalClassification that = (AdditionalClassification) o;

        if (industry != null ? !industry.equals(that.industry) : that.industry != null) return false;
        if (superSector != null ? !superSector.equals(that.superSector) : that.superSector != null) return false;
        if (subSector != null ? !subSector.equals(that.subSector) : that.subSector != null) return false;
        if (sector != null ? !sector.equals(that.sector) : that.sector != null) return false;
        return alphaCode != null ? alphaCode.equals(that.alphaCode) : that.alphaCode == null;
    }

    @Override
    public int hashCode() {
        int result = industry != null ? industry.hashCode() : 0;
        result = 31 * result + (superSector != null ? superSector.hashCode() : 0);
        result = 31 * result + (subSector != null ? subSector.hashCode() : 0);
        result = 31 * result + (sector != null ? sector.hashCode() : 0);
        result = 31 * result + (alphaCode != null ? alphaCode.hashCode() : 0);
        return result;
    }
}

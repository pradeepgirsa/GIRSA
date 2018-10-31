package za.co.global.domain.fileupload.mapping;

import com.gizbel.excel.annotations.ExcelBean;
import com.gizbel.excel.annotations.ExcelColumnHeader;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "reg28_instr_type")
@ExcelBean
public class Reg28InstrumentType implements Serializable {

    private static final long serialVersionUID = 5410446845211187632L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reg28_instr_type", unique = true, nullable = false)
    @ExcelColumnHeader(columnHeader = "Reg28_InstrType")
    @NaturalId
    private String reg28InstrType;

    @Column(name = "rsa_or_foreign")
    @ExcelColumnHeader(columnHeader = "RSA or foreign")
    private String rsaOrForeign;

    @Column(name = "security_type")
    @ExcelColumnHeader(columnHeader = "Security Type")
    private String securityType;

    @Column(name = "institution_type")
    @ExcelColumnHeader(columnHeader = "Institution Type")
    private String institutionType;

    @Column(name = "market_cap")
    @ExcelColumnHeader(columnHeader = "Market Cap")
    private String marketCap;

    @Column(name = "bn_categories")
    @ExcelColumnHeader(columnHeader = "BN Categories")
    private String bnCategories;

    @Column(name = "asisa_defined2")
    @ExcelColumnHeader(columnHeader = "ASISADefined2")
    private String asisaDefined2;

    @Column(name = "aci_asset_Class")
    @ExcelColumnHeader(columnHeader = "ACIAssetClass")
    private String aciAssetClass;

    @Column(name = "add_classification1")
    @ExcelColumnHeader(columnHeader = "AddClassification1")
    private String addClassificationOne;

    @Column(name = "add_classification2")
    @ExcelColumnHeader(columnHeader = "AddClassification2")
    private String addClassificationTwo;

    @Column(name = "add_classification3")
    @ExcelColumnHeader(columnHeader = "AddClassification3")
    private String addClassificationThree;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReg28InstrType() {
        return reg28InstrType;
    }

    public void setReg28InstrType(String reg28InstrType) {
        this.reg28InstrType = reg28InstrType;
    }

    public String getRsaOrForeign() {
        return rsaOrForeign;
    }

    public void setRsaOrForeign(String rsaOrForeign) {
        this.rsaOrForeign = rsaOrForeign;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public String getInstitutionType() {
        return institutionType;
    }

    public void setInstitutionType(String institutionType) {
        this.institutionType = institutionType;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    public String getBnCategories() {
        return bnCategories;
    }

    public void setBnCategories(String bnCategories) {
        this.bnCategories = bnCategories;
    }

    public String getAsisaDefined2() {
        return asisaDefined2;
    }

    public void setAsisaDefined2(String asisaDefined2) {
        this.asisaDefined2 = asisaDefined2;
    }

    public String getAciAssetClass() {
        return aciAssetClass;
    }

    public void setAciAssetClass(String aciAssetClass) {
        this.aciAssetClass = aciAssetClass;
    }

    public String getAddClassificationOne() {
        return addClassificationOne;
    }

    public void setAddClassificationOne(String addClassificationOne) {
        this.addClassificationOne = addClassificationOne;
    }

    public String getAddClassificationTwo() {
        return addClassificationTwo;
    }

    public void setAddClassificationTwo(String addClassificationTwo) {
        this.addClassificationTwo = addClassificationTwo;
    }

    public String getAddClassificationThree() {
        return addClassificationThree;
    }

    public void setAddClassificationThree(String addClassificationThree) {
        this.addClassificationThree = addClassificationThree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reg28InstrumentType that = (Reg28InstrumentType) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(reg28InstrType, that.reg28InstrType) &&
                Objects.equals(rsaOrForeign, that.rsaOrForeign) &&
                Objects.equals(securityType, that.securityType) &&
                Objects.equals(institutionType, that.institutionType) &&
                Objects.equals(marketCap, that.marketCap) &&
                Objects.equals(bnCategories, that.bnCategories) &&
                Objects.equals(asisaDefined2, that.asisaDefined2) &&
                Objects.equals(aciAssetClass, that.aciAssetClass) &&
                Objects.equals(addClassificationOne, that.addClassificationOne) &&
                Objects.equals(addClassificationTwo, that.addClassificationTwo) &&
                Objects.equals(addClassificationThree, that.addClassificationThree);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, reg28InstrType, rsaOrForeign, securityType, institutionType, marketCap, bnCategories, asisaDefined2, aciAssetClass, addClassificationOne, addClassificationTwo, addClassificationThree);
    }
}

package za.co.global.domain.fileupload.mapping;

import com.gizbel.excel.annotations.ExcelBean;
import com.gizbel.excel.annotations.ExcelColumnHeader;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "derivative_types")
@ExcelBean
public class DerivativeTypes implements Serializable {

    private static final long serialVersionUID = 5511756840911787232L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_of_derivative", nullable = false)
    @ExcelColumnHeader(columnHeader = "Type of Derivative")
    private String typeOfDerivative;

    @Column(name = "local_classification", nullable = false)
    @ExcelColumnHeader(columnHeader = "Local Classification")
    private String localClassification;

    @Column(name = "foreign_classification", nullable = false)
    @ExcelColumnHeader(columnHeader = "Foreign Classification")
    private String foreignClassification;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeOfDerivative() {
        return typeOfDerivative;
    }

    public void setTypeOfDerivative(String typeOfDerivative) {
        this.typeOfDerivative = typeOfDerivative;
    }

    public String getLocalClassification() {
        return localClassification;
    }

    public void setLocalClassification(String localClassification) {
        this.localClassification = localClassification;
    }

    public String getForeignClassification() {
        return foreignClassification;
    }

    public void setForeignClassification(String foreignClassification) {
        this.foreignClassification = foreignClassification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DerivativeTypes that = (DerivativeTypes) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(typeOfDerivative, that.typeOfDerivative) &&
                Objects.equals(localClassification, that.localClassification) &&
                Objects.equals(foreignClassification, that.foreignClassification);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, typeOfDerivative, localClassification, foreignClassification);
    }
}

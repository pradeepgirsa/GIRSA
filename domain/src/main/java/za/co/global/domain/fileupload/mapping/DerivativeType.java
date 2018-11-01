package za.co.global.domain.fileupload.mapping;

import com.gizbel.excel.annotations.ExcelBean;
import com.gizbel.excel.annotations.ExcelColumnHeader;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "derivative_types")
@ExcelBean
public class DerivativeType implements Serializable {

    private static final long serialVersionUID = 5511756840911787232L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_of_derivative", nullable = false)
    @ExcelColumnHeader(columnHeader = "Type of Derivative")
    @NaturalId
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        DerivativeType that = (DerivativeType) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(localClassification, that.localClassification) &&
                Objects.equals(foreignClassification, that.foreignClassification);
    }

    @Override
    public int hashCode() {

        return Objects.hash(type, localClassification, foreignClassification);
    }
}

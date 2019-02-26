package za.co.global.domain.fileupload.mapping;

import com.gizbel.excel.annotations.ExcelBean;
import com.gizbel.excel.annotations.ExcelColumnHeader;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "derivative_type")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DerivativeType that = (DerivativeType) o;

        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (localClassification != null ? !localClassification.equals(that.localClassification) : that.localClassification != null)
            return false;
        return foreignClassification != null ? foreignClassification.equals(that.foreignClassification) : that.foreignClassification == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (localClassification != null ? localClassification.hashCode() : 0);
        result = 31 * result + (foreignClassification != null ? foreignClassification.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DerivativeType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", localClassification='" + localClassification + '\'' +
                ", foreignClassification='" + foreignClassification + '\'' +
                '}';
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


}

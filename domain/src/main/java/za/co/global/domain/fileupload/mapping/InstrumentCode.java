package za.co.global.domain.fileupload.mapping;

import com.gizbel.excel.annotations.ExcelBean;
import com.gizbel.excel.annotations.ExcelColumnHeader;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "instrument_code")
@ExcelBean
public class InstrumentCode implements Serializable {

    private static final long serialVersionUID = 5321556232211177632L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "manager_code")
    @ExcelColumnHeader(columnHeader = "MANAGER CODE")
    private String managerCode;

    @Column(name = "barra_code")
    @ExcelColumnHeader(columnHeader = "BARRA CODE")
    private String barraCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManagerCode() {
        return managerCode;
    }

    public void setManagerCode(String managerCode) {
        this.managerCode = managerCode;
    }

    public String getBarraCode() {
        return barraCode;
    }

    public void setBarraCode(String barraCode) {
        this.barraCode = barraCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InstrumentCode that = (InstrumentCode) o;

        if (managerCode != null ? !managerCode.equals(that.managerCode) : that.managerCode != null) return false;
        return barraCode != null ? barraCode.equals(that.barraCode) : that.barraCode == null;
    }

    @Override
    public int hashCode() {
        int result = managerCode != null ? managerCode.hashCode() : 0;
        result = 31 * result + (barraCode != null ? barraCode.hashCode() : 0);
        return result;
    }

}

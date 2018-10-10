package za.co.global.domain.fileupload.client;

import com.gizbel.excel.annotations.ExcelBean;
import com.gizbel.excel.annotations.ExcelColumnHeader;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


@Entity
@Table(name = "number_of_accounts")
@ExcelBean
public class NumberOfAccounts  implements Serializable {

    private static final long serialVersionUID = 5511446840911177632L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fund_code", unique = true, nullable = false)
    @ExcelColumnHeader(columnHeader = "ACIFundCode")
    private String fundCode;

    @Column(name = "total")
    @ExcelColumnHeader(columnHeader = "Total", dataType = "big_decimal")
    private BigDecimal total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NumberOfAccounts that = (NumberOfAccounts) o;

        if (fundCode != null ? !fundCode.equals(that.fundCode) : that.fundCode != null) return false;
        return total != null ? total.equals(that.total) : that.total == null;
    }

    @Override
    public int hashCode() {
        int result = fundCode != null ? fundCode.hashCode() : 0;
        result = 31 * result + (total != null ? total.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NumberOfAccounts{" +
                "id=" + id +
                ", fundCode='" + fundCode + '\'' +
                ", total=" + total +
                '}';
    }
}

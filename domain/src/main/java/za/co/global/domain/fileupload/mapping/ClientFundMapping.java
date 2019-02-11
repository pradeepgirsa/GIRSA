package za.co.global.domain.fileupload.mapping;

import com.gizbel.excel.annotations.ExcelBean;
import com.gizbel.excel.annotations.ExcelColumnHeader;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "client_fund_mapping")
@ExcelBean
public class ClientFundMapping implements Serializable {

    private static final long serialVersionUID = 5410446842211177632L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mgr_fund_code", unique = true, nullable = false)
    @ExcelColumnHeader(columnHeader = "Manager Fund Code")
    @NaturalId
    private String managerFundCode;

    @Column(name = "client_fund_code")
    @ExcelColumnHeader(columnHeader = "Client Fund Code")
    private String clientFundCode;

    @Column(name = "mgr_fund_name")
    @ExcelColumnHeader(columnHeader = "Manager Fund Name")
    private String managerFundName;

    @Column(name = "barra_fund_name")
    @ExcelColumnHeader(columnHeader = "BARRA FUND NAME")
    private String barraFundName;

    @Column(name = "fund_currency")
    @ExcelColumnHeader(columnHeader = "Fund Currency")
    private String fundCurrency;

    @Column(name = "comments")
    @ExcelColumnHeader(columnHeader = "Comments")
    private String comments;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManagerFundCode() {
        return managerFundCode;
    }

    public void setManagerFundCode(String managerFundCode) {
        this.managerFundCode = managerFundCode;
    }

    public String getClientFundCode() {
        return clientFundCode;
    }

    public void setClientFundCode(String clientFundCode) {
        this.clientFundCode = clientFundCode;
    }

    public String getManagerFundName() {
        return managerFundName;
    }

    public void setManagerFundName(String managerFundName) {
        this.managerFundName = managerFundName;
    }

    public String getBarraFundName() {
        return barraFundName;
    }

    public void setBarraFundName(String barraFundName) {
        this.barraFundName = barraFundName;
    }

    public String getFundCurrency() {
        return fundCurrency;
    }

    public void setFundCurrency(String fundCurrency) {
        this.fundCurrency = fundCurrency;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientFundMapping that = (ClientFundMapping) o;

        if (managerFundCode != null ? !managerFundCode.equals(that.managerFundCode) : that.managerFundCode != null)
            return false;
        if (clientFundCode != null ? !clientFundCode.equals(that.clientFundCode) : that.clientFundCode != null) return false;
        if (managerFundName != null ? !managerFundName.equals(that.managerFundName) : that.managerFundName != null)
            return false;
        if (barraFundName != null ? !barraFundName.equals(that.barraFundName) : that.barraFundName != null)
            return false;
        if (fundCurrency != null ? !fundCurrency.equals(that.fundCurrency) : that.fundCurrency != null) return false;
        return comments != null ? comments.equals(that.comments) : that.comments == null;
    }

    @Override
    public int hashCode() {
        int result = managerFundCode != null ? managerFundCode.hashCode() : 0;
        result = 31 * result + (clientFundCode != null ? clientFundCode.hashCode() : 0);
        result = 31 * result + (managerFundName != null ? managerFundName.hashCode() : 0);
        result = 31 * result + (barraFundName != null ? barraFundName.hashCode() : 0);
        result = 31 * result + (fundCurrency != null ? fundCurrency.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        return result;
    }
}

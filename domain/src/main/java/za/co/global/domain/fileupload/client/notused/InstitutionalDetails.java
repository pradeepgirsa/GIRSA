//package za.co.global.domain.fileupload.client;
//
//import com.gizbel.excel.annotations.ExcelBean;
//import com.gizbel.excel.annotations.ExcelColumnHeader;
//import org.hibernate.annotations.NaturalId;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.util.Date;
//
//@Entity
//@Table(name = "institutional_details")
//@ExcelBean
//public class InstitutionalDetails implements Serializable {
//
//    private static final long serialVersionUID = 5510446840911177632L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "fund_name", nullable = false)
//    @ExcelColumnHeader(columnHeader = "FundName")
//    private String fundName;
//
//    @Column(name = "fund_code", unique = true, nullable = false)
//    @ExcelColumnHeader(columnHeader = "ACIFundCode")
//    @NaturalId
//    private String clientFundCode;
//
//    @Column(name = "institutional_total")
//    @ExcelColumnHeader(columnHeader = "InstitutionalTotal", dataType = "big_decimal")
//    private BigDecimal institutionalTotal;
//
//    @Column(name = "total")
//    @ExcelColumnHeader(columnHeader = "Total", dataType = "big_decimal")
//    private BigDecimal total;
//
//    @Column(name = "percentage")
//    @ExcelColumnHeader(columnHeader = "Institutional %", dataType = "big_decimal")
//    private BigDecimal percentage;
//
//    @Column(name = "date")
//    @ExcelColumnHeader(columnHeader = "Date", dataType = "date")
//    private Date date;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getFundName() {
//        return fundName;
//    }
//
//    public void setFundName(String fundName) {
//        this.fundName = fundName;
//    }
//
//    public String getClientFundCode() {
//        return clientFundCode;
//    }
//
//    public void setClientFundCode(String clientFundCode) {
//        this.clientFundCode = clientFundCode;
//    }
//
//    public BigDecimal getInstitutionalTotal() {
//        return institutionalTotal;
//    }
//
//    public void setInstitutionalTotal(BigDecimal institutionalTotal) {
//        this.institutionalTotal = institutionalTotal;
//    }
//
//    public BigDecimal getTotal() {
//        return total;
//    }
//
//    public void setTotal(BigDecimal total) {
//        this.total = total;
//    }
//
//    public BigDecimal getPercentage() {
//        return percentage;
//    }
//
//    public void setPercentage(BigDecimal percentage) {
//        this.percentage = percentage;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        InstitutionalDetails that = (InstitutionalDetails) o;
//
//        if (fundName != null ? !fundName.equals(that.fundName) : that.fundName != null) return false;
//        if (clientFundCode != null ? !clientFundCode.equals(that.clientFundCode) : that.clientFundCode != null) return false;
//        if (institutionalTotal != null ? !institutionalTotal.equals(that.institutionalTotal) : that.institutionalTotal != null)
//            return false;
//        if (total != null ? !total.equals(that.total) : that.total != null) return false;
//        return percentage != null ? percentage.equals(that.percentage) : that.percentage == null;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = fundName != null ? fundName.hashCode() : 0;
//        result = 31 * result + (clientFundCode != null ? clientFundCode.hashCode() : 0);
//        result = 31 * result + (institutionalTotal != null ? institutionalTotal.hashCode() : 0);
//        result = 31 * result + (total != null ? total.hashCode() : 0);
//        result = 31 * result + (percentage != null ? percentage.hashCode() : 0);
//        return result;
//    }
//
//    @Override
//    public String toString() {
//        return "InstitutionalDetails{" +
//                "id=" + id +
//                ", fundName='" + fundName + '\'' +
//                ", clientFundCode='" + clientFundCode + '\'' +
//                ", institutionalTotal=" + institutionalTotal +
//                ", total=" + total +
//                ", percentage=" + percentage +
//                ", date=" + date +
//                '}';
//    }
//}

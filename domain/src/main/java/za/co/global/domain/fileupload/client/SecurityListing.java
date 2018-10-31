package za.co.global.domain.fileupload.client;

import com.gizbel.excel.annotations.ExcelBean;
import com.gizbel.excel.annotations.ExcelColumnHeader;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "security_listing")
@ExcelBean
public class SecurityListing implements Serializable {

    private static final long serialVersionUID = 5511446840911177632L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sec_code", nullable = false)
    @ExcelColumnHeader(columnHeader = "sec_code")
    @NaturalId
    private String securityCode;

    @Column(name = "first_payment_date")
    @ExcelColumnHeader(columnHeader = "Date_FirstPayment", dataType = "date")
    private Date firstPaymentDate;

    @Column(name = "coupon_payment_dates")
    @ExcelColumnHeader(columnHeader = "Dates_CouponPayment")
    private String couponPaymentDates;

    @Column(name = "maturity_date")
    @ExcelColumnHeader(columnHeader = "Date_Maturity", dataType = "date")
    private Date maturityDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public Date getFirstPaymentDate() {
        return firstPaymentDate;
    }

    public void setFirstPaymentDate(Date firstPaymentDate) {
        this.firstPaymentDate = firstPaymentDate;
    }

    public void setCouponPaymentDates(String couponPaymentDates) {
        this.couponPaymentDates = couponPaymentDates;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getCouponPaymentDates() {
        return couponPaymentDates;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SecurityListing that = (SecurityListing) o;

        if (securityCode != null ? !securityCode.equals(that.securityCode) : that.securityCode != null) return false;
        if (firstPaymentDate != null ? !firstPaymentDate.equals(that.firstPaymentDate) : that.firstPaymentDate != null)
            return false;
        if (couponPaymentDates != null ? !couponPaymentDates.equals(that.couponPaymentDates) : that.couponPaymentDates != null)
            return false;
        return maturityDate != null ? maturityDate.equals(that.maturityDate) : that.maturityDate == null;
    }

    @Override
    public int hashCode() {
        int result = securityCode != null ? securityCode.hashCode() : 0;
        result = 31 * result + (firstPaymentDate != null ? firstPaymentDate.hashCode() : 0);
        result = 31 * result + (couponPaymentDates != null ? couponPaymentDates.hashCode() : 0);
        result = 31 * result + (maturityDate != null ? maturityDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SecurityListing{" +
                "id=" + id +
                ", securityCode='" + securityCode + '\'' +
                ", firstPaymentDate=" + firstPaymentDate +
                ", couponPaymentDates='" + couponPaymentDates + '\'' +
                ", maturityDate=" + maturityDate +
                '}';
    }
}

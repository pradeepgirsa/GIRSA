package za.co.global.domain.fileupload.client;

import com.gizbel.excel.annotations.ExcelColumnHeader;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "security_listing")
public class SecurityListing implements Serializable {

    private static final long serialVersionUID = 5511446840911177632L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sec_code", nullable = false)
    @ExcelColumnHeader(columnHeader = "sec_code")
    private String securityCode;

    @Column(name = "first_payment_date")
    @ExcelColumnHeader(columnHeader = "Date_FirstPayment", dataType = "date")
    private Date firstPaymentDate;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SecurityListing that = (SecurityListing) o;

        if (securityCode != null ? !securityCode.equals(that.securityCode) : that.securityCode != null) return false;
        return firstPaymentDate != null ? firstPaymentDate.equals(that.firstPaymentDate) : that.firstPaymentDate == null;
    }

    @Override
    public int hashCode() {
        int result = securityCode != null ? securityCode.hashCode() : 0;
        result = 31 * result + (firstPaymentDate != null ? firstPaymentDate.hashCode() : 0);
        return result;
    }


}

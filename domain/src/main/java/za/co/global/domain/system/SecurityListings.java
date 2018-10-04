//package za.co.global.domain.system;
//
//import com.gizbel.excel.annotations.ExcelColumnHeader;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.Date;
//
//@Entity
//@Table(name = "security_listings")
//public class SecurityListings implements Serializable {
//
//    private static final long serialVersionUID = 5511446840911177632L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "sec_code", nullable = false)
//    @ExcelColumnHeader(columnHeader = "sec_code")
//    private String securityCode;
//
//    @Column(name = "first_payment_date")
//    @ExcelColumnHeader(columnHeader = "Date_FirstPayment", dataType = "date")
//    private Date firstPaymentDate;
//
//    //TODO - add all
//
//}

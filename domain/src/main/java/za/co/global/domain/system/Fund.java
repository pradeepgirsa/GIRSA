//package za.co.global.domain.system;
//
//import za.co.global.domain.EntityStatus;
//
//import javax.persistence.*;
//import java.io.Serializable;
//
//@Entity
//@Table(name = "fund")
//public class Fund implements Serializable {
//
//    private static final long serialVersionUID = 5510446842211177632L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "fund_code", unique = true, nullable = false)
//    private String fundCode;
//
//    @Column(name = "fund_name")
//    private String fundName;
//
//    @Column(name="status", nullable = false)
//    @Enumerated(EnumType.STRING)
//    private EntityStatus status = EntityStatus.ACTIVE;
//
//    @Column(name="currency", nullable = false)
//    private String currency;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getFundCode() {
//        return fundCode;
//    }
//
//    public void setFundCode(String fundCode) {
//        this.fundCode = fundCode;
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
//    public EntityStatus getStatus() {
//        return status;
//    }
//
//    public void setStatus(EntityStatus status) {
//        this.status = status;
//    }
//
//    public String getCurrency() {
//        return currency;
//    }
//
//    public void setCurrency(String currency) {
//        this.currency = currency;
//    }
//}

//package za.co.global.domain.fileupload.client;
//
//import com.gizbel.excel.annotations.ExcelBean;
import com.gizbel.excel.annotations.ExcelColumnHeader;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.Date;
//import java.util.Objects;
//
//@Entity
//@Table(name = "transaction_listing")
//@ExcelBean
//public class TransactionListing implements Serializable {
//
//    private static final long serialVersionUID = 3536471384821187632L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    /*@Column(name = "sec_code", unique = true, nullable = false)
//    @ExcelColumnHeader(columnHeader = "sec_code")
//    private String secCode;
//
//    @Column(name = "manco")
//    @ExcelColumnHeader(columnHeader = "Manco")
//    private String manco;
//
//    @Column(name = "mm_class")
//    @ExcelColumnHeader(columnHeader = "MMClass")
//    private String mmClass;
//
//    @Column(name = "instr_type")
//    @ExcelColumnHeader(columnHeader = "InstrType")
//    private String instrType;
//
//    @Column(name = "sub_instr_type")
//    @ExcelColumnHeader(columnHeader = "subInstrType")
//    private String subInstrType;
//
//    @Column(name = "fpm_instrument_type")
//    @ExcelColumnHeader(columnHeader = "FPMInstrumentType")
//    private String fpmInstrumentType;
//
//    @Column(name = "sec_name")
//    @ExcelColumnHeader(columnHeader = "sec_name")
//    private String secName;
//
//    @Column(name = "currency")
//    @ExcelColumnHeader(columnHeader = "Currency")
//    private String currency;
//
//    @Column(name = "instr_id")
//    @ExcelColumnHeader(columnHeader = "InstrID")
//    private String instrId;
//
//    @Column(name = "isin")
//    @ExcelColumnHeader(columnHeader = "ISIN")
//    private String isin;
//
//    @Column(name = "instr_trade_type")
//    @ExcelColumnHeader(columnHeader = "InstrTradeType")
//    private BigDecimal instrTradeType;
//
//    @Column(name = "issuer_code")
//    @ExcelColumnHeader(columnHeader = "IssuerCode")
//    private String issueCode;
//
//    @Column(name = "issuer_name")
//    @ExcelColumnHeader(columnHeader = "IssuerName")
//    private String issuerName;
//
//    @Column(name = "yield_type")
//    @ExcelColumnHeader(columnHeader = "YieldType")
//    private String yieldType;
//
//    @Column(name = "interest_rate")
//    @ExcelColumnHeader(columnHeader = "InterestRate")
//    private BigDecimal interestRate;
//
//    @Column(name = "linked_index")
//    @ExcelColumnHeader(columnHeader = "LinkedIndex")
//    private String linkedIndex;
//
//    @Column(name = "spread")
//    @ExcelColumnHeader(columnHeader = "Spread")
//    private String spread;
//
//    @Column(name = "inflation_index")
//    @ExcelColumnHeader(columnHeader = "InflationIndex")
//    private String inflationIndex;
//
//    @Column(name = "inflation_lag")
//    @ExcelColumnHeader(columnHeader = "InflationLag")
//    private String inflationLag;
//
//    @Column(name = "inflation_base_value")
//    @ExcelColumnHeader(columnHeader = "InflationBaseValue")
//    private BigDecimal inflationBaseValue;
//
//    @Column(name = "date_issue")
//    @ExcelColumnHeader(columnHeader = "Date_Issue")
//    private String dateIssue;
//
//    @Column(name = "nominal")
//    @ExcelColumnHeader(columnHeader = "Nominal")
//    private BigDecimal nominal;
//
//    @Column(name = "date_maturity")
//    @ExcelColumnHeader(columnHeader = "Date_Maturity")
//    private String dateMaturity;
//
//    @Column(name = "maturity_value")
//    @ExcelColumnHeader(columnHeader = "MaturityValue")
//    private BigDecimal maturityValue;
//
//    @Column(name = "interest_freq")
//    @ExcelColumnHeader(columnHeader = "InterestFreq")
//    private String interestFreq;
//
//    @Column(name = "dates_book_close")
//    @ExcelColumnHeader(columnHeader = "Dates_BookClose")
//    private String datesBookClose;
//
//    @Column(name = "dates_coupon_payment")
//    @ExcelColumnHeader(columnHeader = "Dates_CouponPayment")
//    private String datesCouponPayment;
//
//    @Column(name = "dates_reset")
//    @ExcelColumnHeader(columnHeader = "Dates_Reset")
//    private String datesReset;
//
//    @Column(name = "date_first_payment")
//    @ExcelColumnHeader(columnHeader = "Date_FirstPayment")
//    private String dateFirstPayment;
//
//    @Column(name = "date_verified")
//    @ExcelColumnHeader(columnHeader = "Date_Verified")
//    private String dateVerified;
//
//    @Column(name = "verified_by")
//    @ExcelColumnHeader(columnHeader = "VerifiedBy")
//    private String verifiedBy;
//
//    @Column(name = "date_fpm_static")
//    @ExcelColumnHeader(columnHeader = "Date_FPMStatic")
//    private String dateFPMStatic;
//
//    @Column(name = "inflation_index_id")
//    @ExcelColumnHeader(columnHeader = "InflationIndexID")
//    private String inflationIndexID;
//
//    @Column(name = "linked_index_id")
//    @ExcelColumnHeader(columnHeader = "LinkedIndexID")
//    private String linkedIndexID;
//
//    @Column(name = "yield_type_id")
//    @ExcelColumnHeader(columnHeader = "YieldTypeID")
//    private String yieldTypeID;
//
//    @Column(name = "issuer_id")
//    @ExcelColumnHeader(columnHeader = "IssuerID")
//    private String issuerID;
//
//    @Column(name = "mkt_seccode")
//    @ExcelColumnHeader(columnHeader = "mktSeccode")
//    private String mktSeccode;
//
//    @Column(name = "fpm_instrument_type_id")
//    @ExcelColumnHeader(columnHeader = "FPMInstrumentTypeID")
//    private String fpmInstrumentTypeID;
//
//    @Column(name = "instr_trade_type_id")
//    @ExcelColumnHeader(columnHeader = "InstrTradeTypeID")
//    private String instrTradeTypeID;
//
//    @Column(name = "sub_instr_type_id")
//    @ExcelColumnHeader(columnHeader = "subInstrTypeID")
//    private String subInstrTypeID;
//
//    @Column(name = "mm_class_id")
//    @ExcelColumnHeader(columnHeader = "MMClassID")
//    private String mMClassID;
//
//    @Column(name = "manco_id")
//    @ExcelColumnHeader(columnHeader = "MancoID")
//    private String mancoID;
//
//    @Column(name = "user_name")
//    @ExcelColumnHeader(columnHeader = "UserName")
//    private String userName;
//
//    @Column(name = "mod_following")
//    @ExcelColumnHeader(columnHeader = "modFollowing")
//    private String modFollowing;*/
//
//    @Column(name = "client_portfolio_code")
//    @ExcelColumnHeader(columnHeader = "ClientPortfolioCode")
//    private String clientPortfolioCode;
//
//    @Column(name = "instrument_code")
//    @ExcelColumnHeader(columnHeader = "InstrumentCode")
//    private String instrumentCode;
//
//    @Column(name = "trade_date")
//    @ExcelColumnHeader(columnHeader = "TradeDate", dataType = "date")
//    private Date tradeDate;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getClientPortfolioCode() {
//        return clientPortfolioCode;
//    }
//
//    public void setClientPortfolioCode(String clientPortfolioCode) {
//        this.clientPortfolioCode = clientPortfolioCode;
//    }
//
//    public String getInstrumentCode() {
//        return instrumentCode;
//    }
//
//    public void setInstrumentCode(String instrumentCode) {
//        this.instrumentCode = instrumentCode;
//    }
//
//    public Date getTradeDate() {
//        return tradeDate;
//    }
//
//    public void setTradeDate(Date tradeDate) {
//        this.tradeDate = tradeDate;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        TransactionListing that = (TransactionListing) o;
//        return Objects.equals(clientPortfolioCode, that.clientPortfolioCode) &&
//                Objects.equals(instrumentCode, that.instrumentCode) &&
//                Objects.equals(tradeDate, that.tradeDate);
//    }
//
//    @Override
//    public int hashCode() {
//
//        return Objects.hash(clientPortfolioCode, instrumentCode, tradeDate);
//    }
//}

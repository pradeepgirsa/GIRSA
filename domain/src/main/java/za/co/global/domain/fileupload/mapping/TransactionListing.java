package za.co.global.domain.fileupload.mapping;

import com.gizbel.excel.annotations.ExcelBean;
import com.gizbel.excel.annotations.ExcelColumnHeader;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "transaction_listing")
@ExcelBean
public class TransactionListing implements Serializable {

    private static final long serialVersionUID = 3536471384821187632L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sec_code", unique = true, nullable = false)
    @ExcelColumnHeader(columnHeader = "sec_code")
    private String secCode;

    @Column(name = "manco")
    @ExcelColumnHeader(columnHeader = "Manco")
    private String manco;

    @Column(name = "mm_class")
    @ExcelColumnHeader(columnHeader = "MMClass")
    private String mmClass;

    @Column(name = "instr_type")
    @ExcelColumnHeader(columnHeader = "InstrType")
    private String instrType;

    @Column(name = "sub_instr_type")
    @ExcelColumnHeader(columnHeader = "subInstrType")
    private String subInstrType;

    @Column(name = "fpm_instrument_type")
    @ExcelColumnHeader(columnHeader = "FPMInstrumentType")
    private String fpmInstrumentType;

    @Column(name = "sec_name")
    @ExcelColumnHeader(columnHeader = "sec_name")
    private String secName;

    @Column(name = "currency")
    @ExcelColumnHeader(columnHeader = "Currency")
    private String currency;

    @Column(name = "instr_id")
    @ExcelColumnHeader(columnHeader = "InstrID")
    private String instrId;

    @Column(name = "isin")
    @ExcelColumnHeader(columnHeader = "ISIN")
    private String isin;

    @Column(name = "instr_trade_type")
    @ExcelColumnHeader(columnHeader = "InstrTradeType")
    private BigDecimal instrTradeType;

    @Column(name = "issuer_code")
    @ExcelColumnHeader(columnHeader = "IssuerCode")
    private String issueCode;

    @Column(name = "issuer_name")
    @ExcelColumnHeader(columnHeader = "IssuerName")
    private String issuerName;

    @Column(name = "yield_type")
    @ExcelColumnHeader(columnHeader = "YieldType")
    private String yieldType;

    @Column(name = "interest_rate")
    @ExcelColumnHeader(columnHeader = "InterestRate")
    private BigDecimal interestRate;

    @Column(name = "linked_index")
    @ExcelColumnHeader(columnHeader = "LinkedIndex")
    private String linkedIndex;

    @Column(name = "spread")
    @ExcelColumnHeader(columnHeader = "Spread")
    private String spread;

    @Column(name = "inflation_index")
    @ExcelColumnHeader(columnHeader = "InflationIndex")
    private String inflationIndex;

    @Column(name = "inflation_lag")
    @ExcelColumnHeader(columnHeader = "InflationLag")
    private String inflationLag;

    @Column(name = "inflation_base_value")
    @ExcelColumnHeader(columnHeader = "InflationBaseValue")
    private BigDecimal inflationBaseValue;

    @Column(name = "date_issue")
    @ExcelColumnHeader(columnHeader = "Date_Issue")
    private String dateIssue;

    @Column(name = "nominal")
    @ExcelColumnHeader(columnHeader = "Nominal")
    private BigDecimal nominal;

    @Column(name = "date_maturity")
    @ExcelColumnHeader(columnHeader = "Date_Maturity")
    private String dateMaturity;

    @Column(name = "maturity_value")
    @ExcelColumnHeader(columnHeader = "MaturityValue")
    private BigDecimal maturityValue;

    @Column(name = "interest_freq")
    @ExcelColumnHeader(columnHeader = "InterestFreq")
    private String interestFreq;

    @Column(name = "dates_book_close")
    @ExcelColumnHeader(columnHeader = "Dates_BookClose")
    private String datesBookClose;

    @Column(name = "dates_coupon_payment")
    @ExcelColumnHeader(columnHeader = "Dates_CouponPayment")
    private String datesCouponPayment;

    @Column(name = "dates_reset")
    @ExcelColumnHeader(columnHeader = "Dates_Reset")
    private String datesReset;

    @Column(name = "date_first_payment")
    @ExcelColumnHeader(columnHeader = "Date_FirstPayment")
    private String dateFirstPayment;

    @Column(name = "date_verified")
    @ExcelColumnHeader(columnHeader = "Date_Verified")
    private String dateVerified;

    @Column(name = "verified_by")
    @ExcelColumnHeader(columnHeader = "VerifiedBy")
    private String verifiedBy;

    @Column(name = "date_fpm_static")
    @ExcelColumnHeader(columnHeader = "Date_FPMStatic")
    private String dateFPMStatic;

    @Column(name = "inflation_index_id")
    @ExcelColumnHeader(columnHeader = "InflationIndexID")
    private String inflationIndexID;

    @Column(name = "linked_index_id")
    @ExcelColumnHeader(columnHeader = "LinkedIndexID")
    private String linkedIndexID;

    @Column(name = "yield_type_id")
    @ExcelColumnHeader(columnHeader = "YieldTypeID")
    private String yieldTypeID;

    @Column(name = "issuer_id")
    @ExcelColumnHeader(columnHeader = "IssuerID")
    private String issuerID;

    @Column(name = "mkt_seccode")
    @ExcelColumnHeader(columnHeader = "mktSeccode")
    private String mktSeccode;

    @Column(name = "fpm_instrument_type_id")
    @ExcelColumnHeader(columnHeader = "FPMInstrumentTypeID")
    private String fpmInstrumentTypeID;

    @Column(name = "instr_trade_type_id")
    @ExcelColumnHeader(columnHeader = "InstrTradeTypeID")
    private String instrTradeTypeID;

    @Column(name = "sub_instr_type_id")
    @ExcelColumnHeader(columnHeader = "subInstrTypeID")
    private String subInstrTypeID;

    @Column(name = "mm_class_id")
    @ExcelColumnHeader(columnHeader = "MMClassID")
    private String mMClassID;

    @Column(name = "manco_id")
    @ExcelColumnHeader(columnHeader = "MancoID")
    private String mancoID;

    @Column(name = "user_name")
    @ExcelColumnHeader(columnHeader = "UserName")
    private String userName;

    @Column(name = "mod_following")
    @ExcelColumnHeader(columnHeader = "modFollowing")
    private String modFollowing;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecCode() {
        return secCode;
    }

    public void setSecCode(String secCode) {
        this.secCode = secCode;
    }

    public String getManco() {
        return manco;
    }

    public void setManco(String manco) {
        this.manco = manco;
    }

    public String getMmClass() {
        return mmClass;
    }

    public void setMmClass(String mmClass) {
        this.mmClass = mmClass;
    }

    public String getInstrType() {
        return instrType;
    }

    public void setInstrType(String instrType) {
        this.instrType = instrType;
    }

    public String getSubInstrType() {
        return subInstrType;
    }

    public void setSubInstrType(String subInstrType) {
        this.subInstrType = subInstrType;
    }

    public String getFpmInstrumentType() {
        return fpmInstrumentType;
    }

    public void setFpmInstrumentType(String fpmInstrumentType) {
        this.fpmInstrumentType = fpmInstrumentType;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getInstrId() {
        return instrId;
    }

    public void setInstrId(String instrId) {
        this.instrId = instrId;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public BigDecimal getInstrTradeType() {
        return instrTradeType;
    }

    public void setInstrTradeType(BigDecimal instrTradeType) {
        this.instrTradeType = instrTradeType;
    }

    public String getIssueCode() {
        return issueCode;
    }

    public void setIssueCode(String issueCode) {
        this.issueCode = issueCode;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public String getYieldType() {
        return yieldType;
    }

    public void setYieldType(String yieldType) {
        this.yieldType = yieldType;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public String getLinkedIndex() {
        return linkedIndex;
    }

    public void setLinkedIndex(String linkedIndex) {
        this.linkedIndex = linkedIndex;
    }

    public String getSpread() {
        return spread;
    }

    public void setSpread(String spread) {
        this.spread = spread;
    }

    public String getInflationIndex() {
        return inflationIndex;
    }

    public void setInflationIndex(String inflationIndex) {
        this.inflationIndex = inflationIndex;
    }

    public String getInflationLag() {
        return inflationLag;
    }

    public void setInflationLag(String inflationLag) {
        this.inflationLag = inflationLag;
    }

    public BigDecimal getInflationBaseValue() {
        return inflationBaseValue;
    }

    public void setInflationBaseValue(BigDecimal inflationBaseValue) {
        this.inflationBaseValue = inflationBaseValue;
    }

    public String getDateIssue() {
        return dateIssue;
    }

    public void setDateIssue(String dateIssue) {
        this.dateIssue = dateIssue;
    }

    public BigDecimal getNominal() {
        return nominal;
    }

    public void setNominal(BigDecimal nominal) {
        this.nominal = nominal;
    }

    public String getDateMaturity() {
        return dateMaturity;
    }

    public void setDateMaturity(String dateMaturity) {
        this.dateMaturity = dateMaturity;
    }

    public BigDecimal getMaturityValue() {
        return maturityValue;
    }

    public void setMaturityValue(BigDecimal maturityValue) {
        this.maturityValue = maturityValue;
    }

    public String getInterestFreq() {
        return interestFreq;
    }

    public void setInterestFreq(String interestFreq) {
        this.interestFreq = interestFreq;
    }

    public String getDatesBookClose() {
        return datesBookClose;
    }

    public void setDatesBookClose(String datesBookClose) {
        this.datesBookClose = datesBookClose;
    }

    public String getDatesCouponPayment() {
        return datesCouponPayment;
    }

    public void setDatesCouponPayment(String datesCouponPayment) {
        this.datesCouponPayment = datesCouponPayment;
    }

    public String getDatesReset() {
        return datesReset;
    }

    public void setDatesReset(String datesReset) {
        this.datesReset = datesReset;
    }

    public String getDateFirstPayment() {
        return dateFirstPayment;
    }

    public void setDateFirstPayment(String dateFirstPayment) {
        this.dateFirstPayment = dateFirstPayment;
    }

    public String getDateVerified() {
        return dateVerified;
    }

    public void setDateVerified(String dateVerified) {
        this.dateVerified = dateVerified;
    }

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public String getDateFPMStatic() {
        return dateFPMStatic;
    }

    public void setDateFPMStatic(String dateFPMStatic) {
        this.dateFPMStatic = dateFPMStatic;
    }

    public String getInflationIndexID() {
        return inflationIndexID;
    }

    public void setInflationIndexID(String inflationIndexID) {
        this.inflationIndexID = inflationIndexID;
    }

    public String getLinkedIndexID() {
        return linkedIndexID;
    }

    public void setLinkedIndexID(String linkedIndexID) {
        this.linkedIndexID = linkedIndexID;
    }

    public String getYieldTypeID() {
        return yieldTypeID;
    }

    public void setYieldTypeID(String yieldTypeID) {
        this.yieldTypeID = yieldTypeID;
    }

    public String getIssuerID() {
        return issuerID;
    }

    public void setIssuerID(String issuerID) {
        this.issuerID = issuerID;
    }

    public String getMktSeccode() {
        return mktSeccode;
    }

    public void setMktSeccode(String mktSeccode) {
        this.mktSeccode = mktSeccode;
    }

    public String getFpmInstrumentTypeID() {
        return fpmInstrumentTypeID;
    }

    public void setFpmInstrumentTypeID(String fpmInstrumentTypeID) {
        this.fpmInstrumentTypeID = fpmInstrumentTypeID;
    }

    public String getInstrTradeTypeID() {
        return instrTradeTypeID;
    }

    public void setInstrTradeTypeID(String instrTradeTypeID) {
        this.instrTradeTypeID = instrTradeTypeID;
    }

    public String getSubInstrTypeID() {
        return subInstrTypeID;
    }

    public void setSubInstrTypeID(String subInstrTypeID) {
        this.subInstrTypeID = subInstrTypeID;
    }

    public String getmMClassID() {
        return mMClassID;
    }

    public void setmMClassID(String mMClassID) {
        this.mMClassID = mMClassID;
    }

    public String getMancoID() {
        return mancoID;
    }

    public void setMancoID(String mancoID) {
        this.mancoID = mancoID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getModFollowing() {
        return modFollowing;
    }

    public void setModFollowing(String modFollowing) {
        this.modFollowing = modFollowing;
    }
}

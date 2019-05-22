package za.co.global.domain.fileupload.system;

import com.gizbel.excel.annotations.ExcelBean;
import com.gizbel.excel.annotations.ExcelColumnHeader;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by root on 2019/05/22.
 */
@ExcelBean
@Entity
@Table(name = "barra_asset_dsu4")
public class AssetDSU4 implements Serializable {

    private static final long serialVersionUID = 5241642100913184632L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ExcelColumnHeader(columnHeader = "Asset ID")
    @Column(name = "ASSET_ID")
    private String assetId;

    @ExcelColumnHeader(columnHeader = "Asset ID Type")
    @Column(name = "ASSET_ID_TYPE")
    private String assetIdType;

    @ExcelColumnHeader(columnHeader = "Asset Name")
    @Column(name = "ASSET_NAME")
    private String assetName;

    @ExcelColumnHeader(columnHeader = "Holdings", dataType = "big_decimal")
    @Column(name = "HOLDINGS")
    private BigDecimal holdings;

    @ExcelColumnHeader(columnHeader = "Price", dataType = "big_decimal")
    @Column(name = "PRICE")
    private BigDecimal price;

    @ExcelColumnHeader(columnHeader = "Eff Exposure", dataType = "big_decimal")
    @Column(name = "EFF_EXPOSURE")
    private BigDecimal effExposure;

    @ExcelColumnHeader(columnHeader = "Mkt Value", dataType = "big_decimal")
    @Column(name = "MARKET_VALUE")
    private BigDecimal marketValue;

    @ExcelColumnHeader(columnHeader = "Inst. Type")
    @Column(name = "INST_TYPE")
    private String instType;

    @ExcelColumnHeader(columnHeader = "Inst. Sub-type")
    @Column(name = "INST_SUB_TYPE")
    private String instSubType;

    @ExcelColumnHeader(columnHeader = "Eff Weight (%)", dataType = "big_decimal")
    @Column(name = "EFF_WEIGHT")
    private BigDecimal effWeight;

    @ExcelColumnHeader(columnHeader = "GIR Issuer")
    @Column(name = "GIR_ISSUER")
    private String girIssuer;

    @ExcelColumnHeader(columnHeader = "Reg28_InstrType")
    @Column(name = "REG28INSTR_TYPE")
    private String reg28InstrType;

    @ExcelColumnHeader(columnHeader = "SARB Classification")
    @Column(name = "SARB_CLASSIFICATION")
    private String sarbClassification;

    @ExcelColumnHeader(columnHeader = "Africa Values", dataType = "big_decimal")
    @Column(name = "AFRICA_VALUES")
    private BigDecimal africaValues;

    @ExcelColumnHeader(columnHeader = "Local Market")
    @Column(name = "LOCAL_MARKET")
    private String localMarket;

    @ExcelColumnHeader(columnHeader = "Price Currency")
    @Column(name = "PRICE_CURRENCY")
    private String priceCurrency;

    @Column(name = "NET_INDICATOR")
    private boolean netIndicator = Boolean.FALSE.booleanValue();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getAssetIdType() {
        return assetIdType;
    }

    public void setAssetIdType(String assetIdType) {
        this.assetIdType = assetIdType;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public BigDecimal getHoldings() {
        return holdings;
    }

    public void setHoldings(BigDecimal holdings) {
        this.holdings = holdings;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getEffExposure() {
        return effExposure;
    }

    public void setEffExposure(BigDecimal effExposure) {
        this.effExposure = effExposure;
    }

    public BigDecimal getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(BigDecimal marketValue) {
        this.marketValue = marketValue;
    }

    public String getInstType() {
        return instType;
    }

    public void setInstType(String instType) {
        this.instType = instType;
    }

    public String getInstSubType() {
        return instSubType;
    }

    public void setInstSubType(String instSubType) {
        this.instSubType = instSubType;
    }

    public BigDecimal getEffWeight() {
        return effWeight;
    }

    public void setEffWeight(BigDecimal effWeight) {
        this.effWeight = effWeight;
    }

    public String getGirIssuer() {
        return girIssuer;
    }

    public void setGirIssuer(String girIssuer) {
        this.girIssuer = girIssuer;
    }

    public String getReg28InstrType() {
        return reg28InstrType;
    }

    public void setReg28InstrType(String reg28InstrType) {
        this.reg28InstrType = reg28InstrType;
    }

    public String getSarbClassification() {
        return sarbClassification;
    }

    public void setSarbClassification(String sarbClassification) {
        this.sarbClassification = sarbClassification;
    }

    public BigDecimal getAfricaValues() {
        return africaValues;
    }

    public void setAfricaValues(BigDecimal africaValues) {
        this.africaValues = africaValues;
    }

    public String getLocalMarket() {
        return localMarket;
    }

    public void setLocalMarket(String localMarket) {
        this.localMarket = localMarket;
    }

    public boolean isNetIndicator() {
        return netIndicator;
    }

    public void setNetIndicator(boolean netIndicator) {
        this.netIndicator = netIndicator;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssetDSU4 assetDSU4 = (AssetDSU4) o;

        if (!id.equals(assetDSU4.id)) return false;
        if (!assetId.equals(assetDSU4.assetId)) return false;
        if (!assetIdType.equals(assetDSU4.assetIdType)) return false;
        if (!assetName.equals(assetDSU4.assetName)) return false;
        if (!holdings.equals(assetDSU4.holdings)) return false;
        if (!price.equals(assetDSU4.price)) return false;
        if (!effExposure.equals(assetDSU4.effExposure)) return false;
        if (!marketValue.equals(assetDSU4.marketValue)) return false;
        if (!instType.equals(assetDSU4.instType)) return false;
        if (!instSubType.equals(assetDSU4.instSubType)) return false;
        if (!effWeight.equals(assetDSU4.effWeight)) return false;
        if (!girIssuer.equals(assetDSU4.girIssuer)) return false;
        if (!reg28InstrType.equals(assetDSU4.reg28InstrType)) return false;
        if (!sarbClassification.equals(assetDSU4.sarbClassification)) return false;
        if (!africaValues.equals(assetDSU4.africaValues)) return false;
        if (!localMarket.equals(assetDSU4.localMarket)) return false;
        return priceCurrency.equals(assetDSU4.priceCurrency);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + assetId.hashCode();
        result = 31 * result + assetIdType.hashCode();
        result = 31 * result + assetName.hashCode();
        result = 31 * result + holdings.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + effExposure.hashCode();
        result = 31 * result + marketValue.hashCode();
        result = 31 * result + instType.hashCode();
        result = 31 * result + instSubType.hashCode();
        result = 31 * result + effWeight.hashCode();
        result = 31 * result + girIssuer.hashCode();
        result = 31 * result + reg28InstrType.hashCode();
        result = 31 * result + sarbClassification.hashCode();
        result = 31 * result + africaValues.hashCode();
        result = 31 * result + localMarket.hashCode();
        result = 31 * result + priceCurrency.hashCode();
        return result;
    }
}

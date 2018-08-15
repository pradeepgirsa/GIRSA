package za.co.global.domain.upload;

import com.gizbel.excel.annotations.ExcelBean;
import com.gizbel.excel.annotations.ExcelColumnHeader;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@ExcelBean
@Entity
public class DSU5_GIRREP4 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ExcelColumnHeader(columnHeader = "Asset ID", dataType = "string")
    private String assetId;

    @ExcelColumnHeader(columnHeader = "Asset ID Type")
    private String assetIdType;

    @ExcelColumnHeader(columnHeader = "Asset Name")
    private String assetName;

    @ExcelColumnHeader(columnHeader = "Holdings", dataType = "string")
    private String holdings;

    @ExcelColumnHeader(columnHeader = "Price", dataType = "string")
    private String price;

    @ExcelColumnHeader(columnHeader = "Eff Exposure", dataType = "string")
    private String effExposure;

    @ExcelColumnHeader(columnHeader = "Inst. Type", dataType = "string")
    private String instType;

    @ExcelColumnHeader(columnHeader = "Inst. Sub-type", dataType = "string")
    private String instSubType;

    @ExcelColumnHeader(columnHeader = "Eff Weight (%)", dataType = "string")
    private String effWeight;

    @ExcelColumnHeader(columnHeader = "GIR Issuer", dataType = "string")
    private String girIssuer;

    @ExcelColumnHeader(columnHeader = "Local Market", dataType = "string")
    private String localMarket;

    @ExcelColumnHeader(columnHeader = "Market Capitalization", dataType = "string")
    private String marketCapitalization;

    @ExcelColumnHeader(columnHeader = "ICB Industry", dataType = "string")
    private String icbIndustry;

    @ExcelColumnHeader(columnHeader = "ICB Supersector", dataType = "string")
    private String icbSuperSector;

    @ExcelColumnHeader(columnHeader = "Reg28_InstrType", dataType = "string")
    private String reg28InstrType;

    @ExcelColumnHeader(columnHeader = "Africa Values", dataType = "string")
    private String africaValues;

    /*@ExcelColumnHeader(columnHeader = "SARB Classification", dataType = "string")
    private String price;

    @ExcelColumnHeader(columnHeader = "Price", dataType = "string")
    private String price;

    @ExcelColumnHeader(columnHeader = "Price", dataType = "string")
    private String price;

    @ExcelColumnHeader(columnHeader = "Price", dataType = "string")
    private String price;

    @ExcelColumnHeader(columnHeader = "Price", dataType = "string")
    private String price;*/













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

    public String getHoldings() {
        return holdings;
    }

    public void setHoldings(String holdings) {
        this.holdings = holdings;
    }
}

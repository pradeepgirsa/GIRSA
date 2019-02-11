package za.co.global.domain.report;

import za.co.global.domain.fileupload.client.DailyPricing;
import za.co.global.domain.fileupload.client.InstrumentData;
import za.co.global.domain.fileupload.mapping.ClientFundMapping;
import za.co.global.domain.fileupload.mapping.InstrumentCode;
import za.co.global.domain.fileupload.mapping.IssuerMapping;
import za.co.global.domain.fileupload.mapping.Reg28InstrumentType;
import za.co.global.domain.fileupload.system.BarraAssetInfo;

import java.math.BigDecimal;
import java.util.Date;

public class ReportDataCollectionBean {

    private BarraAssetInfo netAsset;
//    private InstitutionalDetails institutionalDetails;
    private BarraAssetInfo barraAssetInfo;
    private InstrumentCode instrumentCode;
    private ClientFundMapping clientFundMapping;
//    private NumberOfAccounts numberOfAccounts;
    private Date maturityDate;
    private Reg28InstrumentType reg28InstrumentType;
    private IssuerMapping issuerMapping;
    private DailyPricing dailyPricing;
    private InstrumentData instrumentData;
    private Date settlementDate;
    private BigDecimal netCurrentMarketValue;

    public static class Builder {
        private BarraAssetInfo netAsset;
        private InstrumentData instrumentData;
//        private InstitutionalDetails institutionalDetails;
        private BarraAssetInfo barraAssetInfo;
        private InstrumentCode instrumentCode;
        private ClientFundMapping clientFundMapping;
//        private NumberOfAccounts numberOfAccounts;
        private Date maturityDate;
        private Reg28InstrumentType reg28InstrumentType;
        private IssuerMapping issuerMapping;
        private DailyPricing dailyPricing;
        private Date settlementDate;
        private BigDecimal netCurrentMarketValue;

        public Builder setNetAsset(BarraAssetInfo netAsset) {
            this.netAsset = netAsset;
            return this;
        }

        public Builder setInstrumentData(InstrumentData instrumentData) {
            this.instrumentData = instrumentData;
            return this;
        }

//        public Builder setInstitutionalDetails(InstitutionalDetails institutionalDetails) {
//            this.institutionalDetails = institutionalDetails;
//            return this;
//        }

        public Builder setBarraAssetInfo(BarraAssetInfo barraAssetInfo) {
            this.barraAssetInfo = barraAssetInfo;
            return this;
        }

        public Builder setInstrumentCode(InstrumentCode instrumentCode) {
            this.instrumentCode = instrumentCode;
            return this;
        }

        public Builder setClientFundMapping(ClientFundMapping clientFundMapping) {
            this.clientFundMapping = clientFundMapping;
            return this;
        }
//
//        public Builder setNumberOfAccounts(NumberOfAccounts numberOfAccounts) {
//            this.numberOfAccounts = numberOfAccounts;
//            return this;
//        }

        public Builder setMaturityDate(Date maturityDate) {
            this.maturityDate = maturityDate;
            return this;
        }

        public Builder setReg28InstrumentType(Reg28InstrumentType reg28InstrumentType) {
            this.reg28InstrumentType = reg28InstrumentType;
            return this;
        }

        public Builder setIssuerMapping(IssuerMapping issuerMapping) {
            this.issuerMapping = issuerMapping;
            return this;
        }

        public Builder setDailyPricing(DailyPricing dailyPricing) {
            this.dailyPricing = dailyPricing;
            return this;
        }

        public Builder setSettlementDate(Date settlementDate) {
            this.settlementDate = settlementDate;
            return this;
        }

        public Builder setNetCurrentMarketValue(BigDecimal netCurrentMarketValue) {
            this.netCurrentMarketValue = netCurrentMarketValue;
            return this;
        }


        public ReportDataCollectionBean build() {
            return new ReportDataCollectionBean(this);
        }
    }


    ReportDataCollectionBean() {};

    ReportDataCollectionBean(Builder builder) {
        this.netAsset = builder.netAsset;
        this.barraAssetInfo = builder.barraAssetInfo;
        this.maturityDate = builder.maturityDate;
//        this.institutionalDetails = builder.institutionalDetails;
        this.instrumentData = builder.instrumentData;
        this.clientFundMapping = builder.clientFundMapping;
//        this.numberOfAccounts = builder.numberOfAccounts;
        this.instrumentCode = builder.instrumentCode;
        this.reg28InstrumentType = builder.reg28InstrumentType;
        this.issuerMapping = builder.issuerMapping;
        this.dailyPricing = builder.dailyPricing;
        this.settlementDate = builder.settlementDate;
        this.netCurrentMarketValue = builder.netCurrentMarketValue;
    }

    public BarraAssetInfo getNetAsset() {
        return netAsset;
    }

    public InstrumentData getInstrumentData() {
        return instrumentData;
    }

//    public InstitutionalDetails getInstitutionalDetails() {
//        return institutionalDetails;
//    }

    public BarraAssetInfo getBarraAssetInfo() {
        return barraAssetInfo;
    }

    public InstrumentCode getInstrumentCode() {
        return instrumentCode;
    }

    public ClientFundMapping getClientFundMapping() {
        return clientFundMapping;
    }

//    public NumberOfAccounts getNumberOfAccounts() {
//        return numberOfAccounts;
//    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public Reg28InstrumentType getReg28InstrumentType() {
        return reg28InstrumentType;
    }

    public IssuerMapping getIssuerMapping() {
        return issuerMapping;
    }

    public DailyPricing getDailyPricing() {
        return dailyPricing;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public BigDecimal getNetCurrentMarketValue() {
        return netCurrentMarketValue;
    }
}

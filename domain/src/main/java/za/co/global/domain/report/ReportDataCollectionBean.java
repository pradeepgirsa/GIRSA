package za.co.global.domain.report;

import za.co.global.domain.fileupload.client.InstitutionalDetails;
import za.co.global.domain.fileupload.client.NumberOfAccounts;
import za.co.global.domain.fileupload.client.fpm.Holding;
import za.co.global.domain.fileupload.client.fpm.Instrument;
import za.co.global.domain.fileupload.mapping.*;
import za.co.global.domain.fileupload.system.BarraAssetInfo;

import java.util.Date;

public class ReportDataCollectionBean {

    private BarraAssetInfo netAsset;
    private InstitutionalDetails institutionalDetails;
    private BarraAssetInfo barraAssetInfo;
    private InstrumentCode instrumentCode;
    private PSGFundMapping psgFundMapping;
    private NumberOfAccounts numberOfAccounts;
    private Date maturityDate;
    private Reg28InstrumentType reg28InstrumentType;
    private IssuerMapping issuerMapping;
    private DailyPricing dailyPricing;
    private Instrument instrument;

    public static class Builder {
        private BarraAssetInfo netAsset;
        private Instrument instrument;
        private InstitutionalDetails institutionalDetails;
        private BarraAssetInfo barraAssetInfo;
        private InstrumentCode instrumentCode;
        private PSGFundMapping psgFundMapping;
        private NumberOfAccounts numberOfAccounts;
        private Date maturityDate;
        private Reg28InstrumentType reg28InstrumentType;
        private IssuerMapping issuerMapping;
        private DailyPricing dailyPricing;

        public Builder setNetAsset(BarraAssetInfo netAsset) {
            this.netAsset = netAsset;
            return this;
        }

        public Builder setInstrument(Instrument instrument) {
            this.instrument = instrument;
            return this;
        }

        public Builder setInstitutionalDetails(InstitutionalDetails institutionalDetails) {
            this.institutionalDetails = institutionalDetails;
            return this;
        }

        public Builder setBarraAssetInfo(BarraAssetInfo barraAssetInfo) {
            this.barraAssetInfo = barraAssetInfo;
            return this;
        }

        public Builder setInstrumentCode(InstrumentCode instrumentCode) {
            this.instrumentCode = instrumentCode;
            return this;
        }

        public Builder setPsgFundMapping(PSGFundMapping psgFundMapping) {
            this.psgFundMapping = psgFundMapping;
            return this;
        }

        public Builder setNumberOfAccounts(NumberOfAccounts numberOfAccounts) {
            this.numberOfAccounts = numberOfAccounts;
            return this;
        }

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

        public ReportDataCollectionBean build() {
            return new ReportDataCollectionBean(this);
        }
    }


    ReportDataCollectionBean() {};

    ReportDataCollectionBean(Builder builder) {
        this.netAsset = builder.netAsset;
        this.barraAssetInfo = builder.barraAssetInfo;
        this.maturityDate = builder.maturityDate;
        this.institutionalDetails = builder.institutionalDetails;
        this.instrument = builder.instrument;
        this.psgFundMapping = builder.psgFundMapping;
        this.numberOfAccounts = builder.numberOfAccounts;
        this.instrumentCode = builder.instrumentCode;
        this.reg28InstrumentType = builder.reg28InstrumentType;
        this.issuerMapping = builder.issuerMapping;
        this.dailyPricing = builder.dailyPricing;
    }

    public BarraAssetInfo getNetAsset() {
        return netAsset;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public InstitutionalDetails getInstitutionalDetails() {
        return institutionalDetails;
    }

    public BarraAssetInfo getBarraAssetInfo() {
        return barraAssetInfo;
    }

    public InstrumentCode getInstrumentCode() {
        return instrumentCode;
    }

    public PSGFundMapping getPsgFundMapping() {
        return psgFundMapping;
    }

    public NumberOfAccounts getNumberOfAccounts() {
        return numberOfAccounts;
    }

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
}

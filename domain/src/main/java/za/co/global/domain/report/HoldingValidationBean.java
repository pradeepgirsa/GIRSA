package za.co.global.domain.report;

import za.co.global.domain.fileupload.client.InstitutionalDetails;
import za.co.global.domain.fileupload.client.NumberOfAccounts;
import za.co.global.domain.fileupload.client.fpm.Holding;
import za.co.global.domain.fileupload.mapping.InstrumentCode;
import za.co.global.domain.fileupload.mapping.IssuerMapping;
import za.co.global.domain.fileupload.mapping.PSGFundMapping;
import za.co.global.domain.fileupload.mapping.Reg28InstrumentType;
import za.co.global.domain.fileupload.system.BarraAssetInfo;

import java.util.Date;

public class HoldingValidationBean {

    private BarraAssetInfo netAsset;
    private Holding holding;
    private InstitutionalDetails institutionalDetails;
    private BarraAssetInfo barraAssetInfo;
    private InstrumentCode instrumentCode;
    private PSGFundMapping psgFundMapping;
    private NumberOfAccounts numberOfAccounts;
    private Date maturityDate;
    private Reg28InstrumentType reg28InstrumentType;
    private IssuerMapping issuerMapping;

    public static class Builder {
        private BarraAssetInfo netAsset;
        private Holding holding;
        private InstitutionalDetails institutionalDetails;
        private BarraAssetInfo barraAssetInfo;
        private InstrumentCode instrumentCode;
        private PSGFundMapping psgFundMapping;
        private NumberOfAccounts numberOfAccounts;
        private Date maturityDate;
        private Reg28InstrumentType reg28InstrumentType;
        private IssuerMapping issuerMapping;

        public Builder setNetAsset(BarraAssetInfo netAsset) {
            this.netAsset = netAsset;
            return this;
        }

        public Builder setHolding(Holding holding) {
            this.holding = holding;
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

        public HoldingValidationBean build() {
            return new HoldingValidationBean(this);
        }
    }


    HoldingValidationBean() {};

    HoldingValidationBean(Builder builder) {
        this.netAsset = builder.netAsset;
        this.barraAssetInfo = builder.barraAssetInfo;
        this.maturityDate = builder.maturityDate;
        this.institutionalDetails = builder.institutionalDetails;
        this.holding = builder.holding;
        this.psgFundMapping = builder.psgFundMapping;
        this.numberOfAccounts = builder.numberOfAccounts;
        this.instrumentCode = builder.instrumentCode;
        this.reg28InstrumentType = builder.reg28InstrumentType;
        this.issuerMapping = builder.issuerMapping;
    }

    public BarraAssetInfo getNetAsset() {
        return netAsset;
    }

    public Holding getHolding() {
        return holding;
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
}

package za.co.global.services.upload;

import za.co.global.domain.fileupload.client.DailyPricing;
import za.co.global.domain.fileupload.client.Indices;
import za.co.global.domain.fileupload.mapping.ClientFundMapping;
import za.co.global.domain.fileupload.mapping.IssuerMapping;
import za.co.global.domain.fileupload.system.BarraAssetInfo;

public enum FileAndObjectResolver {

    BARRA_FILE("BARRA_FILE", BarraAssetInfo.class),
    ADDITIONAL_CLASSIFICATION("ADDITIONAL_CLASSIFICATION", za.co.global.domain.fileupload.mapping.AdditionalClassification.class),
    INSTRUMENT_CODE("INSTRUMENT_CODE", za.co.global.domain.fileupload.mapping.InstrumentCode.class),
    INSTRUMENT_DATA("INSTRUMENT_DATA", za.co.global.domain.fileupload.client.InstrumentData.class),
    ISSUER_MAPPINGS("ISSUER_MAPPINGS", IssuerMapping.class),
    CLIENT_FUND_MAPPING("CLIENT_FUND_MAPPING", ClientFundMapping.class),
//    INSTITUTIONAL_DETAILS("INSTITUTIONAL_DETAILS", za.co.global.domain.fileupload.client.InstitutionalDetails.class),
//    NUMBER_OF_ACCOUNTS("NUMBER_OF_ACCOUNTS", za.co.global.domain.fileupload.client.NumberOfAccounts.class),
//    SECUTIY_LISTING_CONTROLLER("SECUTIY_LISTING_CONTROLLER", za.co.global.domain.fileupload.client.SecurityListing.class),
    REG28_INSTR_TYPE("REG28_INSTR_TYPE", za.co.global.domain.fileupload.mapping.Reg28InstrumentType.class),
    INDICES("INDICES", Indices.class),
    DAILY_PRICING("DAILY_PRICING", DailyPricing.class),
//    TRANSACTION_LISTING("TRANSACTION_LISTING", TransactionListing.class),
    DERIVATIVE_TYPES("DERIVATIVE_TYPES", za.co.global.domain.fileupload.mapping.Reg28InstrumentType.class);
    //DSU5("GIR_L2", za.co.global.domain.upload.DSU5_GIRREP4.class);

    private String fileType;
    private Class clazz;

    FileAndObjectResolver(String fileType, Class clazz) {
        this.fileType = fileType;
        this.clazz = clazz;
    }

    public static Class getClazzFromFileType(String sheetName) {
        if (sheetName != null) {
            for (FileAndObjectResolver nameSpaceItem : FileAndObjectResolver.values()) {
                if (sheetName.equals(nameSpaceItem.getFileType())) {
                    return nameSpaceItem.getClazz();
                }
            }
        }
        return null;
    }

    public String getFileType() {
        return fileType;
    }

    public Class getClazz() {
        return clazz;
    }
}

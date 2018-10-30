package za.co.global.services.upload;

public enum FileAndObjectResolver {

    BARRA_FILE("BARRA_FILE", za.co.global.domain.fileupload.barra.BarraFile.class),
    ADDITIONAL_CLASSIFICATION("ADDITIONAL_CLASSIFICATION", za.co.global.domain.fileupload.mapping.AdditionalClassification.class),
    INSTRUMENT_CODE("INSTRUMENT_CODE", za.co.global.domain.fileupload.mapping.InstrumentCode.class),
    ISSUER_MAPPINGS("ISSUER_MAPPINGS", za.co.global.domain.fileupload.mapping.IssuerMappings.class),
    PSG_FUND_MAPPING("PSG_FUND_MAPPING", za.co.global.domain.fileupload.mapping.PSGFundMapping.class),
    INSTITUTIONAL_DETAILS("INSTITUTIONAL_DETAILS", za.co.global.domain.fileupload.client.InstitutionalDetails.class),
    NUMBER_OF_ACCOUNTS("NUMBER_OF_ACCOUNTS", za.co.global.domain.fileupload.client.NumberOfAccounts.class),
    SECUTIY_LISTING_CONTROLLER("SECUTIY_LISTING_CONTROLLER", za.co.global.domain.fileupload.client.SecurityListing.class),
    REG28_INSTR_TYPE("REG28_INSTR_TYPE", za.co.global.domain.fileupload.mapping.Reg28InstrumentType.class),
    INDICES("INDICES", za.co.global.domain.fileupload.mapping.Indices.class),
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

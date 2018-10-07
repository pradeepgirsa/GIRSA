package za.co.global.services.upload;

public enum FileAndObjectResolver {

    DSU5_GIRREP4("GIR_C4", za.co.global.domain.fileupload.barra.DSU5_GIRREP4.class),
    ADDITIONAL_CLASSIFICATION("ADDITIONAL_CLASSIFICATION", za.co.global.domain.fileupload.mapping.AdditionalClassification.class);
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

package za.co.global.services.upload;

public enum SheetAndObjectResolver {

    DSU5_GIRREP4("DSU5_GIRREP4", DSU5_GIRREP4.class);

    private String sheetName;
    private Class clazz;

    SheetAndObjectResolver(String sheetName, Class clazz) {
        this.sheetName = sheetName;
        this.clazz = clazz;
    }

    public static Class getClazzFromSheetName(String sheetName) {
        if (sheetName != null) {
            for (SheetAndObjectResolver nameSpaceItem : SheetAndObjectResolver.values()) {
                if (sheetName.equals(nameSpaceItem.getSheetName())) {
                    return nameSpaceItem.getClazz();
                }
            }
        }
        return null;
    }

    public String getSheetName() {
        return sheetName;
    }

    public Class getClazz() {
        return clazz;
    }
}

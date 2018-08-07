package za.co.global.services.upload;

import com.gizbel.excel.annotations.ExcelBean;
import com.gizbel.excel.annotations.ExcelColumnHeader;
import com.gizbel.excel.annotations.ExcelColumnIndex;
import com.gizbel.excel.enums.ExcelFactoryType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GirsaExcelParser {
    //    private Class clazz;
    private ExcelFactoryType excelFactoryType;
    private boolean skipHeader;
    //    private Map<String, Field> fieldsMap;
    private boolean breakAfterEmptyRow;

    public GirsaExcelParser(ExcelFactoryType excelFactoryType) throws Exception {
        this.excelFactoryType = excelFactoryType;
        this.breakAfterEmptyRow = true;
    }

    private void prepareColumnIndexBasedFieldMap(Field field, Map<String, Field> fieldsMap) {
        if (field.isAnnotationPresent(ExcelColumnIndex.class)) {
            field.setAccessible(true);
            ExcelColumnIndex column = (ExcelColumnIndex) field.getAnnotation(ExcelColumnIndex.class);
            String key = String.valueOf(column.columnIndex());
            fieldsMap.put(key, field);
        }

    }

    private void prepareColumnHeaderBasedFieldMap(Field field, Map<String, Field> fieldsMap) {
        if (field.isAnnotationPresent(ExcelColumnHeader.class)) {
            field.setAccessible(true);
            ExcelColumnHeader column = (ExcelColumnHeader) field.getAnnotation(ExcelColumnHeader.class);
            String key = column.columnHeader();
            fieldsMap.put(key, field);
        }

    }

    private String getDataTypeFor(Field field) {
        String dataType = null;
        switch (this.excelFactoryType) {
            case COLUMN_INDEX_BASED_EXTRACTION:
                ExcelColumnIndex indexColumn = (ExcelColumnIndex) field.getAnnotation(ExcelColumnIndex.class);
                dataType = indexColumn.dataType();
                break;
            case COLUMN_NAME_BASED_EXTRACTION:
                ExcelColumnHeader headerColumn = (ExcelColumnHeader) field.getAnnotation(ExcelColumnHeader.class);
                dataType = headerColumn.dataType();
        }

        return dataType;
    }

    private String getDefaultValueFor(Field field) {
        String defaultValue = null;
        switch (this.excelFactoryType) {
            case COLUMN_INDEX_BASED_EXTRACTION:
                ExcelColumnIndex indexColumn = (ExcelColumnIndex) field.getAnnotation(ExcelColumnIndex.class);
                defaultValue = indexColumn.defaultValue();
                break;
            case COLUMN_NAME_BASED_EXTRACTION:
                ExcelColumnHeader headerColumn = (ExcelColumnHeader) field.getAnnotation(ExcelColumnHeader.class);
                defaultValue = headerColumn.defaultValue();
        }

        return defaultValue;
    }

    public Map<String, List<Object>> parse(File file) throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, ParseException {

        Map<String, List<Object>> r = new HashMap<>();
        Workbook invoiceWorkbook = WorkbookFactory.create(file);
        for (int i = 0; i < invoiceWorkbook.getNumberOfSheets(); i++) {
            Sheet sheet = invoiceWorkbook.getSheetAt(i);

            Class clazz = SheetAndObjectResolver.getClazzFromSheetName(sheet.getSheetName());

            Map<String, Field> fieldsMap = new HashMap();

            if (clazz != null) {
                if (clazz.isAnnotationPresent(ExcelBean.class)) {
                    Field[] fields = clazz.getDeclaredFields();
                    Field[] var4 = fields;
                    int var5 = fields.length;

                    for (int var6 = 0; var6 < var5; ++var6) {
                        Field field = var4[var6];
                        switch (this.excelFactoryType) {
                            case COLUMN_INDEX_BASED_EXTRACTION:
                                this.prepareColumnIndexBasedFieldMap(field, fieldsMap);
                                break;
                            case COLUMN_NAME_BASED_EXTRACTION:
                                this.prepareColumnHeaderBasedFieldMap(field, fieldsMap);
                        }
                    }

                } else {
                    //throw new Exception("Provided class is not annotated with ExcelBean");
                }

                List<Object> result = new ArrayList();
                if (this.excelFactoryType == ExcelFactoryType.COLUMN_NAME_BASED_EXTRACTION) {
                    Row firstRow = sheet.getRow(0);
                    Iterator var6 = firstRow.iterator();

                    while (var6.hasNext()) {
                        Cell column = (Cell) var6.next();
                        Field field = (Field) fieldsMap.get(column.getStringCellValue());
                        if (field != null) {
                            fieldsMap.remove(column.getStringCellValue());
                            fieldsMap.put(String.valueOf(column.getColumnIndex()), field);
                        }
                    }
                }

                Iterator var9 = sheet.iterator();

                while (var9.hasNext()) {
                    Row row = (Row) var9.next();
                    if (this.excelFactoryType == ExcelFactoryType.COLUMN_INDEX_BASED_EXTRACTION) {
                        if (row.getRowNum() == 0 && this.skipHeader) {
                            continue;
                        }
                    } else if (this.excelFactoryType == ExcelFactoryType.COLUMN_NAME_BASED_EXTRACTION && row.getRowNum() == 0) {
                        continue;
                    }

                    if (!this.isEmptyRow(row)) {
                        Object beanObj = this.getBeanForARow(clazz, row, fieldsMap);
                        result.add(beanObj);
                    } else if (this.breakAfterEmptyRow) {
                        break;
                    }
                }

                r.put(sheet.getSheetName(), result);
            } //if close to check null on clazz
        } //for close

        return r;
    }

    public Object getBeanForARow(Class clazz, Row row, Map<String, Field> fieldsMap) throws InstantiationException, IllegalAccessException, IllegalArgumentException, ParseException {
        Object classObj = clazz.newInstance();

        for (int i = 0; i < row.getLastCellNum(); ++i) {
            Cell cell = row.getCell(i);
            if (cell != null) {
                if (cell.getCellType() == 0) {
                    if (DateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        cell.setCellType(1);
                        cell.setCellValue((new SimpleDateFormat("dd-MM-yyyy")).format(date));
                    } else {
                        cell.setCellType(1);
                    }
                } else {
                    cell.setCellType(1);
                }

                String value = cell.getStringCellValue() == null ? null : cell.getStringCellValue().trim();
                this.setCellValueBasedOnDesiredExcelFactoryType(classObj, fieldsMap, value, i);
            } else {
                this.setCellValueBasedOnDesiredExcelFactoryType(classObj, fieldsMap, (String) null, i);
            }
        }

        return classObj;
    }

    private void setCellValueBasedOnDesiredExcelFactoryType(Object classObj, Map<String, Field> fieldsMap, String columnValue, int columnIndex) throws IllegalArgumentException, IllegalAccessException, ParseException {
        Field field = (Field) fieldsMap.get(String.valueOf(columnIndex));
        if (field != null) {
            if (columnValue == null || columnValue.trim().isEmpty()) {
                columnValue = this.getDefaultValueFor(field);
            }

            if (columnValue != null && !columnValue.trim().isEmpty()) {
                String dataType = this.getDataTypeFor(field);
                byte var7 = -1;
                switch (dataType.hashCode()) {
                    case -1325958191:
                        if (dataType.equals("double")) {
                            var7 = 3;
                        }
                        break;
                    case 104431:
                        if (dataType.equals("int")) {
                            var7 = 0;
                        }
                        break;
                    case 3029738:
                        if (dataType.equals("bool")) {
                            var7 = 2;
                        }
                        break;
                    case 3076014:
                        if (dataType.equals("date")) {
                            var7 = 4;
                        }
                        break;
                    case 3327612:
                        if (dataType.equals("long")) {
                            var7 = 1;
                        }
                }

                switch (var7) {
                    case 0:
                        field.set(classObj, Integer.parseInt(columnValue));
                        break;
                    case 1:
                        field.set(classObj, Long.parseLong(columnValue));
                        break;
                    case 2:
                        field.set(classObj, Boolean.parseBoolean(columnValue));
                        break;
                    case 3:
                        Double data = Double.parseDouble(columnValue);
                        field.set(classObj, data);
                        break;
                    case 4:
                        field.set(classObj, this.dateParser(columnValue));
                        break;
                    default:
                        field.set(classObj, columnValue);
                }
            }
        }

    }

    private java.sql.Date dateParser(String value) {
        if (value != null && !value.isEmpty()) {
            String[] formats = new String[]{"dd-MM-yyyy"};

            try {
                Date date = DateUtils.parseDate(value, formats);
                return new java.sql.Date(date.getTime());
            } catch (ParseException var5) {
                var5.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    boolean isEmptyRow(Row row) {
        boolean isEmptyRow = true;

        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); ++cellNum) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != 3 && StringUtils.isNotBlank(cell.toString())) {
                isEmptyRow = false;
            }
        }

        return isEmptyRow;
    }

    public boolean isBreakAfterEmptyRow() {
        return this.breakAfterEmptyRow;
    }

    public void setBreakAfterEmptyRow(boolean breakAfterEmptyRow) {
        this.breakAfterEmptyRow = breakAfterEmptyRow;
    }

    public boolean isSkipHeader() {
        return this.skipHeader;
    }

    public void setSkipHeader(boolean skipHeader) {
        this.skipHeader = skipHeader;
    }
}

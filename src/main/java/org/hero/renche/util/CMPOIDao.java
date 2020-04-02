package org.hero.renche.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.Region;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * @title POIDao
 * @description POI处理文档的一些方法
 * @author  ZhaoZhongbin
 */
public class CMPOIDao {
    //文档的最大行数
    public static final int MAX_VALUE = Integer.MAX_VALUE;
    public final static String YYYY_MM_DD = "yyyy-MM-dd";
    public final static String YYYY_MM_DD2 = "yyyy/MM/dd";
    public final static String YYYY_MM_DD3 = "yyyy年MM月dd日";
    public final static String YYYY_MM_DD4 = "yyyy-MM-dd HH:mm";
    public final static String YYYY_MM_DD5 = "yyyy-MM-dd HH:mm:ss";
    /**
     * @description 向工作表中填加工作区并填充数据
     * @param sheet    工作表(不可为空)
     * @param rowFrom  起始行(不可为空)
     * @param colFrom  起始列(不可为空)
     * @param rowTo    终止行(不可为空)
     * @param colTo    终止列(不可为空)
     * @param val
     * @param cellStyle
     * @author  ZhaoZhongbin
     * @date 2012-5-21
     */
    public static void createRegion(HSSFSheet sheet, int rowFrom, short colFrom, int rowTo, short colTo, Object val, HSSFCellStyle cellStyle) {
        sheet.addMergedRegion(new Region( rowFrom, colFrom, rowTo, colTo));
        for(int tmpRow = rowFrom; tmpRow <= rowTo && cellStyle != null; tmpRow++) {
            HSSFRow curRow = sheet.getRow(tmpRow);
            if (curRow == null) {
                curRow = sheet.createRow(tmpRow);
            }
            for (short tmpCol = colFrom; tmpCol <= colTo ; tmpCol ++) {
                if (cellStyle != null) {
                    HSSFCell cell = curRow.createCell(tmpCol) ;
                    cell.setCellStyle(cellStyle);
                }
            }
        }
        HSSFCell cell = (sheet.getRow(rowFrom) == null ? sheet.createRow(rowFrom) : sheet.getRow(rowFrom)).getCell(colFrom);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(val== null ? "" : val.toString());
    }

    /**
     * @description 向单元格中填加数据
     * @param sheet (不可为空)
     * @param row   (不可为空)
     * @param col   (不可为空)
     * @param val
     * @param cellStyle
     * @author  ZhaoZhongbin
     * @date 2012-5-21
     */
    public static void createCell(HSSFSheet sheet, int row, short col, Object val, HSSFCellStyle cellStyle) {
        HSSFRow curRow = sheet.getRow(row);
        if (curRow == null) {
            curRow = sheet.createRow(row);
        }
        HSSFCell cell = curRow.createCell(col);
        if (val instanceof BigDecimal) {
            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            cell.setCellValue(val== null ? 0 : ((BigDecimal)val).doubleValue());
        } else if (val instanceof Number) {
            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            cell.setCellValue(new BigDecimal(val.toString()).doubleValue()) ;
        } else if (val instanceof String){
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(new HSSFRichTextString(val == null ? "" : val.toString())) ;
        }
        if (cellStyle != null) {
            cell.setCellStyle(cellStyle);
        }
    }
    /**
     * @description 			取的单元格的值
     * @return 					string值
     * @author Dongqb
     * @date 2012-9-26
     */
    public static Object getValue(HSSFSheet sheet, int row, int col) {
        HSSFCell cell = sheet.getRow(row) == null ? null : sheet.getRow(row).getCell(col);
        if (cell == null) {
            return "";
        }
        Object cellValue = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_STRING:
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        cellValue = getDateValue(cell);
                    } else {
                        // cellValue = cell.getNumericCellValue();
                        Double d_temp = cell.getNumericCellValue();
                        DecimalFormat df = new DecimalFormat();
                        cellValue = df.format(d_temp).replaceAll(",", "")
                                .toString();
                    }
                    break;
                case HSSFCell.CELL_TYPE_FORMULA:
                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                case HSSFCell.CELL_TYPE_BLANK:
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    cellValue = cell.getBooleanCellValue();
                    break;
                case HSSFCell.CELL_TYPE_ERROR:
                    cellValue = cell.getErrorCellValue();
                    break;
                default:
                    break;
            }
        }
        return cellValue != null ? cellValue.toString() : "";
    }
    /**
     * @description 取单元格中的数据
     * @param sheet
     * @param row
     * @param col
     * @return
     * @author  zhaozhongbin
     * @date 2012-8-1
     */
    public static Object getCellValue (HSSFSheet sheet, int row, int col) {
        HSSFCell cell = sheet.getRow(row) == null ? null : sheet.getRow(row).getCell(col);
        Object cellValue = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_STRING:
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    cellValue = cell.getNumericCellValue();
                    break;
                case HSSFCell.CELL_TYPE_FORMULA:
                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    cellValue = cell.getNumericCellValue();
                    break;
                case HSSFCell.CELL_TYPE_BLANK:
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    cellValue = cell.getBooleanCellValue();
                    break;
                case HSSFCell.CELL_TYPE_ERROR:
                    cellValue = cell.getErrorCellValue();
                    break;
                default:
                    break;
            }
        }
        return cellValue;
    }
    /**
     * @description 单元格数据有效性设置 （单元格为下拉列表）
     * @param firstRow  起始行
     * @param lastRow   终止行
     * @param firstCol  起始列
     * @param lastCol   终止列
     * @param dataRange
     * @param errorMsgTitle  错误提示标题
     * @param errorMsgText   错误提示内容
     * @return
     * @author  zhaozhongbin
     * @date 2012-11-3
     */
    public static HSSFDataValidation createCellRange (int firstRow, int lastRow, int firstCol, int lastCol, String[] dataRange, String errorMsgTitle, String errorMsgText) {
        int size = dataRange.length;
//		String[] dataRanges = new String[]{};
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
        DVConstraint constraint = DVConstraint.createExplicitListConstraint((String[]) dataRange);
        HSSFDataValidation dataValidation = new HSSFDataValidation(regions,constraint);
        if (errorMsgTitle != null && errorMsgText != null) {
            dataValidation.createErrorBox(errorMsgTitle, errorMsgText);
        }
        return dataValidation;
    }

    public static HSSFDataValidation createDateCellRange (int firstRow, int lastRow, int firstCol, int lastCol, String start, String end, String errorMsgTitle, String errorMsgText) {
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
        DVConstraint constraint = DVConstraint.createDateConstraint(0, start, end, YYYY_MM_DD);//eExplicitListConstraint((String[]) dataRange);
        HSSFDataValidation dataValidation = new HSSFDataValidation(regions,constraint);
        if (errorMsgTitle != null && errorMsgText != null) {
            dataValidation.createErrorBox(errorMsgTitle, errorMsgText);
        }
        return dataValidation;
    }

    /**
     * @description     单元格数据有效性设置 （单元格为数字范围）
     * @param firstRow  起始行
     * @param lastRow   终止行
     * @param firstCol  起始列
     * @param lastCol   终止列
     * @param minVal    最大值 （数字的字符串形式）
     * @param maxVal    最小值 （数字的字符串形式）
     * @param errorMsgTitle  错误提示标题
     * @param errorMsgText   错误提示内容
     * @return
     * @author  zhaozhongbin
     * @date 2012-11-3
     */
    public static HSSFDataValidation createCellRange (int firstRow, int lastRow, int firstCol, int lastCol, String minVal, String maxVal, String errorMsgTitle, String errorMsgText) {
        DVConstraint foundConstraint = DVConstraint.createNumericConstraint(
                DataValidationConstraint.ValidationType.DECIMAL, DataValidationConstraint.OperatorType.BETWEEN, minVal, maxVal);
        CellRangeAddressList fundRegion = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
        HSSFDataValidation dataValidation = new HSSFDataValidation(fundRegion,foundConstraint);
        if (errorMsgTitle != null && errorMsgText != null) {
            dataValidation.createErrorBox(errorMsgTitle, errorMsgText);
        }
        return dataValidation;
    }
    /**
     * 创建名称
     * @param wb
     * @param name
     * @param expression
     * @return
     */
    public static HSSFName createName(HSSFWorkbook wb, String name, String expression){
        HSSFName refer = wb.createName();
        refer.setRefersToFormula(expression);
        refer.setNameName(name);
        return refer;
    }

    /**
     * 设置数据有效性（通过名称管理器级联相关）
     * @param name
     * @param firstRow
     * @param endRow
     * @param firstCol
     * @param endCol
     * @return
     */
    public static HSSFDataValidation setDataValidation(String name, int firstRow, int endRow, int firstCol, int endCol){
        //加载下拉列表内容
        DVConstraint constraint = DVConstraint.createFormulaListConstraint(name);
        // 设置数据有效性加载在哪个单元格上。
        // 四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList( firstRow,  endRow,  firstCol,  endCol);
        // 数据有效性对象
        HSSFDataValidation data_validation = new HSSFDataValidation(regions, constraint);
        return data_validation;
    }
    /**
     * @description 返回时间内的特殊时间格式 OFFICE2003
     * @param cell
     * @return
     * @author Dongqb
     * @date 2012-9-28
     */
    private static String getDateValue(HSSFCell cell) {
        DateFormat df = getDateFormat(cell);
        return df.format(cell.getDateCellValue());
    }

    private static DateFormat getDateFormat(HSSFCell cell) {

        String S_temp = cell.toString();
        DateFormat df;
        if (S_temp.length() < 12) {
            String str = cell.getCellStyle().getDataFormatString();
            if (str.indexOf("/") > -1) {
                df = new SimpleDateFormat(YYYY_MM_DD2);
            } else if (str.indexOf("-") > -1) {
                df = new SimpleDateFormat(YYYY_MM_DD);
            } else {
                df = new SimpleDateFormat(YYYY_MM_DD3);
            }
        } else if (S_temp.length() > 11 && S_temp.length() < 17) {
            df = new SimpleDateFormat(YYYY_MM_DD4);
        } else {
            df = new SimpleDateFormat(YYYY_MM_DD5);
        }
        return df;
    }

}
package com.touceng.common.utils;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 功能描述: Excel创建工具类
 * @createTime 2018/7/31 下午12:46
 * @copyright: 上海投嶒网络技术有限公司
 */
public class PoiExcelWriteUtils {

    public static int maxLine = 65535;


    public static HSSFCellStyle getTitleStyle(HSSFWorkbook wb) {

        HSSFCellStyle style = wb.createCellStyle(); // 标题样式
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setWrapText(true);
        style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
        style.setFillBackgroundColor(HSSFColor.GREY_40_PERCENT.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return style;
    }

    public static HSSFCellStyle getCellStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle(); // 单元格样式
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // style.setWrapText(true);
        // style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
        // style.setFillBackgroundColor(HSSFColor.GREY_40_PERCENT.index);
        // style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return style;
    }

    public static HSSFCellStyle getCellRateStyle(HSSFWorkbook wb) {

        HSSFCellStyle style = wb.createCellStyle(); // 单元格样式
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // style.setWrapText(true);
        // style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
        // style.setFillBackgroundColor(HSSFColor.GREY_40_PERCENT.index);
        // style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return style;
    }

    /**
     * 创建表头
     */
    public static void creatHeads(HSSFWorkbook wb, HSSFSheet sheet, String[] titles, int[] widthes) {
        createHeadsForstartRow(wb, sheet, titles, widthes, 0);
    }

    /****
     *
     * @param wb
     * @param sheet
     * @param titles
     * @param widthes
     *            长度
     * @param startRow
     *            开始行 0为第一列
     */
    public static void createHeadsForstartRow(HSSFWorkbook wb, HSSFSheet sheet, String[] titles, int[] widthes, int startRow) {
        HSSFRow head = sheet.createRow(startRow);
        HSSFCellStyle titleStyle = getTitleStyle(wb);
        head.setHeightInPoints(25);
        for (int i = 0; i < titles.length; i++) {
            Cell cell = head.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(titleStyle);
            sheet.setColumnWidth((short) i, 256 * widthes[i]); // 调整第一列宽度:256 *
            // 30
        }

    }

    public static void write(Workbook wb, HttpServletResponse response, String title) throws IOException {

        if (wb == null) {
            wb = new HSSFWorkbook();
        }
        response.setHeader("Content-Disposition", "attachment;filename=" + new String((title).getBytes("gbk"), "iso8859-1") + ".xls");
        response.setHeader("Connection", "close");
        response.setHeader("Content-Type", "application/vnd.ms-excel");
        wb.write(response.getOutputStream());
    }

    public static void write2File(HSSFWorkbook workbook, String filePath, String fileName) {

        try {
            TCFileUtils.createDirs(filePath, true);
            try (FileOutputStream fos = new FileOutputStream(filePath + fileName)) {
                try {
                    workbook.write(fos);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("写入的文件不存在");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("写入数据失败:" + e);
        }
    }


    public static <T> HSSFWorkbook createWorkbook(String title, String[] titles, String[] attrs, List<T> dataset) {

        return createWorkbook(null, title, titles, attrs, null, dataset, DateToolUtils.DATE_FORMAT_DATEONLY);

    }

    public static <T> HSSFWorkbook createWorkbook(String title, String[] titles, String[] attrs, int[] widthes, List<T> dataset) {

        return createWorkbook(null, title, titles, attrs, widthes, dataset, DateToolUtils.DATE_FORMAT_DATEONLY);

    }

    public static <T> HSSFWorkbook createWorkbook(String title, String[] titles, String[] attrs, List<T> dataset, String pattern) {

        return createWorkbook(null, title, titles, attrs, null, dataset, pattern);

    }

    public static <T> HSSFWorkbook createWorkbook(HSSFWorkbook workbook, String title, String[] titles, String[] attrs, List<T> dataset) {
        return createWorkbook(workbook, title, titles, attrs, null, dataset, DateToolUtils.DATE_FORMAT_DATEONLY);
    }

    /**
     * 这是一个通用的方法，利用了 JAVA 的反射机制，可以将放置在 JAVA 集合中并且符号一定条件的数据以 EXCEL 的形式输出到指定 IO
     * 设备上
     *
     * @param title   表格标题名
     * @param titles  例如:{mobile='手机号',amount='充值总额',merchantName='商家',.....}
     * @param dataset 需要显示的数据集合 , 集合中一定要放置符合 javabean 风格的类的对象。此方法支持的 javabean
     *                属性的数据类型有基本数据类型及 String,Date
     * @param pattern 如果有时间数据，设定输出格式。默认为 "yyyy-MM-dd"
     */
    private static <T> HSSFWorkbook createWorkbook(HSSFWorkbook workbook, String title, String[] titles, String[] attrs, int[] widthes, List<T> dataset, String pattern) {
        int linesNum = 0;
        // 声明一个工作薄
        if (workbook == null) {
            workbook = new HSSFWorkbook();
        }
        if (dataset != null) {
            linesNum = dataset.size();
        }
        if (maxLine >= linesNum) {
            // 创建单元格格式
            createSheet(title, dataset, pattern, titles, attrs, widthes, workbook);
        } else {
            throw new RuntimeException("poi导出excel版本为2003兼容版，每sheet不能超过" + maxLine + "行。现总行数为：" + linesNum);
        }

        return workbook;

    }

    /**
     * createWorkbook方法升级版，可以创建多个sheet
     */
    @SuppressWarnings("unused")
    private static <T> HSSFWorkbook createWorkbook(HSSFSheet[] sheets, String[] titles, String[] attrs, int[] widthes, List<T> dataset, String pattern) {

        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        if (sheets != null && sheets.length > 0) {
            for (HSSFSheet sheet : sheets) {
                createSheet(sheet.getSheetName(), dataset, pattern, titles, attrs, widthes, workbook);
            }
        }

        return workbook;

    }

    /**
     * 现在用的poi版本导出excel版本为2007-2003兼容版本 excel最大行数支持为65536行 进行判断是否超过，如超过则新建sheet
     *
     * @param title
     * @param dataset
     * @param pattern
     * @param workbook
     * @return: void
     */
    private static <T> void createSheet(String title, List<T> dataset, String pattern, String[] titles, String[] attrs, int[] widthes, HSSFWorkbook workbook) {

        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为 15 个字节
        sheet.setDefaultColumnWidth(15);
        // 产生表格标题行
        // 生成一个样式
        HSSFCellStyle cellStyle = PoiExcelWriteUtils.getCellStyle(workbook);
        /** 创建表头 */
        int[] widthesTmp = new int[titles.length];
        if (widthes == null) {
            for (int i = 0; i < titles.length; i++) {
                widthesTmp[i] = 15;
            }
        } else {
            widthesTmp = widthes;
        }
        creatHeads(workbook, sheet, titles, widthesTmp);

        // 遍历集合数据，产生数据行
        createRows(sheet, dataset, attrs, 0, cellStyle, pattern);
    }

    @SuppressWarnings("rawtypes")
    public static void createRows(HSSFSheet sheet, List list, String[] attrs, int start, HSSFCellStyle cellStyle, String pattern) {

        HSSFRow row;
        for (Object o : list) {
            start++;
            row = sheet.createRow(start);
            row.setHeightInPoints(20);
            for (int i = 0; i < attrs.length; i++) {
                String attrName = attrs[i];
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(cellStyle);
                try {
                    Object value = PropertyUtils.getProperty(o, attrName);
                    // 判断值的类型后进行强制类型转换
                    setCellValue(pattern, cell, value);
                } catch (Exception e) {

                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
    public static void createRows(HSSFSheet sheet, List list, String[] attrs, int start, HSSFCellStyle cellStyle) {
        createRows(sheet, list, attrs, start, cellStyle, DateToolUtils.DATE_FORMAT_DATEONLY);
    }

    public static void setCellValue(String pattern, HSSFCell cell, Object value) {

        if (value instanceof BigDecimal) {
            double intValue = ((BigDecimal) value).doubleValue();
            cell.setCellValue(intValue);
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            // style.setDataFormat(df.getFormat("#,##0.00"));
        } else if (value instanceof Float) {
            float fValue = (Float) value;
            cell.setCellValue(fValue);
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            // style.setDataFormat(df.getFormat("#,##0.00"));
        } else if (value instanceof Double) {
            double dValue = (Double) value;
            cell.setCellValue(dValue);
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            // style.setDataFormat(df.getFormat("#,##0.00"));
        } else if (value instanceof Long) {
            long longValue = (Long) value;
            cell.setCellValue(longValue);
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            // style.setDataFormat(df.getFormat("#,##0.00"));
        } else if (value instanceof Date) {
            Date date = (Date) value;
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            String textValue = sdf.format(date);
            cell.setCellValue(textValue);
        } else {
            // 其它数据类型都当作字符串简单处理
            String textValue = value == null ? "" : value.toString();
            // Pattern p = Pattern.compile("^//d+(//.//d+)?$");
            // Matcher matcher = p.matcher(textValue);
            // if ( matcher.matches() ) {
            // // 是数字当作 double 处理
            // cell.setCellValue(Double.parseDouble(textValue));
            // } else {
            // // HSSFRichTextString richString = new
            // // HSSFRichTextString(textValue);
            // // HSSFFont font3 = workbook.createFont();
            // // richString.applyFont(font3);
            // cell.setCellValue(textValue);
            // }
            cell.setCellValue(textValue);
        }
        // return textValue;
    }

}

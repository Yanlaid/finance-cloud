package com.touceng.common.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 功能描述: Excel读取工具类
 * @createTime 2018/7/31 下午12:41
 * @copyright: 上海投嶒网络技术有限公司
 */
@Slf4j
public class PoiExcelReadUtils {

	/**
	 * 是否为EXCEL2003
	 */
	public static boolean isExcel2003 = false;

	// 判断excel版本
	public static Workbook openWorkbook(InputStream in) throws IOException {

		Workbook wb = null;
		if (!isExcel2003) {
			wb = new XSSFWorkbook(in);// Excel 2007
		} else {
			wb = new HSSFWorkbook(in);// Excel 2003
		}
		return wb;
	}

	@SuppressWarnings("rawtypes")
	public List getExcelDataForByFileName(String fileName) throws Exception {

		if (fileName.endsWith(".xlsx")) {
			PoiExcelReadUtils.isExcel2003 = false;
		} else {
			PoiExcelReadUtils.isExcel2003 = true;
		}
		InputStream in = new FileInputStream(fileName);
		getExcelData(in);
		return null;
	}

	@SuppressWarnings("rawtypes")
	public List getExcelData(InputStream in) throws Exception {

		Workbook wb = openWorkbook(in);
		Sheet sheet = (Sheet) wb.getSheetAt(0);
		Row row = null;
		Cell cell = null;

		int totalRows = sheet.getPhysicalNumberOfRows();
		int totalCells = sheet.getRow(0).getPhysicalNumberOfCells();

		for (int r = 0; r < totalRows; r++) {
			row = sheet.getRow(r);
			// System.out.print("第" + r + "行");
			for (int c = 0; c < totalCells; c++) {
				cell = row.getCell(c);
				String cellValue = "";
				if (null != cell) {
					// 以下是判断数据的类型
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_NUMERIC: // 数字
						cellValue = cell.getNumericCellValue() + "";
						// 时间格式
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							Date dd = cell.getDateCellValue();
							DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							cellValue = df.format(dd);
						}
						break;
					case HSSFCell.CELL_TYPE_STRING: // 字符串
						cellValue = cell.getStringCellValue();
						break;

					case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
						cellValue = cell.getBooleanCellValue() + "";
						break;

					case HSSFCell.CELL_TYPE_FORMULA: // 公式
						cellValue = cell.getCellFormula() + "";
						break;

					case HSSFCell.CELL_TYPE_BLANK: // 空值
						cellValue = "";
						break;

					case HSSFCell.CELL_TYPE_ERROR: // 故障
						cellValue = "非法字符";
						break;

					default:
						cellValue = "未知类型";
						break;
					}

					System.out.print("   " + cellValue + "\t");
				}
			}
			System.out.println();
		}
		return null;
	}

	/**
	 * 获取Excel数据,返回List<String[]>
	 *
	 * @param sheetNumber 读取工作表的下标(从1开始).可有可无,默认读取所有表单.
	 */
	public static List<String[]> excelToArrayList(String filePath, int... sheetNumber) throws Exception {

		List<String[]> resultList = null;
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);
			resultList = excelToArrayList(is, sheetNumber);
		} catch (Exception e) {
			throw e;
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return resultList;
	}

	/**
	 * 获取Excel数据,返回List<String[]>
	 *
	 * @param sheetNumber 读取工作表的下标(从1开始).可有可无,默认读取所有表单.
	 */
	public static List<String[]> excelToArrayList(FileItem fileItem, int... sheetNumber) throws Exception {

		List<String[]> resultList = null;
		InputStream is = null;
		try {
			is = fileItem.getInputStream();
			resultList = excelToArrayList(is, sheetNumber);
		} catch (Exception e) {
			throw e;
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return resultList;
	}

	/**
	 * 获取Excel数据,返回List<String[]>;
	 *
	 * @param sheetNumber 读取工作表的下标(从1开始).可有可无,默认读取所有表单.
	 */
	public static List<String[]> excelToArrayList(InputStream is, int... sheetNumber) throws Exception {

		ArrayList<String[]> resultList = new ArrayList<String[]>();
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(is);
			if (sheetNumber == null || sheetNumber.length < 1) {
				int sheetCount = wb.getNumberOfSheets();// 得到所有Excel中页的列表.
				sheetNumber = new int[sheetCount];
				for (int i = 0; i < sheetNumber.length; i++) {
					sheetNumber[i] = i + 1;
				}
			}
			Sheet sheet = null;
			for (int k = 0; k < sheetNumber.length; k++) {// 循环工作表
				sheet = wb.getSheetAt(sheetNumber[k] - 1);

				int totalRows = sheet.getPhysicalNumberOfRows();
				log.warn("Sheet " + sheetNumber[k] + "." + wb.getSheetName(sheetNumber[k] - 1) + ":" + (totalRows + 1));
				for (int i = 0; i <= totalRows; i++) {// 循环行
					Row row = sheet.getRow(i);
					if (row != null) {
						int cellCount = row.getLastCellNum();
						if (-1 == cellCount) {
							cellCount = sheet.getRow(0).getPhysicalNumberOfCells();
						}
						if (cellCount > 0) {
							String[] objects = new String[cellCount];
							for (int j = 0; j < cellCount; j++) {// 读取单元格
								objects[j] = getCellValue(row.getCell(j));
							}
							resultList.add(objects);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Read excel error.", e);
			throw e;
		}
		return resultList;
	}

	/**
	 * 读取Excel中的数据.将这些数据放入到一个三维数组中.
	 *
	 * @param filePath 文件路径.
	 * @param rowNum   读取工作表的下标(从1开始).可有可无,默认读取所有表单.
	 * @deprecated:读取Excel中的数据将它放入到ArrayList数组中(此为三维数组).
	 */
	public static ArrayList<List<String>> readExcel(String filePath, Integer rowNum) throws Exception {

		ArrayList<List<String>> datas = null;
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);
			datas = readExcel(is, rowNum);
		} catch (Exception e) {
			throw e;
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return datas;
	}

	/**
	 * 读取Excel中的数据.将这些数据放入到一个三维数组中.
	 *
	 * @param rowNum 读取工作表的下标(从1开始).可有可无,默认读取所有表单.
	 */
	public static ArrayList<List<String>> readExcel(FileItem fileItem, Integer rowNum) throws Exception {

		ArrayList<List<String>> datas = null;
		InputStream is = null;
		try {
			is = fileItem.getInputStream();
			datas = readExcel(is, rowNum);
		} catch (Exception e) {
			throw e;
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return datas;
	}

	/**
	 * 读取Excel中的数据.将这些数据放入到一个三维数组中.
	 *
	 * @param rowNum 读取工作表的下标(从1开始).可有可无,默认读取所有表单.
	 */
	public static ArrayList<List<String>> readExcel(InputStream is, Integer rowNum) throws Exception {

		ArrayList<List<String>> datas = new ArrayList<List<String>>();
		Workbook wb = null;
		try {
			wb = openWorkbook(is);
			if (null == rowNum) {
				rowNum = 1;// 默认从第一行读取数据
			}
			Sheet sheet = null;
			List<String> subData = null;
			sheet = wb.getSheetAt(0);
			int rsRows = sheet.getPhysicalNumberOfRows();
			int iRow = 0;
			String cellValue = null;
			for (int i = rowNum; i <= rsRows; i++) {
				subData = new ArrayList<String>();
				Row row = sheet.getRow(i);
				if (null == row) {
					continue;
				}
				int cellCount = row.getLastCellNum();
				for (int j = 0; j < cellCount; j++) {
					// 通用的获取cell值的方式,返回字符串
					cellValue = getCellValue(row.getCell(j));
					// 获得cell具体类型值的方式得到内容.
					subData.add(j, cellValue);
				}
				datas.add(iRow, subData);
			}
		} catch (Exception e) {
			log.error("Read excel error.", e);
			throw e;
		}
		return datas;
	}

	/**
	 * 根据Cell类型设置数据
	 */
	private static String getCellValue(Cell cell) {

		String cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_BLANK:
				cellvalue = "";
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				cellvalue = Boolean.toString(cell.getBooleanCellValue());
				break;
			// 数值
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					cellvalue = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue());
				} else {
					cell.setCellType(Cell.CELL_TYPE_STRING);
					String temp = cell.getStringCellValue();
					// 判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串
					if (temp.indexOf(".") > -1) {
						cellvalue = String.valueOf(new Double(temp)).trim();
					} else {
						cellvalue = temp.trim();
					}
				}
				break;
			case Cell.CELL_TYPE_STRING:
				cellvalue = cell.getStringCellValue().trim();
				break;
			case Cell.CELL_TYPE_ERROR:
				cellvalue = "";
				break;
			case Cell.CELL_TYPE_FORMULA:
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cellvalue = cell.getStringCellValue();
				if (cellvalue != null) {
					cellvalue = cellvalue.replaceAll("#N/A", "").trim();
				}
				break;
			default:
				cellvalue = "";
				break;
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;
	}

	public boolean isExcel2003() {

		return PoiExcelReadUtils.isExcel2003;
	}

	public void setExcel2003(boolean isExcel2003) {

		PoiExcelReadUtils.isExcel2003 = isExcel2003;
	}

	public void setExcel2003(String fileName) {

		if (fileName.endsWith(".xlsx")) {
			isExcel2003 = false;
		} else {
			isExcel2003 = true;
		}
	}
}

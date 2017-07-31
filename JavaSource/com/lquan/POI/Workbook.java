package com.lquan.POI;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import snt.common.rs.MemoryResultSet;
import snt.common.rs.MemoryResultSetMetaData;


public abstract class Workbook {

	private HSSFWorkbook wookBook;
	private int sheet;
	private Map<String, Short> colNamsMap;
	private List<String> colNamesList;
	private MemoryResultSet resultSet;
	
	public Workbook(FileInputStream workBookStream, int sheet) throws IOException, POIException{
		POIFSFileSystem saleBook = new POIFSFileSystem(workBookStream);
		this.wookBook = new HSSFWorkbook(saleBook);
		if (sheet < 0 || sheet > this.wookBook.getNumberOfSheets()) {
			throw new POIException("Out of the number of sheets!");
		}else {
			if (this.wookBook.getNumberOfSheets() == 1) {
				this.sheet = 0;
			} else {
				this.sheet = sheet;
			}
		}
		
		//初始化各成员变量
		this.colNamesList = this.gainColNameList();
		
		this.colNamsMap = this.validCheck();
		if (this.colNamsMap == null) {
			this.colNamsMap = this.listToMap(this.colNamesList);
		}
	}
	
	/**
	 * 将从Excel中读取得到的结果集转换成一个列表，列表中的每个元素为一个Map，对应原结果集的每条记录。
	 * Excel表中的第一行的单元格中的字符串对应Map中的key，Excel表中的其余各行的值对应Map中的value。
	 * @return
	 * @throws POIException 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map> gainDateForMapList() throws POIException {
		List ls = new ArrayList<Map>();
		if (this.getWookBook().getSheetAt(this.sheet) == null) {
			return ls;
		}else {
			Map row;
			HSSFSheet sheet = this.getWookBook().getSheetAt(this.sheet);
			List<String> iter = this.gainColNameList();
			for (int i = 1; sheet.getRow(i) != null ; i++) {
				row = new LinkedHashMap<String, Object>();
				int isNull = 0;
				for(int j = 0; j < iter.size(); j++) {
					HSSFCell cell = sheet.getRow(i).getCell(j);
					if (cell == null) {
						row.put(iter.get(j), null);
						isNull++;
						continue;
					}
					Object val = this.format(cell);
					if (val.equals("#BLANK")) {
						isNull++;
						val = null;
					} else if (val.equals("#ERROR")) {
						isNull++;
						val = null;
					}
					row.put(iter.get(j), val);
				}
				if (isNull > 5) {
					isNull--;
					isNull++;
				}
				if (isNull < iter.size()) {
					ls.add(row);
				}
			}
			return ls;
		}
	}

	/**
	 * 将从Excel中读取得到的结果集转换成一个内存结果集。
	 * @return
	 * @throws POIException
	 */
	@SuppressWarnings("unchecked")
	private MemoryResultSet gainDate() throws POIException {
		int[] colTypes;
		List<List<Object>> ls = new ArrayList<List<Object>>();
		if (this.getWookBook().getSheetAt(this.sheet) == null) {
			return null;
		}else {
			HSSFSheet sheet = this.getWookBook().getSheetAt(this.sheet);
			
			colTypes = new int[this.colNamsMap.size()]; 
			List<Object> row;
			for (int i = 1; sheet.getRow(i) != null ; i++) {
				row = new ArrayList<Object>();
				int isNull = 0;
				int colTypeAt = 0;
				int length = this.colNamsMap.size();
				for (String colName : this.colNamsMap.keySet()) {
					int j = this.colNamsMap.get(colName);
					HSSFCell cell = sheet.getRow(i).getCell(j);
					if (cell == null) {
						colTypes[colTypeAt] = colTypes[colTypeAt] == Types.NULL ? Types.NULL:colTypes[colTypeAt];
						row.add(null);
						isNull++;
						colTypeAt++;
						continue;
					}
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_BLANK:
						colTypes[colTypeAt] = colTypes[colTypeAt] == Types.NULL ? Types.NULL:colTypes[colTypeAt];
						row.add(null);
						isNull++;
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						colTypes[colTypeAt] = colTypes[colTypeAt] == Types.NULL ? Types.BOOLEAN:colTypes[colTypeAt];
						row.add(cell.getBooleanCellValue());
						break;
					case HSSFCell.CELL_TYPE_ERROR:
						colTypes[colTypeAt] = colTypes[colTypeAt] == Types.NULL ? Types.NULL:colTypes[colTypeAt];
						row.add(null);
						isNull++;
						break;
					case HSSFCell.CELL_TYPE_FORMULA:
						colTypes[colTypeAt] = colTypes[colTypeAt] == Types.NULL ? Types.VARCHAR:colTypes[colTypeAt];
						row.add(cell.getCellFormula().trim());
						break;
					case HSSFCell.CELL_TYPE_NUMERIC:
						//POI是根据 MS Excel 的默认单元格类型来判断其是不是 Date ，但是，自定义类型并不存在于
						// MS Excel 的默认类型当中，
						// 所以，即使自定义类型与日期型格式完全一样，POI也不认为其是 Date 类型。
						if (this.checkStyle(cell)) {
							DateFormat sdf = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss"); // dd-MMM-yyyy
							colTypes[colTypeAt] = colTypes[colTypeAt] == Types.NULL ? Types.DATE
									: colTypes[colTypeAt];
							row.add(sdf.format(cell.getDateCellValue()));
						} else {
							if(cell.getNumericCellValue() > (int) cell
									.getNumericCellValue()){
								colTypes[colTypeAt] = colTypes[colTypeAt] == Types.NULL ? Types.DOUBLE
										: colTypes[colTypeAt];
								row.add(cell.getNumericCellValue() );
							}else{
								colTypes[colTypeAt] = colTypes[colTypeAt] == Types.NULL ? Types.INTEGER
										: colTypes[colTypeAt];
								row.add((int)cell.getNumericCellValue());
							}
						}
						break;
					case HSSFCell.CELL_TYPE_STRING:
						colTypes[colTypeAt] = colTypes[colTypeAt] == Types.NULL ? Types.VARCHAR:colTypes[colTypeAt];
						String rowCell = cell.getStringCellValue().trim();
						if ("".equalsIgnoreCase(rowCell)) {
							row.add(null);
						}else {
							row.add(rowCell);
						}
						break;
					default:
						colTypes[colTypeAt] = colTypes[colTypeAt] == Types.NULL ? Types.NULL:colTypes[colTypeAt];
						row.add(null);
						isNull++;
						throw new POIException("Unknown Cell Type: " + cell.getCellType());
					}
					colTypeAt++;
				}
				if (isNull < length) {
					ls.add(row);
				}
			}
			return new MemoryResultSet(ls, new MemoryResultSetMetaData(colTypes, this.setToList(this.colNamsMap.keySet())));
		}
	}

	
	/**
	 * 得到sheet中第一行的值，存在List中
	 * @return
	 * @throws POIException
	 */
	private List<String> gainColNameList() throws POIException{
 		List<String> ls = new ArrayList<String>();
		if (this.getWookBook().getSheetAt(this.sheet) == null||this.getWookBook().getSheetAt(this.sheet).getRow(0)==null) {
			return ls;
		}else {
			HSSFSheet sheet = this.getWookBook().getSheetAt(this.sheet);
				for (int j = 0; sheet.getRow(0).getCell(j) != null; j++) {
				HSSFCell cell = sheet.getRow(0).getCell(j);
				ls.add( String.valueOf(this.format(cell)).toUpperCase() );
			}
			return ls;
		}
	}

	/**
	 * 得到Excel单元格对应数据类型的值
	 * @param cell
	 * @return
	 * @throws POIException
	 */
	protected Object format(HSSFCell cell) throws POIException {
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_BLANK:
			return "#BLANK";
		case HSSFCell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();
		case HSSFCell.CELL_TYPE_ERROR:
			return "#ERROR";
		case HSSFCell.CELL_TYPE_FORMULA:
			return cell.getCellFormula().trim();
		case HSSFCell.CELL_TYPE_NUMERIC:
			// POI是根据 MS Excel 的默认单元格类型来判断其是不是 Date ，但是，自定义类型并不存在于 MS Excel 的默认类型当中，
			//所以，即使自定义类型与日期型格式完全一样，POI也不认为其是 Date 类型。
			if (this.checkStyle(cell)) {
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //dd-MMM-yyyy
				return sdf.format(cell.getDateCellValue());
			} else {
				try{
					return (long) cell.getNumericCellValue(); 
				}catch (Exception e) {
				}
				return cell.getNumericCellValue(); //系统没有小数点之类的导入，转换成int
			}
		case HSSFCell.CELL_TYPE_STRING:
			return cell.getStringCellValue().trim();
		default:
			throw new POIException("Unknown Cell Type: " + cell.getCellType());
		}
	}
	
	/**
	 * 检查单元格的类型是否属于日期型。
	 * @param cell
	 * @return
	 */
	private boolean checkStyle(HSSFCell cell) {
		if (HSSFDateUtil.isCellDateFormatted(cell)) {
			return true;
		}
		switch (cell.getCellStyle().getDataFormat()) { //  自定义类型好难处理阿。。。
		case 188:
		case 184:
		//case 176:
			return true;
		default:
			return false;
		}
	}
	
	/**
	 * 将List<String> 转换为 Map<String, Short>，其中，Map的key值为List中的值，Map中的value为List中对应值在该List中的位置。
	 * @param ls
	 * @return
	 */
	private Map<String, Short> listToMap(List<String> ls) {
		Map<String, Short> map = new LinkedHashMap<String, Short>();
		int length = ls.size();
		for (int i = 0; i < length; i++) {
			map.put(ls.get(i), Integer.valueOf(i).shortValue());
		}
		return map;
	}
	
	private String[] setToList(Set<String> set){
		List<String> list = new ArrayList<String>(set);
		return  list.toArray(new String [0]);
	}
	
	/**
	 * 数据有效性检查，取得有效字段
	 * @return
	 */
	protected abstract Map<String, Short> validCheck() throws POIException;

	public HSSFWorkbook getWookBook() {
		return wookBook;
	}

	protected void setWookBook(HSSFWorkbook wookBook) {
		this.wookBook = wookBook;
	}
	
	public List<String> getColNamesList() {
		return colNamesList;
	}

	protected void setColNamesList(List<String> colNamesList) {
		this.colNamesList = colNamesList;
	}

	public Map<String, Short> getColNamsMap() {
		return colNamsMap;
	}

	public void setColNamsMap(Map<String, Short> colNamsMap) {
		this.colNamsMap = colNamsMap;
	}
	
	public MemoryResultSet gainDateForMemoryResultSet() throws POIException, SQLException {
		if (this.resultSet == null || this.resultSet.getResultList() == null) {
			this.resultSet = this.gainDate();
		}
		return new MemoryResultSet(this.resultSet);
	}
}
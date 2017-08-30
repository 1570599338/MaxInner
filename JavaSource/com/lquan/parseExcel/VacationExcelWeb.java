package com.lquan.parseExcel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lquan.POI.POIException;
import com.lquan.POI.Workbook;
import com.lquan.POI.Workbookx;


public final class VacationExcelWeb extends Workbook {
	
	private Log log = LogFactory.getLog(StaffExcelWb.class);
	public static String[] strArray= {"姓名","可休年假天数","本月实休天数","剩余年假天数"};
	public static String columnsCode=" id,useName,baseDay,spendDay,restDay,createBy,createAt ";
	

	public VacationExcelWeb(FileInputStream workBookStream, int sheet) throws IOException, POIException {
		super(workBookStream, sheet);
	}
	
	
	/**
	 * 验证表头
	 */
	@Override
	protected Map<String, Short> validCheck() throws POIException {
		
		return null;
	}
	/**
	 * 验证表头
	 */
	protected Map<String, Short> validCheckhead() throws POIException {

		Map<String, Short> map = new LinkedHashMap<String, Short>();
		List<String> check = this.getColNamesList();
		for (int i = 0; i < strArray.length; i++) {
			Integer index = check.indexOf(strArray[i]);
			if (index < 0) {
				log.error("上传年假模板不正确，该错误是由缺失标题列：" + strArray[i] + " 引起的");
				throw new POIException("上传年假模板不正确，该错误是由缺失标题列：" + strArray[i] + " 引起的");
			} else {
				map.put(strArray[i], index.shortValue());
			}
		}
		return map;
	}

	/**
	 * 临时表SQL
	 * @param tempTable
	 * @return
	 */
	public String getCreateSql(String tempTable) {
		StringBuilder sb = new StringBuilder();
		sb.append(" CREATE TABLE ").append(tempTable).append(" ( ");
		sb.append(" pk_id BIGINT IDENTITY, ");
		sb.append(" id BIGINT , ");
		sb.append(" useName varchar(200) not null, ");
		sb.append(" baseDay varchar(200) , ");
		sb.append(" spendDay varchar(200),");
		sb.append(" restDay varchar(200),");
		sb.append(" createBy varchar(200),");
		sb.append("	createAt datetime,");
		sb.append(" batchnum BIGINT ");
		sb.append(" ) ");
		return sb.toString();
	}
	// public static String columnsCode="id,userName,baseDay,spendDay,restDay";
	/**
	 * 插入临时表的SQL
	 * @param tempTable
	 * @param batchNum
	 * @return
	 */
	public String getInsertSql(String tempTable, long batchNum) {
	// pk_id,id,userName,baseDay,spendDay,restDay,createBy,createAt,batchNum
		StringBuilder sb = new StringBuilder();
		sb.append(" insert into ").append(tempTable);
		sb.append(" (id,useName,baseDay,spendDay,restDay,createBy,createAt,batchNum)");
		sb.append(" values(?,?,?,?,?,?,getdate(),").append(batchNum).append(")");
		return sb.toString();
	}


	@Override
	protected Map<String, Short> validCheck(String type) throws POIException {
		// TODO Auto-generated method stub
		return null;
	}



}

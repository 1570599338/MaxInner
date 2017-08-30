package com.lquan.parseExcel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



import com.lquan.POI.POIException;
import com.lquan.POI.Workbookx;


public class StaffExcelWb extends Workbookx {
	private Log log = LogFactory.getLog(StaffExcelWb.class);
	public static String[] strArray= {"部门编码","姓名","性别","分机号","手机号","邮箱"};
	public static String columnsCode="id,departmentCode,useName,gender,telphone,celphone,email,createBy,stat,createAt";
	

	public StaffExcelWb(FileInputStream workBookStream, int sheet) throws IOException, POIException {
		super(workBookStream, sheet);
	}

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
				log.error("上传员工信息模板不正确，该错误是由缺失标题列：" + strArray[i]
						+ " 引起的");
				throw new POIException("上传员工模板不正确，该错误是由缺失标题列：" + strArray[i]
						+ " 引起的");
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
		sb.append(" departmentCode varchar(200), ");
		sb.append(" useName varchar(200) not null, ");
		sb.append(" gender int, ");
		sb.append(" telphone varchar(200) , ");
		sb.append(" celphone varchar(200),");
		sb.append(" email varchar(200),");
		sb.append(" createBy varchar(200),");
		sb.append("stat int ,");
		sb.append("	createAt datetime,");
		sb.append(" batchnum BIGINT ");
		sb.append(" ) ");
		return sb.toString();
	}
	
	/**
	 * 插入临时表的SQL
	 * @param tempTable
	 * @param batchNum
	 * @return
	 */
	public String getInsertSql(String tempTable, long batchNum) {
	
		StringBuilder sb = new StringBuilder();
		sb.append(" insert into ").append(tempTable);
		sb.append(" (id,departmentCode,useName,gender,telphone,celphone,email,createBy,stat,createAt,batchNum)");
		sb.append(" values(?,?,?,?,?,?,?,?,?,getdate(),").append(batchNum).append(")");
		return sb.toString();
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

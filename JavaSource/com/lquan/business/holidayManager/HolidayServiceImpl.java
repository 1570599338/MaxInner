package com.lquan.business.holidayManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import snt.common.dao.base.CommonDAO;
import snt.common.dao.base.PaginationSupport;
import snt.common.dao.base.PrimaryKeyGenerator;
import snt.common.dao.base.RowSelection;

import com.lquan.common.Constants;
import com.lquan.parseExcel.StaffExcelWb;
import com.lquan.parseExcel.VacationExcelWeb;
import com.lquan.util.Utils;

@Service(value="holidayService")
public class HolidayServiceImpl implements IHolidayService {
	
	private Log log = LogFactory.getLog(HolidayServiceImpl.class);
	
	
	@Autowired
	@Qualifier("commonDAO")
	private CommonDAO commonDao;
	

	/**
	 * 验证数据
	 * @param batchNum
	 * @param tempTable
	 * @param staffWb
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> checkUserData(long batchNum,
			String tempTable, VacationExcelWeb staffWb, String loginName)throws Exception {
	
		
		List<Object[]> args = new ArrayList<Object[]>();
		// 文件头
		String[] titles = staffWb.strArray;
		// 记录行数
		int rowNum = 1;
		// 获得excel中数据map形式，key为表头数据
		List<Map> excelData = staffWb.gainDateForMapList();
		// 记录错误数据
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapObj;
		String userName =""; //运功名称
		String baseDay = ""; // 性别   男性： 1  女性：0
		String spendDay = ""; //分机号
		String restDay = "";// 手机号码
// public static String[] strArray= {"部门编码","姓名","性别","分机号","手机号","邮箱"};
	for (Map map : excelData) {
			// 用户名
			userName = map.get(titles[0]) == null ? null : map.get(titles[0]).toString();
			// 基础天数
			baseDay = map.get(titles[1]) == null ? null : map.get(titles[1]).toString();
			// 当月的请假天数
			spendDay = map.get(titles[2]) == null ? null : map.get(titles[2]).toString();
			// 剩余天数
			restDay = map.get(titles[3]) == null ? null : map.get(titles[3]).toString();

			
			if (userName == null) {
				mapObj = new HashMap<String, Object>();
				mapObj.put("pk_id", rowNum);
				mapObj.put("Result", "员工不能为空");
				list.add(mapObj);
			}
			
			String pw = "";
			try {
				pw = Utils.encryptPassword(Constants.DEFAULT_PW_STRING);
			} catch (Exception e) {
				log.error("默认密码加密错误", e);
			}
			
			Long pk_id = PrimaryKeyGenerator.getLongKey();
			// pk_id,id,userName,baseDay,spendDay,restDay,createBy,createAt,batchNum
			args.add(new Object[] {PrimaryKeyGenerator.getLongKey(),userName, baseDay, spendDay, restDay,loginName});
			rowNum++;
		}
		// 是否有错误数据,有则直接返回给用户
		if (list != null && list.size() > 0) {
			return list;
		}
		
		try{
			// 创建员工管理临时表
			commonDao.update(staffWb.getCreateSql(tempTable).toString());
			log.info("用户管理临时表创建完成");
			// 分别插入数据，由于数据量不大，直接插入，如果数据量太大，可分批次插入
			commonDao.batchUpdate(staffWb.getInsertSql(tempTable, batchNum), args);
			log.info("插入数据至用户管理临时表完成");
			// sql再次验证
/*			StringBuffer sb = new StringBuffer();
			sb = sb.delete(0, sb.length());
			sb.append(" SELECT userName from ");
			sb.append("");
	
			list = commonDao.queryForMapList(sb.toString());*/
		}catch (Exception e) {
			log.error("员工表出错",e);
			commonDao.update("DROP TABLE " + tempTable);
			log.info("导入员工出错，删除员工临时表");
		}
		
		if(list!=null && list.size()>0){
			log.error("导入员工表出错了！！！！！！！！！！！");
			commonDao.update("DROP TABLE " + tempTable);
			log.info("导入员工出错，删除员工临时表");
		}

		return list;
	}

	
	/**
	 * 插入数据
	 * @param tempTable
	 * @param dealerWb
	 * @return
	 * @throws Exception
	 */
	@Override
	public int importData(String tempTable, VacationExcelWeb dealerWb) throws Exception {
		StringBuffer sb = new StringBuffer();
		int result = 0;
		try {
			// 插入数据到条件属性表
			sb.append(" INSERT INTO vacation( pk_id,userName,baseDay,spendDay,restDay,createBy,createAt) ");
			sb.append(" select ").append(VacationExcelWeb.columnsCode);
			sb.append(" FROM ").append(tempTable);
			result = commonDao.update(sb.toString());
			log.info("成功导入数据到正式表！上传流程结束");
		} catch (Exception e) {
			log.error("员工的临时表数据到正式表中出错。", e);
		} finally {
			sb.delete(0, sb.length());
			sb.append(" DROP TABLE ").append(tempTable);
			commonDao.update(sb.toString());
			log.info("删除员工的临时表已完成！！！");
		}
		return result;
	}


	/**
	 * 查询导入年假的信息的页面
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @param title
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@Override
	public PaginationSupport getRuleList(String page, String rows, String sort,String order, String username, String type) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select pk_id,userName,baseDay,spendDay,restDay,createBy,createAt FROM vacation ");
		sql.append(" where 1=1 ");
		if(!"".equals(username)&& username!=null){
			sql.append(" AND userName like '%");
			sql.append(username);
			sql.append("%'");
		}
		
		
		String orderBy = " order by " + sort + " " + order;
		PaginationSupport ps = commonDao.queryForPaginatedMapList(sql.toString(), new Object[]{},new int[]{},new RowSelection(Integer.valueOf(page)-1 ,Integer.valueOf(rows)),orderBy);
		
		if(null != ps){
			return ps;
		}
		return null;
	}

}

package com.lquan.business.staffManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hdgf.chunks.Chunk.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lquan.common.Constants;
import com.lquan.parseExcel.StaffExcelWb;
import com.lquan.util.Utils;

import snt.common.dao.base.CommonDAO;
import snt.common.dao.base.PaginationSupport;
import snt.common.dao.base.PrimaryKeyGenerator;
import snt.common.dao.base.RowSelection;

@Service(value="staffService")
public class StaffServiceImpl implements IStaffService {
	private Log log = LogFactory.getLog(StaffServiceImpl.class);
	
	@Autowired
	@Qualifier("commonDAO")
	private CommonDAO commonDao;
	
	/**
	 * 分页查询员工信息
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @param title
	 * @return
	 */
	@Override
	public PaginationSupport getStaffPage(String page, String rows,String sort, String order, String title,Map<String,String> condition) {
		StringBuffer sb=new StringBuffer();
		sb.append("select s.pk_id,c.company,d.department,d.companyid,d.pk_id depart,s.departmentCode,s.useName,case s.gender when 1 then '男' else '女' end gender,s.telphone,s.celphone,s.email,s.createAt,s.createBy,case s.stat when '1' then '<font color=blue>在职</font>' else  '<font color=red>离职</font>' end state,s.stat from  staffInfo s ");
		sb.append("join department d on s.departmentCode = d.code ");
		sb.append("join company  c on c.pk_id = d.companyid where 1=1 ");
		if(title!=null && !"".equals(title)){
			sb.append("AND fileName like '%").append(title).append("%'");
		}
		if(null!=condition.get("company")&&!"".equals(condition.get("company"))){
			sb.append("AND c.company like '%").append(condition.get("company")).append("%'");
		}
		if(null!=condition.get("depart")&&!"".equals(condition.get("depart"))){
			sb.append("AND d.department like '%").append(condition.get("depart")).append("%'");	
		}
		if(null!=condition.get("isforbid")&&!"".equals(condition.get("isforbid"))){
			sb.append("AND s.stat like '%").append(condition.get("isforbid")).append("%'");
		}

		
		
		
		String orderBy = " order by " + sort + " " + order;
		PaginationSupport ps = commonDao.queryForPaginatedMapList(sb.toString(), new Object[]{},new int[]{},new RowSelection(Integer.valueOf(page)-1 ,Integer.valueOf(rows)),orderBy);
		
		if(null != ps){
			return ps;
		}
		return null;
	}

	
	/**
	 * 添加员工的信息
	 * @param con
	 * @param user
	 * @return
	 */
	@Override
	public boolean addStaff(Map<String, Object> con, String user) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into staffInfo(pk_id,departmentCode,useName,gender,telphone,celphone,email,createAt,createBy,stat ) ");
		sql.append("values(?,?,?,?,?,?,?,getdate(),?,?) ");
		//添加主键
		long pk_id = PrimaryKeyGenerator.getLongKey();
		int stat = 1;
		int a = commonDao.update(sql.toString(),  new Object[] { pk_id,con.get("department"),con.get("username"),con.get("gender"),con.get("telphone"),con.get("cellphone"),con.get("email"),user,stat});
		if(a>0)
			return true ;
		else
			return false;
	}


	/**
	 * 验证数据
	 * @param batchNum
	 * @param tempTable
	 * @param staffWb
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> checkUserData(long batchNum, String tempTable, StaffExcelWb staffWb,String loginName) throws Exception {
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
		/*String log_name = ""; // 登录名
		String user_name = ""; // 用户名
		String genderx = ""; // 性别
		int gender = 0;
		String tel = ""; // 电话
		String mobliephone = ""; // 手机号
		//String fax = ""; // 传真
		String company = ""; // 分公司
		//String postalcode = ""; // 邮政编码
		String email = ""; // 电子邮件
		*/	
		String departmentCode = "";// 部门编码
		String userName =""; //运功名称
		int gender = 1;  //性别  男性： 1  女性：0
		String genderStr = ""; // 性别   男性： 1  女性：0
		String tel = ""; //分机号
		String cellPhone = "";// 手机号码
		String  email = "" ; // 邮箱
// public static String[] strArray= {"部门编码","姓名","性别","分机号","手机号","邮箱"};
	for (Map map : excelData) {
			// 部门编码
			departmentCode = map.get(titles[0]) == null ? null : map.get(titles[0]).toString();
			// 用户名
			userName = map.get(titles[1]) == null ? null : map.get(titles[1]).toString();
			// 性别
			genderStr = map.get(titles[2]) == null ? null : map.get(titles[2]).toString();
			// 分机号
			tel = map.get(titles[3]) == null ? null : map.get(titles[3]).toString();
			// 手机号
			cellPhone = map.get(titles[4]) == null ? null : map.get(titles[4]).toString();
			// 传真
			//fax = map.get(titles[5]) == null ? null : map.get(titles[5]).toString();
			// 电子邮件
			email = map.get(titles[5]) == null ? null : map.get(titles[5]).toString();

			if (departmentCode == null) {
				mapObj = new HashMap<String, Object>();
				mapObj.put("pk_id", rowNum);
				mapObj.put("Result", "部门编码不能为空。");
				list.add(mapObj);
			}
			//TODO 缺少判断所改部门编码是否正确
			
			if (userName == null) {
				mapObj = new HashMap<String, Object>();
				mapObj.put("pk_id", rowNum);
				mapObj.put("Result", "员工不能为空");
				list.add(mapObj);
			}
			if (genderStr == null) {
				mapObj = new HashMap<String, Object>();
				mapObj.put("pk_id", rowNum);
				mapObj.put("Result", "性别不能为空");
				list.add(mapObj);
			} else {
				if (genderStr.equals("男")) {
					gender = 1;
				} else if(genderStr.equals("女")){
					gender = 0;
				}else{
					mapObj = new HashMap<String, Object>();
					mapObj.put("pk_id", rowNum);
					mapObj.put("Result", "性别出错");
					list.add(mapObj);
				}
			}
			
			if (tel == null) {
				mapObj = new HashMap<String, Object>();
				mapObj.put("pk_id", rowNum);
				mapObj.put("Result", "分机号不能为空。");
				list.add(mapObj);
			} 
			
/*			if (cellPhone == null) {
				mapObj = new HashMap<String, Object>();
				mapObj.put("pk_id", rowNum);
				mapObj.put("Result", "手机号码不能为空。");
				list.add(mapObj);
			} else {
				if(cellPhone.indexOf(".")>0&&cellPhone.indexOf("E")>0){
					cellPhone = cellPhone.substring(0,cellPhone.indexOf(".") )+cellPhone.substring(cellPhone.indexOf(".") + 1,cellPhone.lastIndexOf("E"));
					if (cellPhone.length() != 11) {
						mapObj = new HashMap<String, Object>();
						mapObj.put("pk_id", rowNum);
						mapObj.put("Result", "手机号码的格式不正确");
						list.add(mapObj);
					} 
				}else{
					if (cellPhone.length() != 11) {
						mapObj = new HashMap<String, Object>();
						mapObj.put("pk_id", rowNum);
						mapObj.put("Result", "手机号码的格式不正确");
						list.add(mapObj);
					}
				}
			}

			if (email == null) {
				mapObj = new HashMap<String, Object>();
				mapObj.put("pk_id", rowNum);
				mapObj.put("Result", "电子邮件不能为空。");
				list.add(mapObj);
			}*/
			String pw = "";
			try {
				pw = Utils.encryptPassword(Constants.DEFAULT_PW_STRING);
			} catch (Exception e) {
				log.error("默认密码加密错误", e);
			}
			
			
			int state = 1;
			args.add(new Object[] { PrimaryKeyGenerator.getLongKey(), departmentCode,userName, gender, tel, cellPhone,email,loginName,state});
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
			StringBuffer sb = new StringBuffer();
			sb = sb.delete(0, sb.length());
			sb.append(" SELECT pk_id,'数据库中不存在该部门编码' as Result FROM ").append(tempTable);
			sb.append(" WHERE departmentCode NOT IN ( SELECT code FROM department)");
			sb.append(" union all");
			sb.append(" SELECT pk_id,'数据库中已存在该用户名' as Result FROM ").append(tempTable);
			sb.append(" WHERE useName  IN ( select useName FROM staffInfo)");
			/*sb.append(" union all");
			sb.append(" SELECT pk_id,'数据库中已存在该email地址' as Result FROM ").append(tempTable);
			sb.append("  WHERE email  IN ( select email from staffInfo)");*/
	
			list = commonDao.queryForMapList(sb.toString());
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
	 * 插入数据,数据没有问题，将数据插入到正式表中
	 */
	@Override
	public int importData(String tempTable, StaffExcelWb staffWb)throws Exception {
		StringBuffer sb = new StringBuffer();
		int result = 0;
		try {
			// 插入数据到条件属性表
			sb.append(" INSERT INTO staffInfo(pk_id,departmentCode,useName,gender,telphone,celphone,email,createBy,stat,createAt) ");
			sb.append(" select ").append(staffWb.columnsCode);
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
	 * 获取所有已经有头像的用户信息
	 * @param company
	 * @param departMent
	 * @return
	 */
	@Override
	public List<Object[]> getStaffHeadImg(String company,String departMent) {
		StringBuffer sql = new StringBuffer();
		sql.append("select headImg from  staffInfo where headImg is not null");
		return commonDao.queryForSimpObjList(sql.toString(), null);
	}
	
	/**
	 * 验证导入的文件
	 * @param fileName
	 * @return
	 */
	@Override
	public boolean valudateFileFormat(String fileName){
		String suffix =  fileName.substring(fileName.lastIndexOf(".")+1,fileName.length() );
		if("JPG".equalsIgnoreCase(suffix) || "png".equalsIgnoreCase(suffix) || "jpeg".equalsIgnoreCase(suffix)){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 上传员工的照片
	 * @param staffParamsList
	 * @return
	 */
	@Override
	public int[] updateStaffHeadImg(List<Object[]> staffParamsList){
		String sql = "update staffInfo set headImg=? where useName=?";
		int a[] = commonDao.batchUpdate(sql, staffParamsList);
		return a;
	}
	
	
	/**
	 * 检查通过文件得到的经销商Id， 是否存在。
	 * @param dealerId
	 * @return
	 */
	@Override
	public boolean checkStaffName(String userName) {
		boolean dealerIdMatch = false;
		StringBuffer sql = new StringBuffer();
		sql.append("select useName From staffInfo where useName='");
		sql.append(userName);
		sql.append("'");
		List<Map<String, Object>> list = commonDao.queryForMapList(sql.toString());
		if(null!=list&&list.size()>0 )
			 dealerIdMatch = true;
		
		return dealerIdMatch;
	}
	
	/**
	 * 更改员工的在职状态
	 * @param pk_id
	 * @param type
	 * @return
	 */
	@Override
	public boolean forbidStaff(String pk_id, Integer type) {
		StringBuffer sql = new StringBuffer();
		sql.append("update staffInfo set stat='").append(Math.abs(type-1)).append("' ");
		sql.append("where pk_id=").append(pk_id);
		int count = commonDao.update(sql.toString(), new Object[]{});
		if(count>0)
			return true;
		else
			return false;
	}
	
	
	/**
	 * 更改员工的在职状态
	 * @param pk_id
	 * @param type
	 * @return
	 */
	@Override
	public boolean deleStaff(String pk_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from staffInfo ");
		sql.append(" where pk_id=").append(pk_id);
		int count = commonDao.update(sql.toString(), new Object[]{});
		if(count>0)
			return true;
		else
			return false;
	}

	/**
	 * 修改用户信息
	 * @param con
	 * @param user
	 * @return
	 */
	@Override
	public boolean editStaff(Map<String, Object> con, String user) {
		StringBuffer sql = new StringBuffer();
		sql.append("update staffInfo set departmentCode=?,useName=?,gender=?,telphone=?,celphone=?,email=? ");
		sql.append(" where pk_id=").append(con.get("staffId"));
		int a = commonDao.update(sql.toString(),  new Object[] {con.get("department"),con.get("username"),con.get("gender"),con.get("telphone"),con.get("cellphone"),con.get("email")});
		if(a>0)
			return true ;
		else
			return false;
	}

}

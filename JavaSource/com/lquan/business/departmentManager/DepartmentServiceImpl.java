package com.lquan.business.departmentManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import snt.common.dao.base.CommonDAO;
import snt.common.dao.base.PrimaryKeyGenerator;

/**
 * 实现员工与公司的接口
 * @author lquan
 *
 */
@Service(value="departmentService")
public class DepartmentServiceImpl implements IDepartmentService {

	//获取数据库的链接
	@Autowired
	@Qualifier("commonDAO")
	private CommonDAO commonDao;
	
	/**
	 * 显示上传模板类型
	 */
	@Override
	public List<Map<String, Object>>  getCompanyAndDepartmentList()throws Exception {
		
		List<Map<String, Object>> modelList = getCompanyList();
		List<Map<String, Object>> typeList = getDepartmentList();
		for (Map<String, Object> modelMap:modelList) {
			List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> typeMap:typeList) {
				if(modelMap.get("pk_id").toString().equals(typeMap.get("companyId").toString())){
					tempList.add(typeMap);
				}
			}
			modelMap.put("typeList", tempList);
		}
		return modelList;
	}
	
	/**
	 * 获取公司的
	 * @return
	 */
	@Override
	public List<Map<String, Object>>  getCompanyList(){
		StringBuffer sql = new StringBuffer();
		sql.append("select pk_id,company from dbo.company");
		List<Map<String, Object>> list = commonDao.queryForMapList(sql.toString());
		return list;
	}
	/**
	 * 获取部门的信息
	 * @return
	 */
	private List<Map<String, Object>>  getDepartmentList(){
		StringBuffer sql = new StringBuffer();
		sql.append("select pk_id,companyId,department,code,createAt,createBy from department ");
		List<Map<String, Object>> list = commonDao.queryForMapList(sql.toString());
		return list;
	}
	
	/**
	 * 添加公司
	 */
	@Override
	public boolean addCompany(String company,String user) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into company(pk_id,company,createBy,createAt) values(?,?,?,getdate()) ");
		//添加主键
		long pk_id = PrimaryKeyGenerator.getLongKey();
		int a = commonDao.update(sql.toString(),  new Object[] { pk_id, company,user});
		if(a>0)
			return true ;
		else
			return false;
	}
	
	/**
	 * 编辑文件的文件类型
	 * @param title
	 * @return
	 */
	@Override
	public boolean editCompany(String companyId, String company){
		StringBuffer sql = new StringBuffer();
		sql.append("update company set company='");
		sql.append(company);
		sql.append("' where pk_id=");
		sql.append(companyId);
		int a = commonDao.update(sql.toString(),  new Object[] {});
		if(a>0)
			return true ;
		else
			return false;
	}
	
	/**
	 * 查询公司下面的部门
	 * @param pk_id
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> queryDepartment(String pk_id) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select pk_id,department ,companyId,code from department  ");
		if(pk_id!=null&&!"".equals(pk_id)){
			sb.append(" WHERE companyId = '").append(pk_id).append("' ");
		}
		List<Map<String,Object>> list = commonDao.queryForMapList(sb.toString()) ;
		return list;
	}
	
	/**
	 * 删除文件的文件类型
	 * @param pk_id
	 * @return
	 */
	@Override
	public boolean delComany(String pk_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from company where pk_id=?");
		int a = commonDao.update(sql.toString(),  new Object[] { pk_id});
		if(a>0)
			return true ;
		else
			return false;
	}

	/**
	 * 添加部门
	 * @param companyId
	 * @param departmentName
	 * @return
	 */
	@Override
	public boolean addDepartment(String companyId, String departmentName,String code,String user) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into department(pk_id,companyId,department,Code,createBy,createAt) values(?,?,?,?,?,getdate()) ");
		//添加主键
		long pk_id = PrimaryKeyGenerator.getLongKey();
		int a = commonDao.update(sql.toString(),  new Object[] { pk_id, companyId,departmentName,code,user});
		if(a>0)
			return true ;
		else
			return false;
	}
	
	/**
	 * 编辑文件的文件类型
	 * @param title
	 * @return
	 */
	@Override
	public boolean editDepartment(String departmentId, String department){
		StringBuffer sql = new StringBuffer();
		sql.append("update department set department='");
		sql.append(department);
		sql.append("' where pk_id=");
		sql.append(departmentId);
		int a = commonDao.update(sql.toString(),  new Object[] {});
		if(a>0)
			return true ;
		else
			return false;
	}
	
	/**
	 * 删除部门信息
	 */
	@Override
	public boolean delDepartment(String pk_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from department where pk_id=?");
		int a = commonDao.update(sql.toString(),  new Object[] { pk_id});
		if(a>0)
			return true ;
		else
			return false;
	}
	
	
}

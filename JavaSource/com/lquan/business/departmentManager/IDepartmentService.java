package com.lquan.business.departmentManager;

import java.util.List;
import java.util.Map;

public interface IDepartmentService {
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>>  getCompanyAndDepartmentList()throws Exception;
	/**
	 * 添加分公司
	 * @param title
	 * @return
	 */
	public boolean addCompany(String company,String user);
	
	/**
	 * 编辑公司信息
	 * @param modelId
	 * @param title
	 * @return
	 */
	public boolean editCompany(String modelId, String title);
	
	/**
	 * 查询公司下面的部门
	 * @param pk_id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryDepartment(String pk_id) throws Exception;
	
	/**
	 * 删除公司
	 * @param pk_id
	 * @return
	 */
	public boolean delComany(String pk_id);
	
	/**
	 * 获取公司列表
	 * @return
	 */
	public List<Map<String, Object>>  getCompanyList();
	
	/**
	 * 添加部门
	 * @param companyId
	 * @param departmentName
	 * @return
	 */
	public boolean addDepartment(String companyId,String departmentName,String code,String user);
	
	/**
	 * 编辑部门
	 * @param departmentId
	 * @param department
	 * @return
	 */
	public boolean editDepartment(String departmentId, String department);
	
	/**
	 * 删除部门信息
	 * @param pk_id
	 * @return
	 */
	boolean delDepartment(String pk_id);
	
	
	

}

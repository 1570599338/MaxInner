package com.lquan.business.staffManager;

import java.util.List;
import java.util.Map;

import com.lquan.parseExcel.StaffExcelWb;

import snt.common.dao.base.PaginationSupport;

public interface IStaffService {
	
	/**
	 * 分页查询员工信息
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @param title
	 * @return
	 */
	public PaginationSupport getStaffPage(String page, String rows, String sort,String order, String title,Map<String,String> condition) ;
	
	
	/**
	 * 添加员工的信息
	 * @param con
	 * @param user
	 * @return
	 */
	public boolean addStaff(Map<String,Object> con,String user);
	
	/**
	 * 验证数据
	 * @param batchNum
	 * @param tempTable
	 * @param staffWb
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> checkUserData(long batchNum,String tempTable, StaffExcelWb staffWb,String loginName) throws Exception;
	
	/**
	 * 插入数据
	 * @param tempTable
	 * @param dealerWb
	 * @return
	 * @throws Exception
	 */
	public int importData(String tempTable, StaffExcelWb dealerWb)throws Exception;
	
	/**
	 * 获取所有已经有头像的用户信息
	 * @param company
	 * @param departMent
	 * @return
	 */
	public List<Object[]> getStaffHeadImg(String company,String departMent);
	
	/**
	 * 验证导入的文件类型
	 * @param fileName
	 * @return
	 */
	public boolean valudateFileFormat(String fileName);
	
	/**
	 * 上传员工的照片
	 * @param staffParamsList
	 * @return
	 */
	public int[] updateStaffHeadImg(List<Object[]> staffParamsList);
	
	/**
	 * 上传的员工的名字
	 * @param userName
	 * @return
	 */
	public boolean checkStaffName(String userName);
	
	/**
	 * 更改员工的在职状态
	 * @param pk_id
	 * @param type
	 * @return
	 */
	public boolean forbidStaff(String pk_id, Integer type);
	
	/**
	 * 删除员工信息
	 * @param pk_id
	 * @return
	 */
	public boolean deleStaff(String pk_id);
	
	/**
	 * 修改用户信息
	 * @param con
	 * @param user
	 * @return
	 */
	public boolean editStaff(Map<String, Object> con, String user);
}

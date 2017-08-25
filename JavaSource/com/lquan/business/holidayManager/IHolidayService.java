package com.lquan.business.holidayManager;

import java.util.List;
import java.util.Map;

import snt.common.dao.base.PaginationSupport;

import com.lquan.parseExcel.StaffExcelWb;
import com.lquan.parseExcel.VacationExcelWeb;

public interface IHolidayService {
	
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
	public PaginationSupport getRuleList(String page, String rows, String sort,String order, String title,String type) throws Exception;
	
	/**
	 * 验证数据
	 * @param batchNum
	 * @param tempTable
	 * @param staffWb
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> checkUserData(long batchNum,String tempTable, VacationExcelWeb staffWb,String loginName) throws Exception;
	
	/**
	 * 插入数据
	 * @param tempTable
	 * @param dealerWb
	 * @return
	 * @throws Exception
	 */
	public int importData(String tempTable, VacationExcelWeb dealerWb)throws Exception;
	

}

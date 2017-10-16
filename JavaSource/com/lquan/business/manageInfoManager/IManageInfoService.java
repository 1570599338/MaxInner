package com.lquan.business.manageInfoManager;

import java.util.List;
import java.util.Map;

import snt.common.dao.base.PaginationSupport;

/**
 * @Description 描述经营管理信息
 * @author liuquan
 *
 */
public interface IManageInfoService {

	/**
	 * 添加经营管理信息
	 * @param title 标题
	 * @param message 信息
	 * @param messageFormat 有格式的信息
	 * @return 返回值是boolean类型
	 */
	public boolean addManageInfo(String title,String message, String messageFormat);
	
	/**
	 * 修改经营管理信息
	 * @param pk_id 主键
	 * @param title 标题
	 * @param message 信息内容
	 * @param messageFormat 有html代码的信息内容
	 * @return 返回boolean值
	 */
	public boolean editManageInfo(String pk_id,String title, String message,String messageFormat);
	
	/**
	 * 删除经营管理信息
	 * @param pk_id
	 * @return
	 */
	public boolean delManageInfo(String pk_id);
	
	/**
	 * 显示经营管理信息的数据列表
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @param title
	 * @return
	 */
	public PaginationSupport manageList(String page, String rows, String sort,String order, String title);
	
	/**
	 * 获取经营管理信息
	 * @param pk_id
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryOneManage(String pk_id) throws Exception;
	
	/**
	 * 查询经营管理信息
	 * @param pk_id
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryOneManageOrder(String pk_id,String stat) throws Exception;
	
	
	/**
	 * 图书显示
	 * @param companyid
	 * @param departMentCode
	 * @param gender
	 * @param userName
	 * @param telPhone
	 * @param path
	 * @return
	 */
	public List<Map<String, Object>>  getBookList(String companyid,String departMentCode,String gender,String userName,String telPhone,String path);
	
	
	/**
	 * 查询预定图书
	 * @param pk_id
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryBookOne(String pk_id) throws Exception;
}









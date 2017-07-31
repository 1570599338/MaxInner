package com.lquan.business.ruleManager;

import snt.common.dao.base.PaginationSupport;

public interface IRuleServer {
	
	/**
	 * 对查询的数据进行分页处理
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @param title
	 * @return
	 */
	public PaginationSupport getRule(String page, String rows, String sort,String order, String title) ;
	
	/**
	 * 
	 * @param tourID
	 * @param fileName	      文件的名称
	 * @param fileAllName 文件的全名称:aa.pdf
	 * @param Message
	 * @param userName
	 * @return
	 */
	public boolean updateImg(int type,String fileName,String fileAllName,String userName);
	
	/**
	 * 删除上传的文件
	 * @param id
	 * @return
	 */
	public boolean deleRule(String id);
	
	/**
	 * 添加new图标
	 * @param id
	 * @return
	 */
	public boolean addFlageNew(String id);
	
	/**
	 * 删除new图标
	 * @param id
	 * @return
	 */
	public boolean delFlageNew(String id);

}




















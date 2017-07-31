package com.lquan.business.activeManager;

import java.util.Map;

import snt.common.dao.base.PaginationSupport;

public interface IActiveManagerServer {

	/**
	 * @descripion 分页数据显示
	 * @param page 页数
	 * @param row  行数
	 * @return
	 */
	public PaginationSupport getTour(String page,String row,String sort,String order,String title);

	/**
	 * @descripion 		添加摘要的信息 
	 * @param title		摘要信息标题
	 * @param message	摘要信息内容
	 * @return			返回插入数据是否成功
	 */
	public boolean addAdTour(String title, String message);
	
	/**
	 * 
	 * @param pk_id
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryActive(String pk_id) throws Exception;
	
	/**
	 * 查询对应的某一事件
	 * @param pk_id
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryOneActive(String pk_id) throws Exception;
	
	/**
	 * 保存修改活动信息
	 * @param title
	 * @param message
	 * @param content
	 * @return
	 */
	public boolean editSaveActive(String title, String message,String content);
	
	/**
	 * 删除活动展示
	 * @param pk_id
	 * @return
	 */
	public boolean deleteActivity(String pk_id);
	
	/**
	 * 上传活动展示的封面
	 * @param tourID
	 * @param fileName
	 * @return
	 */
	public boolean updateImg(long activitypkId, String fileName);
	
	
	/**
	 * 添加new图标
	 * @param articleid
	 * @return
	 */
	public boolean addFlageNew(String articleid);
	
	/**
	 * 去掉new图标
	 * @param articleid
	 * @return
	 */
	public boolean delFlageNew(String articleid);
}

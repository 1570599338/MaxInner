package com.lquan.business.noticeManager;

import java.util.List;
import java.util.Map;

import snt.common.dao.base.PaginationSupport;

public interface INoticeManageService {
	
	/**
	 * @descripion 获取当前的公告信息
	 * @return list
	 * @throws Exception
	 */
	public List<Map<String, Object>> noticelist();
	
	/**
	 * @descripion 分页数据显示
	 * @param page 页数
	 * @param row  行数
	 * @return
	 */
	public PaginationSupport getNotice(String page,String row,String sort,String order,String title);

	/**
	 * @descripion 		添加公告的信息 
	 * @param title		公告信息标题
	 * @param message	公告信息内容
	 * @return			返回插入数据是否成功
	 */
	public boolean addAdNotice(String title,String message);
	
	/**
	 * @Description		修改公告的信息
	 * @param id		公告的主键id
	 * @param title		公告的标题
	 * @param message	公告的内容
	 * @return			修改公告是否成功
	 */
	public boolean editAdNotice(Long id ,String title,String message);
	
	/**
	 * @descripion 删除公告信息
	 * @param id	公告信息的主键	
	 * @return		返回删除是否成功
	 */
	public boolean delNotice(String id);
	
	/**
	 * 获取当前的查询结果
	 * @param pk_id
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryNoticeType(String pk_id) throws Exception;
	
	/**
	 * 添加new图标
	 * @param id
	 * @return
	 */
	public boolean addFlageNew(Long id);
	
	/**
	 * 添加new图标
	 * @param id
	 * @return
	 */
	public boolean delFlageNew(Long id);
}

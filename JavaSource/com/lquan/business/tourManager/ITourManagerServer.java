package com.lquan.business.tourManager;

import java.util.List;
import java.util.Map;

import snt.common.dao.base.PaginationSupport;

/**
 * @Descript 活动展示的模块的借口
 * @author liuquan
 *
 */
public interface ITourManagerServer {
	
	/**
	 * @descripion 分页数据显示
	 * @param page 页数
	 * @param row  行数
	 * @return
	 */
	public PaginationSupport getTour(String page,String row,String sort,String order,String title);

	/**
	 * @descripion 		添加公告的信息 
	 * @param title		公告信息标题
	 * @param message	公告信息内容
	 * @return			返回插入数据是否成功
	 */
	public boolean addAdTour(String title,String message);
	
	/**
	 * @Description		修改公告的信息
	 * @param id		公告的主键id
	 * @param title		公告的标题
	 * @param message	公告的内容
	 * @return			修改公告是否成功
	 */
	public boolean editAdTour(Long id ,String title,String message);
	
	/**
	 * @descripion 删除公告信息
	 * @param id	公告信息的主键	
	 * @return		返回删除是否成功
	 */
	public boolean delTour(String id);
	
	/**
	 * 上传的图片
	 * @param tourID	活动事件的主键
	 * @param fileName	上传文件的文件名称
	 * @param Message	图片的描述信息
	 * @return boolean  上传成功返回true,反之false
	 */
	public boolean updateImg(long tourID,String fileName,String Message,String userName);
	
	/**
	 * 
	 * @param pk_id
	 * @param basePath
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getActiveImg(String pk_id,String basePath) throws Exception;

}

package com.lquan.business.meetManager;

import java.util.List;
import java.util.Map;

import snt.common.dao.base.PaginationSupport;

public interface IMeetServer {
	
	/**
	 * 预定会议室
	 * @param title
	 * @param cont
	 * @return
	 */
	public boolean bookMeet(String title, Map<String,Object> cont);
	
	/**
	 * 展示会议系统的数据
	 * @param dateTime
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getbookMeet(Integer startTime,Integer endTime, String dateTime,String companyId) throws Exception;

	/**
	 * 验证是否有重复时间段的
	 * @param meetID
	 * @param bookTime
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Map<String, Object>> checkMeet(String meetID,String bookTime,String startTime ,String endTime);
	
	/**
	 * 
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @param title
	 * @return
	 */
	public PaginationSupport getCanelMeet(String page,String rows,String sort,String order,String meetId,String dateTime);
	
	/**
	 * 取消会议室预定
	 * @param pk_id
	 * @return
	 */
	public boolean deltebookMeet(String pk_id);
	
	/**
	 * 获取会议室预定的会议室
	 */
	public Map<String, Object> getOnebookMeetInfo(String pk_id) throws Exception;
	
	/**
	 * 对修改会议室的验证
	 * @param bookMeetId
	 * @param meetID
	 * @param bookTime
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Map<String, Object>> checkMeetUpdate(String bookMeetId, String meetID, String bookTime,String startTime, String endTime);
	
	
	/**
	 * 修改会议室系统
	 * @param user
	 * @param cont
	 * @return
	 */
	public boolean editBookMeet(String user, Map<String,Object> cont);
	
	/**
	 * 到处预定的会议室的数据
	 * @param bookName
	 * @param meet
	 * @param bookassist
	 * @param bookTime
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Map<String, Object>> downLoadBookMeetList(String bookName,String meet, long bookassist, String bookTime,String startTime, String endTime);
	
}

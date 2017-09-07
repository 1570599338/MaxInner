package com.lquan.business.meetManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import snt.common.dao.base.CommonDAO;
import snt.common.dao.base.PaginationSupport;
import snt.common.dao.base.PrimaryKeyGenerator;
import snt.common.dao.base.RowSelection;

@Service(value="meetServer")
public class MeetServerimpl implements IMeetServer {
	
	@Autowired
	@Qualifier("commonDAO")
	private CommonDAO commonDao;
	/**
	 * 添加预定的用户的信息
	 */
	public boolean bookMeet(String user, Map<String,Object> cont) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("insert into dbo.bookmeet(pk_id,meetId,bookDate,startTime,endTime,assist,booker,remark,createAt,createBy,type) values(?,?,?,?,?,?,?,?,getdate(),?,?) ");
		//添加主键
		long pk_id = PrimaryKeyGenerator.getLongKey();
		int head = commonDao.update(sql.toString(),  new Object[] { pk_id,cont.get("meetId"),cont.get("bookDate"),
			cont.get("startTime"),cont.get("endTime"),cont.get("assist"),cont.get("booker"),cont.get("remark"),user,cont.get("type")});
		if( head>0)
			return true ;
		else
			return false;
	}
	
	/**
	 * 修改会议室系统
	 * @param user
	 * @param cont
	 * @return
	 */
	public boolean editBookMeet(String user, Map<String,Object> cont) {
		StringBuffer sql = new StringBuffer();
		sql.append("update dbo.bookmeet  set meetId=?,bookDate=?,startTime=?,endTime=?,assist=?,booker=?,remark=?,createAt=getdate(),createBy=? where pk_id=? ");
		//添加主键
		long pk_id = PrimaryKeyGenerator.getLongKey();
		int head = commonDao.update(sql.toString(),  new Object[] {cont.get("meetId"),cont.get("bookDate"),
			cont.get("startTime"),cont.get("endTime"),cont.get("assist"),cont.get("booker"),cont.get("remark"),user,cont.get("bookMeetId")});
		if( head>0)
			return true ;
		else
			return false;
	}
	
	/**
	 * 获取活动展示的照片以及照片简介
	 * @param pk_id
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> getbookMeet(Integer startTime,Integer endTime,String dateTime,String companyId) throws Exception {
		List<Map<String, Object>> getMeet = getMeet(companyId);
		List<Map<String, Object>> getMeetInfo = getbookMeetInfo(dateTime,companyId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("meet", getMeet);
		map.put("meetinfo", getMeetInfo);
		return map;
	}
	
	/**
	 * 获取会议室
	 * @return
	 */
	public List<Map<String, Object>> getMeet(String companyId){
		StringBuffer sql = new StringBuffer();
		sql.append(" select pk_id,name from meet where 1=1  ");
		if(companyId!=null && !"".equals(companyId)){
			sql.append(" And type='").append(companyId).append("'");
		}
		List<Map<String, Object>> list = commonDao.queryForMapList(sql.toString());
		return list;
	}
	
	public List<Map<String, Object>> getbookMeetInfo(String dateTime,String companyId) throws Exception {
		StringBuffer sql = new StringBuffer();
		//sql.append(" select pk_id,meetId,bookDate,startTime,endTime,assist,booker,remark,createAt,createBy from bookmeet where 1=1  ");
		//sql.append(" select pk_id,meetId,bookDate,startTime,endTime,REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(assist,',0,',','),',0',''),'0,',','),',,',','),'1','三方'),'2','电话'),'3','投影仪') assist,booker,remark,createAt,createBy from bookmeet where 1=1  ");
		sql.append(" select pk_id,meetId,bookDate,startTime,endTime,REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(assist,',0,0',''),'0,0,',''),',0,',','),'0,',''),',0',''),'1','三方'),'2','电话'),'3','投影仪') assist,booker,remark,createAt,createBy from bookmeet where 1=1  ");
		if(companyId!=null && !"".equals(companyId)){
			sql.append(" And type='").append(companyId).append("'");
		}
		if(dateTime!=null){
			sql.append(" And bookDate='").append(dateTime).append("'");
		}
		List<Map<String, Object>> list = commonDao.queryForMapList(sql.toString());
		return list;
	}

	/**
	 * 验证是否有重复时间段的
	 * @param meetID
	 * @param bookTime
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public List<Map<String, Object>> checkMeet(String meetID, String bookTime,String startTime, String endTime) {
		StringBuffer sql = new StringBuffer();
		//sql.append("select pk_id,meetId,startTime,endTime FROM dbo.bookMeet WHERE 1=1  ");
		sql.append("select pk_id,meetId,convert(float,startTime) startTime,convert(float,endTime) endTime FROM dbo.bookMeet WHERE 1=1 ");
		if(meetID!=null && !"".equals(meetID)){
			sql.append(" AND MEETID='").append(meetID).append("'");
		}
		if(meetID!=null && !"".equals(meetID)){
			sql.append(" AND bookDate='").append(bookTime).append("'");
		}
		sql.append(" AND(( convert(float,startTime)<= ").append(startTime).append(" and convert(float,endTime) >").append(startTime).append(" ) ");
		sql.append(" OR ( convert(float,startTime)< ").append(endTime).append(" and convert(float,endTime) >=").append(endTime).append(" ))");
		List<Map<String, Object>> list = commonDao.queryForMapList(sql.toString());
		return list;
	}
	
	/**
	 * 获取取消会议的记录数据
	 */
	@Override
	public PaginationSupport getCanelMeet(String page,String rows,String sort,String order,String meetId,String dateTime) {
		StringBuffer sb=new StringBuffer();
		//sb.append("select bm.pk_id,m.name,bm.meetid,bm.booker,bm.assist bookassist,case when bm.assist=1 then '三方' when bm.assist=2 then '电话' when bm.assist=3 then '投影' else '无' end assist,CONVERT(varchar(100), bm.bookdate, 23 ) bootime,bm.startTime,bm.endTime,bm.remark from  bookMeet bm join meet m on m.pk_id = bm.meetid where 1=1 ");
		sb.append("select bm.pk_id,m.name,bm.meetid,bm.booker,bm.assist bookassist,REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(assist,',0,0',''),'0,0,',''),',0,',','),'0,',''),',0',''),'1','三方'),'2','电话'),'3','投影仪') assist,CONVERT(varchar(100), bm.bookdate, 23 ) bootime,bm.startTime,bm.endTime,bm.remark from  bookMeet bm join meet m on m.pk_id = bm.meetid where 1=1 ");
		if(meetId!=null && !"".equals(meetId)&& !"0".equals(meetId)){
			sb.append("AND m.pk_id = ").append(meetId);
		}
		if(dateTime!=null && !"".equals(dateTime)){
			sb.append("AND bm.bookdate='").append(dateTime).append("'");
		}
		String orderBy = " order by " + sort + " " + order;
		PaginationSupport ps = commonDao.queryForPaginatedMapList(sb.toString(), new Object[]{},new int[]{},new RowSelection(Integer.valueOf(page)-1 ,Integer.valueOf(rows)),orderBy);
		
		if(null != ps){
			return ps;
		}
		return null;
	}
	

	/**
	 * 删除预定的数据
	 */
	public boolean deltebookMeet(String pk_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from dbo.bookMeet where pk_id=").append(pk_id);
		//添加主键
		int head = commonDao.update(sql.toString());
		if( head>0)
			return true ;
		else
			return false;
	}

	@Override
	public Map<String, Object> getOnebookMeetInfo(String pk_id)throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select pk_id,meetid,CONVERT(varchar(100), bookDate, 23 ) bootime ,startTime ,endTime,assist bookassist,booker,remark,createAt,createBy from bookmeet where 1=1  ");
		if(pk_id!=null){
			sql.append(" And pk_id=").append(pk_id);
		}
		List<Map<String, Object>> list = commonDao.queryForMapList(sql.toString());
		if(null!=list){
			return list.get(0);
		}
		
		return null;
	}
	
	/**
	 * 对修改会议室的验证
	 * @param bookMeetId
	 * @param meetID
	 * @param bookTime
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public List<Map<String, Object>> checkMeetUpdate(String bookMeetId, String meetID, String bookTime,String startTime, String endTime) {
		StringBuffer sql = new StringBuffer();
		sql.append("select pk_id,meetId,startTime,endTime FROM dbo.bookMeet WHERE 1=1  ");
		if(meetID!=null && !"".equals(meetID)){
			sql.append(" AND MEETID='").append(meetID).append("'");
		}
		if(bookTime!=null && !"".equals(bookTime)){
			sql.append(" AND bookDate='").append(bookTime).append("'");
		}
		if(bookMeetId!=null && !"".equals(bookMeetId)){
			sql.append(" AND pk_id not in ('").append(bookMeetId).append("')");
		}
		sql.append(" AND(( convert(float,startTime)<= ").append(startTime).append(" and convert(float,endTime) >").append(startTime).append(" ) ");
		sql.append(" OR ( convert(float,startTime)< ").append(endTime).append(" and convert(float,endTime) >=").append(endTime).append(" ))");
		/*sql.append(" AND(( startTime<= ").append(startTime).append(" and endTime >=").append(startTime).append(" ) ");
		sql.append(" OR ( startTime<= ").append(endTime).append(" and endTime >=").append(endTime).append(" ))");*/
		List<Map<String, Object>> list = commonDao.queryForMapList(sql.toString());
		return list;
	}

	/**
	 * 到处预定的会议室的数据
	 * @param bookName
	 * @param meet
	 * @param assist
	 * @param bookTime
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public List<Map<String, Object>> downLoadBookMeetList(String bookName,String meet, long assist, String bookTime, String startTime,String endTime) {
		//String bmassist="REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(bm.assist,',0,',','),',0',''),'0,',','),',,',','),'1','三方'),'2','电话'),'3','投影仪')";
		String bmassist="REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(assist,',0,0',''),'0,0,',''),',0,',','),'0,',''),',0',''),'1','三方'),'2','电话'),'3','投影仪')";
		if(assist==1)
			bmassist="'三方'";
		if(assist==2)
			bmassist="'电话'";
		if(assist==3)
			bmassist="'投影仪'";
		
		StringBuffer sql = new StringBuffer();
		sql.append("select m.name,CONVERT(varchar(100),  bm.bookDate, 23 ) bookDate,REPLACE(bm.startTime+':00','.5:00',':05') startTime,REPLACE(bm.endTime+':00','.5:00',':05') endTime,bm.booker,");
		sql.append("Case when ");
		sql.append(bmassist).append("='0' ").append(" then '无' else ").append(bmassist).append(" end assistx");
		
		sql.append(",bm.remark from dbo.bookMeet bm left join dbo.meet m on m.pk_id=bm.meetId WHERE 1=1  ");
		// 会议室
		if(meet!=null&&!"".equals(meet)&&!"0".equals(meet)){
			sql.append(" and m.pk_id=").append(meet);
		}
		// 预定日期
		if(bookTime!=null&&!"".equals(bookTime)){
			sql.append(" and bookDate='").append(bookTime).append("'");
		}
		// 开始时间
		if(startTime!=null&&!"".equals(startTime)){
			sql.append(" AND convert(float,bm.startTime)>= ").append(startTime);
		}
		// 结束时间
		if(endTime!=null&&!"".equals(endTime)){
			sql.append(" AND convert(float,bm.endTime)<= ").append(endTime);
		}
		// 辅助设备
		if(assist!=0){
			sql.append(" and bm.assist like'").append(assist).append("%'");
		}
		// 预定人
		if(bookName!=null&&!"".equals(bookName)){
			sql.append(" and bm.booker like '%").append(bookName).append("%'");
		}
		
		/*sql.append(" AND(( startTime<= ").append(startTime).append(" and endTime >=").append(startTime).append(" ) ");
		sql.append(" OR ( startTime<= ").append(endTime).append(" and endTime >=").append(endTime).append(" ))");*/
		List<Map<String, Object>> list = commonDao.queryForMapList(sql.toString());
		return list;
	}
	
}

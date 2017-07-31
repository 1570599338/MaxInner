package com.lquan.business.activeManager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import snt.common.dao.base.CommonDAO;
import snt.common.dao.base.PaginationSupport;
import snt.common.dao.base.PrimaryKeyGenerator;
import snt.common.dao.base.RowSelection;


@Service(value="activeManagerServer")
public class ActiveManagerServerImpl implements IActiveManagerServer {

	@Autowired
	@Qualifier("commonDAO")
	private CommonDAO commonDao;

	/**
	 * @descripion 分页数据显示
	 * @param page 页数
	 * @param row  行数
	 * @return
	 */
	@Override
	public PaginationSupport getTour(String page, String rows, String sort,String order, String title) {
		StringBuffer sb=new StringBuffer();
		//sb.append("select pk_id,title,content,CONVERT(varchar(100), tourTime, 23) tourTime from tour where 1=1 ");
		sb.append("select at.pk_id,case when at.filePath is not null then '有' else '无' end filePath ,CONVERT(varchar(100), at.createAt, 23) time,a.activityId,at.createBy,a.pk_id articleId,case when at.flage_new =1 then '<img alt= style=display: inline; src=../img/new/new2.gif>' else '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' end +''+at.title as title from activity_title at ");
		sb.append("join article a on a.activityId=at.pk_id ");
		sb.append("Where 1=1");
		
		if(title!=null && !"".equals(title)){
			sb.append("AND at.title like '%").append(title).append("%'");
		}
		String orderBy = " order by  at.createAt " + order;
		PaginationSupport ps = commonDao.queryForPaginatedMapList(sb.toString(), new Object[]{},new int[]{},new RowSelection(Integer.valueOf(page)-1 ,Integer.valueOf(rows)),orderBy);
		
		if(null != ps){
			return ps;
		}
		return null;
	}
		

	/**
	 * @descripion 		添加摘要的信息 
	 * @param title		摘要信息标题
	 * @param message	摘要信息内容
	 * @return			返回插入数据是否成功
	 */
	@Override
	@Transactional
	public boolean addAdTour(String title, String message) {
		title = title==null? "Sorry忘了写标题了！":title;
		StringBuffer sql = new StringBuffer();
		sql.append("insert into dbo.activity_title(pk_id,title,createAt,createBy) values(?,?,getdate(),?) ");
		//添加主键
		long pk_id = PrimaryKeyGenerator.getLongKey();
		int head = commonDao.update(sql.toString(),  new Object[] { pk_id, title,"admin"});
		
		StringBuffer sb = new StringBuffer();
		sb.append("insert into dbo.article(pk_id,activityId,content,createAt,createBy) values(?,?,?,getdate(),?) ");
		//添加主键
		long pkid_title = PrimaryKeyGenerator.getLongKey();
		int a = commonDao.update(sb.toString(),  new Object[] { pkid_title,pk_id ,message,"admin"});
		
		if(a>0 && head>0)
			return true ;
		else
			return false;
	}
	
	
	/**
	 * 编辑上传文件的类型
	 * @param pk_id
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> queryActive(String pk_id) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select at.pk_id,at.title,case when at.filePath is not null then '有' else '无' end filePath ,CONVERT(varchar(100), at.createAt, 23) time,a.pk_id articleId,at.createBy,a.content  from activity_title at join article a on a.activityId=at.pk_id where at.pk_id=").append(pk_id);
		List<Map<String,Object>> targetResult = commonDao.queryForMapList(sb.toString()) ;
		if(null != targetResult && targetResult.size() > 0){
			Map<String, Object> result = targetResult.get(0);
			return result;
		}
		return null;
	}
	
	
	/**
	 * 编辑上传文件的类型
	 * @param pk_id
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> queryOneActive(String pk_id) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select at.pk_id,at.title,case when at.filePath is not null then '有' else '无' end filePath ,CONVERT(varchar(100), at.createAt, 23) time,a.pk_id articleId,at.createBy,a.content  from activity_title at join article a on a.activityId=at.pk_id where a.pk_id=").append(pk_id);
		List<Map<String,Object>> targetResult = commonDao.queryForMapList(sb.toString()) ;
		if(null != targetResult && targetResult.size() > 0){
			Map<String, Object> result = targetResult.get(0);
			return result;
		}
		return null;
	}
	
	
	/**
	 * @descripion 		添加摘要的信息 
	 * @param title		摘要信息标题
	 * @param message	摘要信息内容
	 * @return			返回插入数据是否成功
	 */
	@Override
	@Transactional
	public boolean editSaveActive(String pk_id, String title,String content) {
		StringBuffer sql = new StringBuffer();
		sql.append("update dbo.activity_title set title=?,createAt=getdate() where pk_id =(select activityId from article where pk_id=?)");
		int head = commonDao.update(sql.toString(),  new Object[] { title, pk_id});
		
		StringBuffer sb = new StringBuffer();
		sb.append("update dbo.article set content=?,createAt=getdate() where pk_id=? ");
		int a = commonDao.update(sb.toString(),  new Object[] {content,pk_id });
		
		if(a>0 && head>0)
			return true ;
		else
			return false;
	}
	
	/**
	 * @descripion 		添加摘要的信息 
	 * @param title		摘要信息标题
	 * @param message	摘要信息内容
	 * @return			返回插入数据是否成功
	 */
	@Override
	@Transactional
	public boolean deleteActivity(String pk_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from dbo.activity_title where pk_id =?");
		int head = commonDao.update(sql.toString(),  new Object[] { pk_id});
		
		StringBuffer sb = new StringBuffer();
		sb.append("delete from  dbo.article  where activityId=? ");
		int a = commonDao.update(sb.toString(),  new Object[] {pk_id});
		
		if(a>0 && head>0)
			return true ;
		else
			return false;
	}
	
	/**
	 * 上传的图片
	 * @param tourID	活动事件的主键
	 * @param fileName	上传文件的文件名称
	 * @param Message	图片的描述信息
	 * @return boolean  上传成功返回true,反之false
	 */
	@Override
	public boolean updateImg(long activitypkId, String fileName) {
		//添加主键
		long pk_id = PrimaryKeyGenerator.getLongKey();
		StringBuffer sql = new StringBuffer();
		sql.append("update dbo.activity_title set filePath=?,createAt=getdate() where pk_id =?");
		
		int a = commonDao.update(sql.toString(),  new Object[] { fileName,activitypkId});
		if(a>0)
			return true ;
		else
			return false;
	}


	/**
	 * 添加new图标
	 * @param articleid
	 * @return
	 */
	@Override
	public boolean addFlageNew(String articleid) {
		StringBuffer sql = new StringBuffer();
		sql.append("update dbo.activity_title set flage_new=1,createAt=getdate() where pk_id =?");
		int head = commonDao.update(sql.toString(),  new Object[] { articleid});
		
		if( head>0)
			return true ;
		else
			return false;
	}
	
	/**
	 * 去掉new图标
	 * @param articleid
	 * @return
	 */
	@Override
	public boolean delFlageNew(String articleid) {
		StringBuffer sql = new StringBuffer();
		sql.append("update dbo.activity_title set flage_new=0 where pk_id =?");
		int head = commonDao.update(sql.toString(),  new Object[] { articleid});
		
		if( head>0)
			return true ;
		else
			return false;
	}
}

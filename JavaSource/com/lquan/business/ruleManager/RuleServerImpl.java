package com.lquan.business.ruleManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import snt.common.dao.base.CommonDAO;
import snt.common.dao.base.PaginationSupport;
import snt.common.dao.base.PrimaryKeyGenerator;
import snt.common.dao.base.RowSelection;

@Service(value="ruleManagerServer")
public class RuleServerImpl implements IRuleServer {
	
	
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
	public PaginationSupport getRule(String page, String rows, String sort,String order, String title) {
		StringBuffer sb=new StringBuffer();
		sb.append("select pk_id,case when type=1 then '基本制度' when type=2 then 'IT制度' when type=3 then '财务制度' end type,case when flage_new =1 then '<img  style=display: inline; src=../img/new/new2.gif>' else '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' end +''+fileName as fileName,createBy,CONVERT(varchar(100), createAt, 23) ruleTime from ruleFile where 1=1 ");
		/*sb.append("select t.pk_id,t.title,t.content,CONVERT(varchar(100), t.tourTime, 23) tourTime,case when ti.count is null then 0 else  ti.count end count from tour t ");
		sb.append("left join  (select count(pk_id) count ,tourid from tourImg group by tourid) ti on t.pk_id = ti.tourid ");
		sb.append("Where 1=1");*/
		
		if(title!=null && !"".equals(title)){
			sb.append("AND fileName like '%").append(title).append("%'");
		}
		String orderBy = " order by " + sort + " " + order;
		PaginationSupport ps = commonDao.queryForPaginatedMapList(sb.toString(), new Object[]{},new int[]{},new RowSelection(Integer.valueOf(page)-1 ,Integer.valueOf(rows)),orderBy);
		
		if(null != ps){
			return ps;
		}
		return null;
	}


	/**
	 * 上传的图片
	 * @param tourID	活动事件的主键
	 * @param fileName	上传文件的文件名称
	 * @param Message	图片的描述信息
	 * @return boolean  上传成功返回true,反之false
	 */
	@Override
	public boolean updateImg(int type, String fileName,String fileAllName, String userName) {
		//添加主键
		long pk_id = PrimaryKeyGenerator.getLongKey();
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ruleFile(pk_id,type,fileName,fileAllName,createAt,createBy) values(?,?,?,?,getdate(),?) ");
		
		int a = commonDao.update(sql.toString(),  new Object[] { pk_id, type,fileName,fileAllName,userName});
		if(a>0)
			return true ;
		else
			return false;
	}
	
	
	/**
	 * @descripion 删除公告信息
	 * @param id	公告信息的主键	
	 * @return		返回删除是否成功
	 */
	@Override
	public boolean deleRule(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from ruleFile where pk_id=").append(id);
		int p = commonDao.update(sql.toString(), new Object[] {});
		if(p>0)
			return true;
		else
			return false;
	}

	/**
	 * 
	 */
	@Override
	public boolean addFlageNew(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("update ruleFile set flage_new=1,createAt=getdate() where pk_id =").append(id);
		int p = commonDao.update(sql.toString(), new Object[] {});
		if(p>0)
			return true;
		else
			return false;
	}
	
	
	@Override
	public boolean delFlageNew(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("update ruleFile set flage_new=0,createAt=getdate() where pk_id =").append(id);
		int p = commonDao.update(sql.toString(), new Object[] {});
		if(p>0)
			return true;
		else
			return false;
	}
	
	
}

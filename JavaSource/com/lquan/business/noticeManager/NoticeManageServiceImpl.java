package com.lquan.business.noticeManager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import snt.common.dao.base.CommonDAO;
import snt.common.dao.base.PaginationSupport;
import snt.common.dao.base.PrimaryKeyGenerator;
import snt.common.dao.base.RowSelection;


/**
 * 公告的业务逻辑
 * @author liuquan
 *
 */
@Service(value="noticeManageService")
public class NoticeManageServiceImpl implements INoticeManageService {

	@Autowired
	@Qualifier("commonDAO")
	private CommonDAO commonDao;

	/**
	 * 获取当前的公告信息
	 * @return list
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> noticelist(){
		StringBuffer sql = new StringBuffer();
		sql.append("select pk_id,title,title_img,content,noticeTime from notice ");
		List<Map<String, Object>> list  = commonDao.queryForMapList(sql.toString());
		
		return list;
	}

	@Override
	public PaginationSupport getNotice(String page,String rows,String sort,String order,String title) {
		StringBuffer sb=new StringBuffer();
		sb.append("select  pk_id, case when flage_new =1 then '<img  style=display: inline; src=../img/new/new2.gif>' else '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' end +''+title as title ,content,CONVERT(varchar(100), noticeTime, 23) noticeTimex from notice where 1=1 ");
		if(title!=null && !"".equals(title)){
			sb.append("AND title like '%").append(title).append("%'");
		}
		String orderBy = " order by noticeTime  desc";
		PaginationSupport ps = commonDao.queryForPaginatedMapList(sb.toString(), new Object[]{},new int[]{},new RowSelection(Integer.valueOf(page)-1 ,Integer.valueOf(rows)),orderBy);
		
		if(null != ps){
			return ps;
		}
		return null;
	}

	/**
	 * 添加公告的信息 
	 * @param title		公告信息标题
	 * @param message	公告信息内容
	 * @return			返回插入数据的个数
	 */
	@Override
	public boolean addAdNotice(String title, String message) {
		title = title==null? "Sorry忘了写标题了！":title;
		StringBuffer sql = new StringBuffer();
		sql.append("insert into notice(pk_id,title,content,noticeTime,sortTime,updateTime) values(?,?,?,getdate(),getdate(),getdate()) ");
		//添加主键
		long pk_id = PrimaryKeyGenerator.getLongKey();
		int a = commonDao.update(sql.toString(),  new Object[] { pk_id, title,message});
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
	public boolean delNotice(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from notice where pk_id=").append(id);
		int p = commonDao.update(sql.toString(), new Object[] {});
		if(p>0)
			return true;
		else
			return false;
	}

	
	/**
	 * @Description		修改公告的信息
	 * @param id		公告的主键id
	 * @param title		公告的标题
	 * @param message	公告的内容
	 * @return			修改公告是否成功
	 */
	@Override
	public boolean editAdNotice(Long id, String title, String message) {
		StringBuffer sql = new StringBuffer();
		sql.append("update notice set title='").append(title).append("', ");
		sql.append("content='").append(message).append("'");
		sql.append("where pk_id=").append(id);
		int count = commonDao.update(sql.toString(), new Object[]{});
		if(count>0)
			return true;
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
	public Map<String, Object> queryNoticeType(String pk_id) throws Exception {
		StringBuffer sb = new StringBuffer();
		/*sb.append(" SELECT PK_ID,PROJECTID,TACHEN..O,TARGETNO,TARGETNAME,TARGET_TYPE,replace(select_standard,'；','；<br />') AS SELECT_STANDARD,replace(OPTION_TEXT,'；','；<br />') AS OPTION_TEXT,replace(RECHECK,'；','；<br />') AS RECHECK ");*/
		sb.append("select pk_id,title,content,CONVERT(varchar(100), noticeTime, 23) noticeTime from notice where pk_id=").append(pk_id);
		List<Map<String,Object>> targetResult = commonDao.queryForMapList(sb.toString()) ;
		if(null != targetResult && targetResult.size() > 0){
			Map<String, Object> result = targetResult.get(0);
			return result;
		}
		return null;
	}
	
	
	
	/**
	 * 添加new图标
	 * @param id
	 * @return
	 */
	@Override
	public boolean addFlageNew(Long id) {
		StringBuffer sql = new StringBuffer();
		sql.append("update notice set  flage_new=1,noticeTime=getdate() where pk_id =").append(id);
		int count = commonDao.update(sql.toString(), new Object[]{});
		if(count>0)
			return true;
		else
			return false;
	}
	
	/**
	 * 删除new图标
	 * @param id
	 * @return
	 */
	@Override
	public boolean delFlageNew(Long id) {
		StringBuffer sql = new StringBuffer();
		sql.append("update notice  set flage_new=0 where pk_id =").append(id);
		int count = commonDao.update(sql.toString(), new Object[]{});
		if(count>0)
			return true;
		else
			return false;
	}
	
}

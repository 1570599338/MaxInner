package com.lquan.business.tourManager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import snt.common.dao.base.CommonDAO;
import snt.common.dao.base.PaginationSupport;
import snt.common.dao.base.PrimaryKeyGenerator;
import snt.common.dao.base.RowSelection;

@Service(value="tourManagerServer")
public class TourManagerServerImpl implements ITourManagerServer {
	
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
		sb.append("select t.pk_id,t.title,t.content,CONVERT(varchar(100), t.tourTime, 23) tourTime,case when ti.count is null then 0 else  ti.count end count from tour t ");
		sb.append("left join  (select count(pk_id) count ,tourid from tourImg group by tourid) ti on t.pk_id = ti.tourid ");
		sb.append("Where 1=1");
		
		if(title!=null && !"".equals(title)){
			sb.append("AND t.title like '%").append(title).append("%'");
		}
		String orderBy = " order by t." + sort + " " + order;
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
	public boolean addAdTour(String title, String message) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into tour(pk_id,title,content,tourTime,sortTime,updateTime) values(?,?,?,getdate(),getdate(),getdate()) ");
		//添加主键
		long pk_id = PrimaryKeyGenerator.getLongKey();
		int a = commonDao.update(sql.toString(),  new Object[] { pk_id, title,message});
		if(a>0)
			return true ;
		else
			return false;
	}

	/**
	 * @descripion 删除摘要信息
	 * @param id	摘要信息的主键	
	 * @return		返回删除是否成功
	 */
	@Override
	public boolean delTour(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from tour where pk_id=").append(id);
		int p = commonDao.update(sql.toString(), new Object[] {});
		if(p>0)
			return true;
		else
			return false;
	}

	
	/**
	 * @Description		修改摘要的信息
	 * @param id		摘要的主键id
	 * @param title		摘要的标题
	 * @param message	摘要的内容
	 * @return			修改摘要是否成功
	 */
	@Override
	public boolean editAdTour(Long id, String title, String message) {
		StringBuffer sql = new StringBuffer();
		sql.append("update tour set title='").append(title).append("', ");
		sql.append("content='").append(message).append("'");
		sql.append("where pk_id=").append(id);
		int count = commonDao.update(sql.toString(), new Object[]{});
		if(count>0)
			return true;
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
	public boolean updateImg(long tourID, String fileName, String Message,String userName) {
		//添加主键
		long pk_id = PrimaryKeyGenerator.getLongKey();
		StringBuffer sql = new StringBuffer();
		sql.append("insert into tourImg(pk_id,tourId,fileName,description,updateAt,updateBy) values(?,?,?,?,getdate(),?) ");
		
		int a = commonDao.update(sql.toString(),  new Object[] { pk_id, tourID,fileName,Message,userName});
		if(a>0)
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
	public List<Map<String, Object>> getActiveImg(String pk_id,String basePath) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select pk_id,tourid,fileName, '");
		sql.append(basePath);
		sql.append("' + fileName as imgPath,description,CONVERT(varchar(100), updateAt, 23) updateAt from dbo.tourImg where tourid='");
		sql.append(pk_id);
		sql.append("'");
		List<Map<String, Object>> list = commonDao.queryForMapList(sql.toString());
		return list;
	}

}

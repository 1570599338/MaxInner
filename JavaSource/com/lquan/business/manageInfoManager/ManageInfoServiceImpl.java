package com.lquan.business.manageInfoManager;

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
 * @Description 实现经营管理信息的实现
 * @author liuquan
 *
 */
@Service(value="manageInfoService")
public class ManageInfoServiceImpl implements IManageInfoService {

	@Autowired
	@Qualifier("commonDAO")
	private CommonDAO commonDao;

	/**
	 * 添加经营管理信息
	 * @param title 标题
	 * @param message 信息
	 * @param messageFormat 有格式的信息
	 * @return 返回值是boolean类型
	 */
	@Override
	public boolean addManageInfo(String title, String message,String messageFormat) {
		// TODO 进行管理信息添加
		StringBuffer sql = new StringBuffer();
		sql.append("insert into dbo.bookInfo(manageTitle,content,createBy,createAt,flage) values(?,?,?,getdate(),?) ");
		//添加主键
		long pk_id = PrimaryKeyGenerator.getLongKey();
		int head = commonDao.update(sql.toString(),  new Object[] {  title,message,"admin",1});
		
		if(head>0)
			return true ;
		else
			return false;
		
	}
	
	
	/**
	 * 添加经营管理信息
	 * @param title 标题
	 * @param message 信息
	 * @param messageFormat 有格式的信息
	 * @return 返回值是boolean类型
	 */
	@Override
	public boolean editManageInfo(String pk_id,String title, String message,String messageFormat) {
		StringBuffer sql = new StringBuffer();
		sql.append("update dbo.bookInfo set manageTitle=?,content=? where pk_id =?");
		int head = commonDao.update(sql.toString(),  new Object[] { title,message, pk_id});
		
		if(head>0)
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
	public Map<String, Object> queryOneManage(String pk_id) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select pk_id,manageTitle,content,CONVERT(varchar(100), createAt, 23) time,createBy,flage from bookInfo where pk_id=").append(pk_id);
		List<Map<String,Object>> targetResult = commonDao.queryForMapList(sb.toString()) ;
		if(null != targetResult && targetResult.size() > 0){
			Map<String, Object> result = targetResult.get(0);
			return result;
		}
		return null;
	}


	/**
	 * 删除经营管理信息
	 * @param pk_id
	 * @return
	 */
	@Override
	public boolean delManageInfo(String pk_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from dbo.bookInfo where pk_id =?");
		int head = commonDao.update(sql.toString(),  new Object[] { pk_id});
		if(head>0)
			return true;
		else 
			return false;
	}
	
	/**
	 * 显示经营管理信息的数据列表
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @param title
	 * @return
	 */
	@Override
	public PaginationSupport manageList(String page, String rows, String sort,String order, String title) {
		StringBuffer sb=new StringBuffer();
		sb.append("select pk_id,manageTitle,content,CONVERT(varchar(100), createAt, 23) time,createBy,flage from bookInfo");
		sb.append(" Where 1=1 ");
		
		if(title!=null && !"".equals(title)){
			sb.append("AND manageTitle like '%").append(title).append("%'");
		}
		String orderBy = " order by  createAt " + order;
		PaginationSupport ps = commonDao.queryForPaginatedMapList(sb.toString(), new Object[]{},new int[]{},new RowSelection(Integer.valueOf(page)-1 ,Integer.valueOf(rows)),orderBy);
		
		if(null != ps){
			return ps;
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
	public Map<String, Object> queryOneManageOrder(String pk_id,String stat) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select pk_id,manageTitle,content,CONVERT(varchar(100), createAt, 23) time,createBy,flage from bookInfo where 1=1");
		if(stat!=null && !"".equals(stat)){
			if("up".equals(stat)){
				sb.append(" and pk_id>").append(pk_id);
			}
			if("down".equals(stat)){
				sb.append(" and pk_id<").append(pk_id);
				
			}
			
		}else{
			if(pk_id!=null&&!"".equals(pk_id)){
				sb.append(" and pk_id=").append(pk_id);
				if("up".equals(stat)){
					sb.append(" and pk_id>").append(pk_id);
				}
			}
		}
		sb.append(" order by createAt desc ");
		List<Map<String,Object>> targetResult = commonDao.queryForMapList(sb.toString()) ;
		if(null != targetResult && targetResult.size() > 0){
			Map<String, Object> result = targetResult.get(0);
			return result;
		}
		return null;
	}
	
	
	/**
	 * 获取图书的
	 * @return
	 */
	@Override
	public List<Map<String, Object>>  getBookList(String companyid,String departMentCode,String gender,String userName,String telPhone,String path){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT pk_id,LEFT(manageTitle,14) manageTitle,content,img,case  when img is NULL then '\\upload\\demo.jpg' else '\\img\\demo\\' + img  END as path  FROM bookInfo ");
		List<Map<String, Object>> list = commonDao.queryForMapList(sql.toString());
		return list;
	}
	
	/**
	 * 
	 * @param pk_id
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryBookOne(String pk_id) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT pk_id,LEFT(manageTitle,14) manageTitle,content,img,case  when img is NULL then '\\upload\\demo.jpg' else '\\img\\demo\\' + img  END as path,createAt time  FROM bookInfo where pk_id=").append(pk_id);
		List<Map<String,Object>> targetResult = commonDao.queryForMapList(sb.toString()) ;
		if(null != targetResult && targetResult.size() > 0){
			Map<String, Object> result = targetResult.get(0);
			return result;
		}
		return null;
	}
	
}

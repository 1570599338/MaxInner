package com.lquan.business.uploadFileManager;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import snt.common.dao.base.CommonDAO;
import snt.common.dao.base.PaginationSupport;
import snt.common.dao.base.PrimaryKeyGenerator;
import snt.common.dao.base.RowSelection;

/**
 * 文件上传的实现接口
 * @author liuquan
 *
 */
@Service(value="uploadFileServe")
@Transactional
public class UploadFileServeImpl implements IUploadFileService {

	@Autowired
	@Qualifier("commonDAO")
	private CommonDAO commonDao;
	
	/**
	 * 新增文件的模板类型
	 * @param title
	 * @return
	 */
	@Override
	public boolean addUploadFilemodel(String title) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into uploadFileModel(pk_id,title,createAt) values(?,?,getdate()) ");
		//添加主键
		long pk_id = PrimaryKeyGenerator.getLongKey();
		int a = commonDao.update(sql.toString(),  new Object[] { pk_id, title});
		if(a>0)
			return true ;
		else
			return false;
	}

	/**
	 * 显示上传模板类型
	 */
	@Override
	public List<Map<String, Object>>  getuploadList(String table_uploadFileModel,String table_uploadFileType)throws Exception {
		
		List<Map<String, Object>> modelList = getuploadModelList(table_uploadFileModel);
		List<Map<String, Object>> typeList = getuploadTypeListType(table_uploadFileType);
		for (Map<String, Object> modelMap:modelList) {
			List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> typeMap:typeList) {
				if(modelMap.get("pk_id").toString().equals(typeMap.get("uploadFileModelID").toString())){
					tempList.add(typeMap);
				}
			}
			modelMap.put("typeList", tempList);
		}
		
		return modelList;
	}
	
	/**
	 * 上传模板的类型的查询
	 * @return
	 */
	@Override
	public List<Map<String, Object>>  getuploadModelList(String uploadFileModel){
		StringBuffer sql = new StringBuffer();
		sql.append("select pk_id,title from ").append(uploadFileModel);
		List<Map<String, Object>> list = commonDao.queryForMapList(sql.toString());
		return list;
	}
	
	/**
	 * 上传模板类型的文件类型查询
	 * @return
	 */
	private List<Map<String, Object>>  getuploadTypeListType(String table_uploadFileType){
		StringBuffer sql = new StringBuffer();
		sql.append("select pk_id,uploadFileModelID,title,createAt from ");
		sql.append(table_uploadFileType);
		List<Map<String, Object>> list = commonDao.queryForMapList(sql.toString());
		return list;
	}
	
	/**
	 * 上传模板类型的文件类型查询
	 * @return
	 */
	public List<Map<String, Object>>  getuploadTypeList(String modelId){
		StringBuffer sql = new StringBuffer();
		sql.append("select pk_id,uploadFileModelID,title,createAt from dbo.uploadFileType where 1=1 ");
		if(modelId!=null &&! "".equals(modelId.trim())){
			sql.append(" AND uploadFileModelID='");
			sql.append(modelId);
			sql.append("'");
		}
		List<Map<String, Object>> list = commonDao.queryForMapList(sql.toString());
		return list;
	}

	/**
	 * 新增文件的文件类型
	 * @param title
	 * @return
	 */
	@Override
	public boolean addUploadFileType(String modelId, String title) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into uploadFileType(pk_id,uploadFileModelID,title,createAt) values(?,?,?,getdate()) ");
		//添加主键
		long pk_id = PrimaryKeyGenerator.getLongKey();
		int a = commonDao.update(sql.toString(),  new Object[] { pk_id,modelId, title});
		if(a>0)
			return true ;
		else
			return false;
	}
	
	/**
	 * 编辑文件的文件类型
	 * @param title
	 * @return
	 */
	@Override
	public boolean editUploadFileType(String modelId, String title) {
		StringBuffer sql = new StringBuffer();
		sql.append("update uploadFileType set title='");
		sql.append(title);
		sql.append("' where pk_id=");
		sql.append(modelId);
		//添加主键
		long pk_id = PrimaryKeyGenerator.getLongKey();
		int a = commonDao.update(sql.toString(),  new Object[] {});
		if(a>0)
			return true ;
		else
			return false;
	}

	
	/**
	 * 编辑文件的文件类型
	 * @param title
	 * @return
	 */
	@Override
	public boolean editUploadFileModel(String modelId, String title) {
		StringBuffer sql = new StringBuffer();
		sql.append("update uploadFileModel set title='");
		sql.append(title);
		sql.append("' where pk_id=");
		sql.append(modelId);
		//添加主键
		long pk_id = PrimaryKeyGenerator.getLongKey();
		int a = commonDao.update(sql.toString(),  new Object[] {});
		if(a>0)
			return true ;
		else
			return false;
	}
	/**
	 * 上传的文件的
	 * @param user
	 * @param modelId
	 * @param typeId
	 * @param fileName
	 * @param path
	 * @return
	 */
	@Override
	public boolean addUploadFile(String user, String modelId, String typeId,String fileName, String path) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into uploadFile(pk_id,modelID,typeID,fileName,path,createBy,createAt) values(?,?,?,?,?,?,getdate()) ");
		//添加主键
		long pk_id = PrimaryKeyGenerator.getLongKey();
		int a = commonDao.update(sql.toString(),  new Object[] { pk_id,modelId, typeId,fileName,path,user});
		if(a>0)
			return true ;
		else
			return false;
	}
	

	
	
	/**
	 * @descripion 分页数据显示
	 * @param page 页数
	 * @param row  行数
	 * @return
	 */
	@Override
	public PaginationSupport getFile(String page, String rows, String sort,String order, String fileName,Map<String,String> condition) {
		StringBuffer sb=new StringBuffer();
		sb.append("select f.pk_id, m.title modelName,t.title typeName,f.fileName,f.path fileAllName,CONVERT(varchar(100), f.createAt, 23) createTime ,f.createBy from uploadFile f ");
		sb.append("");
		sb.append(" join uploadFileType t on t.pk_id = f.typeid ");
		sb.append(" join uploadFileModel m on m.pk_id = f.modelid where 1=1 ");
		
		if(fileName!=null && !"".equals(fileName)){
			sb.append("AND f.fileName like '%").append(fileName).append("%'");
		}
		if(condition.get("modelId")!=null && !"".equals(condition.get("modelId").toString().trim())){
			sb.append("AND m.pk_id =").append(condition.get("modelId").toString().trim());
		}
		if(condition.get("typeId")!=null && !"".equals(condition.get("typeId").toString().trim())){
			sb.append("AND t.pk_id = ").append(condition.get("typeId").toString().trim());
		}
		String orderBy = " order by " + sort + " " + order;
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
	public Map<String, Object> queryFileType(String pk_id) throws Exception {
		StringBuffer sb = new StringBuffer();
		/*sb.append(" SELECT PK_ID,PROJECTID,TACHENO,TARGETNO,TARGETNAME,TARGET_TYPE,replace(select_standard,'；','；<br />') AS SELECT_STANDARD,replace(OPTION_TEXT,'；','；<br />') AS OPTION_TEXT,replace(RECHECK,'；','；<br />') AS RECHECK ");*/
		sb.append("select ut.pk_id,ut.title,uf.title Mtitle from uploadFileType ut ");
		sb.append("join uploadFileModel uf on uf.pk_id=ut.uploadFileModelID");
		sb.append(" WHERE ut.pk_id = '").append(pk_id).append("' ");
		List<Map<String,Object>> targetResult = commonDao.queryForMapList(sb.toString()) ;
		if(null != targetResult && targetResult.size() > 0){
			Map<String, Object> result = targetResult.get(0);
			return result;
		}
		return null;
	}

	/**
	 * 删除文件的文件类型
	 * @param pk_id
	 * @return
	 */
	@Override
	public boolean delUploadFileType(String pk_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from uploadFileType where pk_id=?");
		int a = commonDao.update(sql.toString(),  new Object[] { pk_id});
		if(a>0)
			return true ;
		else
			return false;
	}
	
	/**
	 * 删除文件的文件类型
	 * @param pk_id
	 * @return
	 */
	@Override
	public boolean delUploadFileModel(String pk_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from uploadFileModel where pk_id=?");
		int a = commonDao.update(sql.toString(),  new Object[] { pk_id});
		if(a>0)
			return true ;
		else
			return false;
	}

	/**
	 * 查询对应的文件模板下面是否有多余的文件类型
	 * @param pk_id
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> queryFileModelType(String pk_id) throws Exception {
		StringBuffer sb = new StringBuffer();
		/*sb.append(" SELECT PK_ID,PROJECTID,TACHENO,TARGETNO,TARGETNAME,TARGET_TYPE,replace(select_standard,'；','；<br />') AS SELECT_STANDARD,replace(OPTION_TEXT,'；','；<br />') AS OPTION_TEXT,replace(RECHECK,'；','；<br />') AS RECHECK ");*/
		sb.append("select pk_id,title ,uploadFileModelID from uploadFileType  ");
		sb.append(" WHERE uploadFileModelID = '").append(pk_id).append("' ");
		List<Map<String,Object>> list = commonDao.queryForMapList(sb.toString()) ;
		return list;
	}
	
	
	
	/**
	 * 删除上传的文件
	 * @param pk_id
	 * @return
	 */
	@Override
	public boolean delUpLoadFile(String pk_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from uploadFile where pk_id=?");
		int a = commonDao.update(sql.toString(),  new Object[] { pk_id});
		if(a>0)
			return true ;
		else
			return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}

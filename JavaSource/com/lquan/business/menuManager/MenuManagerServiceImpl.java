package com.lquan.business.menuManager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lquan.business.menuManager.IMenuManageService;

import snt.common.dao.base.CommonDAO;
import snt.common.dao.base.PrimaryKeyGenerator;

/**
* @ClassName: MenuManageServiceImpl 
* @Description: 菜单管理
* @author huaiqianci 
* @date 2014-7-9 上午11:40
 */
@Service(value="menuManageService")
@Transactional
public class MenuManagerServiceImpl implements IMenuManageService{
	
	@Autowired
    @Qualifier("commonDAO")
	private CommonDAO commonDao;
	
	/**
	 * 查询父菜单和子菜单
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public List<Map<String, Object>> getMenuList() throws Exception {

		//查询一级菜单
		String sql="select pk_id,menuNum,menuName,menuImge,menuURL,parentMenuNum,describe,'closed' as state from t_menu where parentMenuNum='' or parentMenuNum is null";
		List<Map<String, Object>> parents=commonDao.queryForMapList(sql);
		
		String parentMenuNum="";
		List<Map<String, Object>> sons=null;
		
		for (Map<String, Object> map : parents) {
			
			parentMenuNum=(String) map.get("menuNum");
			sql="select pk_id,menuNum,menuName,menuImge,menuURL,parentMenuNum,describe from t_menu where parentMenuNum='"+parentMenuNum+"'";
			sons=commonDao.queryForMapList(sql);
			
			if(sons!=null && sons.size()>0){
				map.put("children", sons);
			}else{
				map.remove("state");
			}		
		}
		return parents;
	}
	
	/**
	 * 查询菜单子菜单，ID，TEXT的json数据格式
	 * 用于页面的下拉树的选择
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> getMenuListForSelect() throws Exception {
		
		String sql="select pk_id,menuNum,menuName,menuImge,menuURL,parentMenuNum,describe,'closed' as state from t_menu where parentMenuNum='' or parentMenuNum is null";
		List<Map<String, Object>> parents=commonDao.queryForMapList(sql);
		
		String parentMenuNum="";
		List<Map<String, Object>> sons=null;
		
		for (Map<String, Object> map : parents) {
			
			parentMenuNum=(String) map.get("id");
			sql="select pk_id,menuNum as id,menuName as text from t_menu where parentMenuNum='"+parentMenuNum+"'";
			sons=commonDao.queryForMapList(sql);
			
			if(sons!=null && sons.size()>0){
				map.put("children", sons);
			}else{
				map.remove("state");
			}
		}
		return parents;	
	}
	
	/**
	 * 编辑菜单信息进行修改
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int updateMenuInfo(Map map) throws Exception {

		String sql=" update t_menu set menuName=?,menuImge=?,parentMenuNum=?,menuURL=?,describe=? where menuNum=?";
		//条件
		String[] condition={(String)map.get("menuName"),(String)map.get("menuImge"),(String)map.get("parentMenuNum"),(String)map.get("menuURL"),(String)map.get("describe"),(String)map.get("menuNum")};
		
		int result=commonDao.update(sql, condition);
		
		return result;
	}

	/**
	 * 新增菜单信息
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int addMenuInfo(Map map) throws Exception {
		
		//插入数据
		String sql=" insert into t_menu(pk_id,menuNum,parentMenuNum,menuName,menuImge,menuURL,describe) values(?,?,?,?,?,?,?)";
		Object[] condition={PrimaryKeyGenerator.getLongKey(),(String) map.get("menuNum"),(String) map.get("parentMenuNum"),(String) map.get("menuName"),(String) map.get("menuImge"),(String) map.get("menuURL"),(String) map.get("describe")};
		
		return commonDao.update(sql, condition);
	}

	/**
	 * 删除菜单信息
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int deleteMenuInfo(String pk_idstr) throws Exception {
		
		if(pk_idstr!=null && !"".equals(pk_idstr)){
			long pk_id = Long.parseLong(pk_idstr);
			String sql="delete from t_menu where pk_id=?";
			return commonDao.update(sql, new Object[]{pk_id});
		}
		
		return 0;
	}

}

package com.lquan.business.menuManager;

import java.util.List;
import java.util.Map;

/**
* @ClassName: IMenuManageService 
* @Description: 菜单管理
* @author huaiqianci
* @date 2014-7-9 上午 11:40
 */
public interface IMenuManageService {
	
	/**
	 * 查询菜单
	 * 输出页面JSON格式数据
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getMenuList() throws Exception;
	
	/**
	 * 查询菜单，下拉格式的
	 * 用于输出JSON格式数据
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getMenuListForSelect() throws Exception;
	
	/**
	 * 修改菜单信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateMenuInfo(Map map) throws Exception;
	
	/**
	 * 增加新菜单
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int addMenuInfo(Map map) throws Exception;
	
	/**
	 * 根据主键删除菜单信息
	 * @param nodeNum
	 * @return
	 * @throws Exception
	 */
	public int deleteMenuInfo(String pk_idstr) throws Exception;
	

}

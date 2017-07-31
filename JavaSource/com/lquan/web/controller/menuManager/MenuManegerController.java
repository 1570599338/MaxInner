package com.lquan.web.controller.menuManager;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lquan.business.menuManager.IMenuManageService;
import com.lquan.web.controller.userManager.UserLoginController;
import com.lquan.web.util.FormUtil;


/**
* @ClassName: MenuManageController 
* @Description:菜单管理
* @author lquan 
* @date 2014-7-9 上午午11:30
 */
@Controller
@RequestMapping("menuManage")
public class MenuManegerController {
	
	@Resource(name="menuManageService")
	public IMenuManageService menuManageService;
	
	private Log log = LogFactory.getLog(UserLoginController.class);
	
	/**
	 * 查询菜单列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("selectMenuManage")
	public ModelAndView selectMenuManage(HttpServletRequest request,HttpServletResponse response) {
		
		return new ModelAndView("menu/menuManager");
	}
	
	/**
	 * 删除菜单信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("subDelMenu")
	public ModelAndView subDelMenu(HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes) {
		
		try{
			String pk_idstr=FormUtil.getStringFiledValue(request, "pk_id");
			
			int result=menuManageService.deleteMenuInfo(pk_idstr);
			
			if(result>0){
				redirectAttributes.addFlashAttribute("message", "删除成功！");
			}else{
				redirectAttributes.addFlashAttribute("message", "未能删除该条记录！");
			}
			
		}catch (Exception e) {
			log.error("subDelMenu,删除菜单信息出错。",e);
		}
		
		return new ModelAndView("redirect:/menuManage/selectMenuManage");
	}
	
	/**
	 * 增加新的菜单信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("subAddMenu")
	public ModelAndView subAddMenu(HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes) {
		
		try{
			String menuName=FormUtil.getStringFiledValue(request, "menuName_add")==null?"":FormUtil.getStringFiledValue(request, "menuName_add");
			String menuImge=FormUtil.getStringFiledValue(request, "menuImge_add")==null?"":FormUtil.getStringFiledValue(request, "menuImge_add");
			String menuNum=FormUtil.getStringFiledValue(request, "menuNum_add")==null?"":FormUtil.getStringFiledValue(request, "menuNum_add");
			String parentMenuNum=FormUtil.getStringFiledValue(request, "parentMenuNum_add")==null?"":FormUtil.getStringFiledValue(request, "parentMenuNum_add");
			String memuURL=FormUtil.getStringFiledValue(request, "menuURL_add")==null?"":FormUtil.getStringFiledValue(request, "menuURL_add");
			String describe=FormUtil.getStringFiledValue(request, "describe_add")==null?"":FormUtil.getStringFiledValue(request, "describe_add");
			
			Map map=new HashMap();
			map.put("menuName", menuName);
			map.put("menuImge", menuImge);
			map.put("menuNum", menuNum);
			map.put("parentMenuNum", parentMenuNum);
			map.put("memuURL", memuURL);
			map.put("describe", describe);
			
			int result=menuManageService.addMenuInfo(map);
			
			if(result>0){
				redirectAttributes.addFlashAttribute("message", "添加成功！");
			}else{
				redirectAttributes.addFlashAttribute("message", "未能添加该条记录！");
			}
			
		}catch (Exception e) {
			log.error("subEditMenu,新增菜单信息出错。",e);
		}
		return new ModelAndView("redirect:/menuManage/selectMenuManage");
	}
	
	/**
	 * 修改菜单信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("subEditMenu")
	public ModelAndView subEditMenu(HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes) {
		
		try{
			//String pk_id=FormUtil.getStringFiledValue(request, "pk_id")==null?"":FormUtil.getStringFiledValue(request, "pk_id");
			String menuName=FormUtil.getStringFiledValue(request, "menuName")==null?"":FormUtil.getStringFiledValue(request, "menuName");
			String menuImge=FormUtil.getStringFiledValue(request, "menuImge")==null?"":FormUtil.getStringFiledValue(request, "menuImge");
			String menuNum=FormUtil.getStringFiledValue(request, "menuNum")==null?"":FormUtil.getStringFiledValue(request, "menuNum");
			String parentMenuNum=FormUtil.getStringFiledValue(request, "parentMenuNum")==null?"":FormUtil.getStringFiledValue(request, "parentMenuNum");
			String memuURL=FormUtil.getStringFiledValue(request, "menuURL")==null?"":FormUtil.getStringFiledValue(request, "menuURL");
			String describe=FormUtil.getStringFiledValue(request, "describe")==null?"":FormUtil.getStringFiledValue(request, "describe");
			
			Map map=new HashMap();
			//map.put("pk_id", pk_id);
			map.put("menuName", menuName);
			map.put("menuImge", menuImge);
			map.put("menuNum", menuNum);
			map.put("parentMenuNum", parentMenuNum);
			map.put("memuURL", memuURL);
			map.put("describe", describe);
			
			int result=menuManageService.updateMenuInfo(map);
			
			if(result>0){
				redirectAttributes.addFlashAttribute("message", "修改成功！");
			}else{
				redirectAttributes.addFlashAttribute("message", "未能修改该条记录！");
			}
			
		}catch (Exception e) {
			log.error("subEditMenu,修改菜单信息出错。",e);
		}
		return new ModelAndView("redirect:/menuManage/selectMenuManage");
	}
	
	/**
	 * 返回菜单内容的json格式数据(用户下拉框的树选择)
	 * 区别是JSON格式得又ID，TEXT，子节点放children中
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="selectMenuJsonForSelect")
	public ModelAndView selectMenuJsonForSelect(HttpServletRequest request,HttpServletResponse response){
		
		try{
			//查询所有菜单
			List<Map<String, Object>> menulist=menuManageService.getMenuListForSelect();
			
			//json形式返回以树形表格展示
			JSONArray json=JSONArray.fromObject(menulist);
			
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();			
			out.write(json.toString());			
			out.flush();			
			out.close();
			
		}catch (Exception e) {
			log.error("selectMenuJsonForSelect,获取下拉菜单列表出错，也有可能JSON格式转换引起。",e);
		}
		return null;
	}
	
	/**
	 * 返回菜单内容的json格式数据
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value="selectMenuJson")
	public ModelAndView selectMenuJson(HttpServletRequest request,HttpServletResponse response){
		
		try{
			//查询所有菜单
			List<Map<String, Object>> menulist=menuManageService.getMenuList();
			
			//json形式返回以树形表格展示
			JSONArray json=JSONArray.fromObject(menulist);
			
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();			
			out.write(json.toString());			
			out.flush();			
			out.close();
			
		}catch (Exception e) {
			log.error("selectMenuJson,获取菜单列表出错，也有可能JSON格式转换引起的错误！",e);
		}
		return null;
	}
	
	/**
	 * 编辑菜单
	 * @param request
	 * @param response
	 * @return ModelAndView
	 *//*
	@RequestMapping(value="editUpdateMenuManage")
	public ModelAndView editUpdateMenuManage(HttpServletRequest request,HttpServletResponse response){
		
		return new ModelAndView("menu/menuManager");
	}
*/
}

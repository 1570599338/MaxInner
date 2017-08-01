package com.lquan.web.controller.activeManager;

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

import snt.common.dao.base.PaginationSupport;
import snt.common.web.util.WebUtils;

import com.google.gson.Gson;
import com.lquan.business.activeManager.IActiveManagerServer;
import com.lquan.business.tourManager.ITourManagerServer;
import com.lquan.util.MyFileUtil;
import com.lquan.web.controller.tourManager.TourController;
import com.lquan.web.util.FormUtil;

//***************************** —— ***************************************************************
//*****************************|  |****************************************************************
//****************************--  --*******************************************************************
//****************************\	   /*************************************************************
//*****************************\  /***************************************************************
//******************************\/****************************************************************
//************************************************************************************************
//******************---------------------------------------------*****************************************************************************
//******************|  下面的代码是重写的~~~~(>_<)~~~~.宝宝心里有苦宝宝不说      |***************************************************************
//******************|                               					   |**************************************************
//******************|   时间：2016年11月14日11:29:57   					   |****************************************************************
//******************---------------------------------------------**********************************************
//************************************************************************************************
//************************************************************************************************

@Controller
@RequestMapping(value="/active")
public class ActivtiyController {
	Log log = LogFactory.getLog(ActivtiyController.class);
	
	@Resource(name="activeManagerServer")
	private IActiveManagerServer activeServer;
	
	@RequestMapping(value="/toPage")
	public String 	topage(){
		
		return "active/active";
	}
	
	
	/**
	 * 添加公告信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="addAdTour")
	public ModelAndView addAd(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		// 标题
		String title = FormUtil.getStringFiledValue(request, "title");
		// 公告标题
		String context = FormUtil.getStringFiledValue(request, "context");
		Boolean p;
		try {

			p = this.activeServer.addAdTour(title, context);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "添加成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "添加失败.");
			}
		} catch (Exception e) {
			log.error("插入活动信息出错", e);
		}
		return new ModelAndView("redirect:/active/toPage");
		
	}


	
	
	/**
	 * 以json数据查询项目列表
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="selectTourList")
	public void selectDealerList(HttpServletRequest request, HttpServletResponse response) {
		
		response.setContentType("text/json; charset=UTF-8"); // 注意设置为json
		response.setCharacterEncoding("UTF-8");// 传送中文时防止乱码
		//查询所有的经销商
		try {
			//当前页
			String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
			//每页显示的条数
			String rows = request.getParameter("rows") == null ? "20" : request.getParameter("rows").toString();
			//排序字段
			String sort = request.getParameter("sort") == null ? "title" : request.getParameter("sort").toString();
			//排序顺序
			String order = request.getParameter("order") == null ? "desc" : request.getParameter("order").toString();
			// 条件查询公告标题
			String title = request.getParameter("title") == null ? "" : request.getParameter("title").toString();
			Map<String,String> condition = new HashMap<String, String>();
			condition.put("sort", sort);
			condition.put("order", order);
			
			PaginationSupport ps = this.activeServer.getTour(page, rows,sort,order,title);
			Map<String,Object> result = new HashMap<String,Object>();
			List<Map<String,Object>> list = null;
			if(null != ps){
				list = ps.getItems();
				result.put("total", ps.getTotalCount());
				result.put("rows", list);
			}else{
				result.put("total", 0);
				result.put("rows", 0);
			}
			
			//json形式返回以树形表格展示
			JSONArray json=JSONArray.fromObject(result);
			PrintWriter out = response.getWriter();	
			//拼接的json数据多出一对“【】”，所以先去掉
			String resultStr = json.toString().substring(1,json.toString().length()-1);
			out.write(resultStr);			
			out.flush();			
			out.close();
			
		} catch (Exception e) {
			log.error("selectDealerList,以json数据查询经销商列表出错", e);
		}
	}
	
	
	/**
	 * 跳转到增加页面
	 * @return
	 */
	@RequestMapping(value="/addActivity")
	public String addActivity(HttpServletRequest request){
		
		request.setAttribute("article", null);
		return "active/addActivity";
	}
	
	/**
	 * 跳转到增加页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/seeActivity")
	public String seeActivity(HttpServletRequest request) throws Exception{
		String articleId = FormUtil.getStringFiledValue(request, "activeID");
		Map<String, Object> map = this.activeServer.queryActive(articleId);
		
		request.setAttribute("article", map);
		
		return "home/newActivedetaile";
	}
	
	
	/**
	 * 跳转到增加页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/editActivity")
	public String editActivity(HttpServletRequest request) throws Exception{
		String articleId = FormUtil.getStringFiledValue(request, "pk_id");
		Map<String, Object> map = this.activeServer.queryOneActive(articleId);
		
		request.setAttribute("article", map);
		
		return "active/addActivity";
	}
		
	/**
	 * 跳转到增加页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/editSaveActivity")
	public ModelAndView editSaveActivity(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception{
		String articleId = FormUtil.getStringFiledValue(request, "pk_id");
		// 标题
		String title = FormUtil.getStringFiledValue(request, "title");
		// 公告标题
		String context = FormUtil.getStringFiledValue(request, "context");
		System.out.println(articleId + "**" + title + "**"+context);
		
		
		try {

			boolean p = this.activeServer.editSaveActive(articleId,title, context);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "修改成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "修改失败.");
			}
		} catch (Exception e) {
			log.error("插入公告信息出错", e);
		}
		
		request.setAttribute("article", null);
		
		return new ModelAndView("redirect:/active/toPage");
	}
	
	
	/**
	 * 跳转到增加页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteActivity")
	public ModelAndView deleteActivity(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception{
		String pk_id = FormUtil.getStringFiledValue(request, "pk_id");
		try {
			boolean p = this.activeServer.deleteActivity(pk_id);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "删除成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "删除失败.");
			}
		} catch (Exception e) {
			log.error("删除活动信息出错", e);
		}
		
		request.setAttribute("article", null);
		
		return new ModelAndView("redirect:/active/toPage");
	}
	
	/**
	 * 上传活动展示的活动封面
	 * @param request
	 * @param response
	 * @param redirect
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="uploadActivtyImg")
	public ModelAndView uploadActivtyImg(HttpServletRequest request,HttpServletResponse response,RedirectAttributes  redirect) throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		// 获取当前文件的存放的路径
		String path = WebUtils.getModuleProperty("upload.fileImg");
		// 经过转换后的文件的文件名称 -- > 实际文件名转换成数字类型的
		String fileName = MyFileUtil.oneFileUpload(request, path);
		long pk_id = FormUtil.getLongFiledValue(request, "activitypkId");
		
		if(fileName != null && !"".equals(fileName)){
			if( this.activeServer.updateImg(pk_id, fileName)){
				redirect.addFlashAttribute("message", "上传图片成功！");
				return new ModelAndView("redirect:/active/toPage");
			}else{
				redirect.addFlashAttribute("message", "上传图片失败！");
				return new ModelAndView("redirect:/active/toPage");
			}
				
			
		}	
		log.error("没有选中要上传的文件！");
		redirect.addFlashAttribute("message", "请选择你要上传的文件！");
		return new ModelAndView("redirect:/active/toPage");
	}
	
	
	/**
	 * 跳转到增加页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/addFlageNew")
	public ModelAndView addFlageNew(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception{
		String articleId = FormUtil.getStringFiledValue(request, "pk_id");
		
		try {

			boolean p = this.activeServer.addFlageNew(articleId);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "设置成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "设置失败.");
			}
		} catch (Exception e) {
			log.error("设置new标记公告信息出错", e);
		}
		
		request.setAttribute("article", null);
		
		return new ModelAndView("redirect:/active/toPage");
	}
	
	
	/**
	 * 跳转到增加页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/delFlageNew")
	public ModelAndView delFlageNew(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception{
		String articleId = FormUtil.getStringFiledValue(request, "pk_id");
		
		try {

			boolean p = this.activeServer.delFlageNew(articleId);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "设置成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "设置失败.");
			}
		} catch (Exception e) {
			log.error("设置new标记公告信息出错", e);
		}
		
		request.setAttribute("article", null);
		
		return new ModelAndView("redirect:/active/toPage");
	}	
	
	
	/**
	 * 查询上传文件的模型
	 * @param request
	 * @throws Exception 
	 * @returngetuploadModelList
	 */
	@RequestMapping(value ="queryFileModelList")
	public void  queryFileModelList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String path = WebUtils.getModuleProperty("upload.actionImg");
		String downPath = WebUtils.getModuleProperty("down.actionImg");
		String fileAllName = MyFileUtil.oneFileUpload(request, path);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("data", "../"+downPath+""+fileAllName);
		JSONArray json=JSONArray.fromObject(map);
		String resultStr = json.toString().substring(1,json.toString().length()-1);
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();			
		out.write(resultStr);			
		out.flush();			
		out.close();
		
		
	}
	
}

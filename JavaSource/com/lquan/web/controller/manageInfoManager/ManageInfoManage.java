package com.lquan.web.controller.manageInfoManager;

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

import com.lquan.business.manageInfoManager.IManageInfoService;
import com.lquan.web.util.FormUtil;

@Controller
@RequestMapping(value="/manage")
public class ManageInfoManage {
	
	
	Log log = LogFactory.getLog(ManageInfoManage.class);
	
	
	@Resource(name="manageInfoService")
	private IManageInfoService manageInfoService;

	@RequestMapping(value="topage")
	public String topageList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		 return "manageInfo/manageInfo";
	}
	
	@RequestMapping(value="manage")
	public String getNoticePage(HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception{
		
		 return "manageInfo/manage";
	}
	
	
	/**
	 * 以json数据查询项目列表
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="selectManageList")
	public void manageList(HttpServletRequest request, HttpServletResponse response) {
		
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
			
			PaginationSupport ps = this.manageInfoService.manageList(page, rows,sort,order,title);
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
	 * 添加公告信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="addManageInfo")
	public ModelAndView addAd(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		// 标题
		String title = FormUtil.getStringFiledValue(request, "title");
		// 公告标题
		String context = FormUtil.getStringFiledValue(request, "context");
		String user = "admin";
		Boolean p=false;
		try {
			p = this.manageInfoService.addManageInfo(title, context, user);
			//p = this.activeServer.addAdTour(title, context);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "添加成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "添加失败.");
			}
		} catch (Exception e) {
			log.error("插入经营管理者信息出错", e);
		}
		return new ModelAndView("redirect:/manage/topage");
		
	}
	
	
	@RequestMapping(value="/editManage")
	public String editManage(HttpServletRequest request) throws Exception{
		String Manageid = FormUtil.getStringFiledValue(request, "pk_id");
		Map<String, Object> map = this.manageInfoService.queryOneManage(Manageid);
		
		request.setAttribute("manage", map);
		
		return "manageInfo/manage";
	}
	
	
	/**
	 * 跳转到增加页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/editSaveManage")
	public ModelAndView editSaveManage(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception{
		String pk_id = FormUtil.getStringFiledValue(request, "pk_id");
		// 标题
		String title = FormUtil.getStringFiledValue(request, "title");
		// 公告标题
		String context = FormUtil.getStringFiledValue(request, "context");
		System.out.println(pk_id + "**" + title + "**"+context);
		
		
		try {

			boolean p = this.manageInfoService.editManageInfo(pk_id,title, context,null);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "修改成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "修改失败.");
			}
		} catch (Exception e) {
			log.error("插入公告信息出错", e);
		}
		
		request.setAttribute("article", null);
		
		return new ModelAndView("redirect:/manage/topage");
	}
	
	/**
	 * 跳转到增加页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteManage")
	public ModelAndView deleteManage(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception{
		String pk_id = FormUtil.getStringFiledValue(request, "pk_id");
		try {
			boolean p = this.manageInfoService.delManageInfo(pk_id);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "删除成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "删除失败.");
			}
		} catch (Exception e) {
			log.error("删除经营管理信息出错", e);
		}
		
		request.setAttribute("article", null);
		
		return new ModelAndView("redirect:/manage/topage");
	}
	
	
	
	/**
	 * 跳转到增加页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/seeManage")
	public String seeActivity(HttpServletRequest request) throws Exception{
		String companyid = FormUtil.getStringFiledValue(request, "companyx");
		String departMentCode = FormUtil.getStringFiledValue(request, "departmentx");
		String gender = FormUtil.getStringFiledValue(request, "genderx");
		String useName = FormUtil.getStringFiledValue(request, "useNamex");
		String telphone = FormUtil.getStringFiledValue(request, "telphonex");
		String path = "\\upload"+"\\"+"phonefile"+"\\"+"Img"+"\\";
		List<Map<String, Object>> list = this.manageInfoService.getBookList(companyid, departMentCode, gender, useName, telphone, path);
		request.setAttribute("list", list);
		request.setAttribute("companyid", companyid);
		request.setAttribute("departMentCode", departMentCode);
		request.setAttribute("gender", gender);
		request.setAttribute("useName", useName);
		request.setAttribute("telphone", telphone);
		
		return "home/bookList";
	}
	
	
	/**
	 * 跳转到查看页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/seeManageOne")
	public String seeManageOne(HttpServletRequest request) throws Exception{
		String pk_id = FormUtil.getStringFiledValue(request, "pk_id");
		Map<String, Object> map = this.manageInfoService.queryBookOne(pk_id);
		
		request.setAttribute("book", map);
		
		return "home/bookInfo";
	}
}

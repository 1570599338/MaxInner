package com.lquan.web.controller.noticeManager;

import java.io.IOException;
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
import com.lquan.business.noticeManager.INoticeManageService;
import com.lquan.util.MyFileUtil;
import com.lquan.web.util.FormUtil;

/**
 * 最新的公告栏
 * @author liuquan
 * 
 */

@Controller
@RequestMapping(value="notice")
public class NoticeController {
	Log log = LogFactory.getLog(NoticeController.class);
	
	@Resource(name="noticeManageService")
	private INoticeManageService noticeManageService;
	
	/**
	 * 跳转到最新公告的页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/toPage")
	public String toLogin(HttpServletRequest request){
		return "notice/noticePage";
	}
	
	/**
	 * 获取公告信息的数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/toNoticePage")
	public String toMain(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/json; charset=UTF-8"); // 注意设置为json
		response.setCharacterEncoding("UTF-8");// 传送中文时防止乱码
		try {
			// 当前页
			String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
			// 每页显示的条数
			String rows = request.getParameter("rows") == null ? "20" : request.getParameter("rows").toString();
			//排序字段
			String sort = request.getParameter("sort") == null ? "noticeTime" : request.getParameter("sort").toString();
			//排序顺序
			String order = request.getParameter("order") == null ? "asc" : request.getParameter("order").toString();
			
			List<Map<String, Object>> list = this.noticeManageService.noticelist();
			//json形式返回以树形表格展示
			JSONArray json=JSONArray.fromObject(list);
		
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
						
			out.write(json.toString());			
			out.flush();			
			out.close();
		} catch (IOException e) {
			log.error("最新公告栏的页面数据出错了！",e);
		}
	   return null;
	}
	
	/**
	 * 以json数据查询项目列表
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="selectNoticeList")
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
			String sort = request.getParameter("sort") == null ? "notice.noticeTime" : request.getParameter("sort").toString();
			//排序顺序
			String order = request.getParameter("order") == null ? "desc" : request.getParameter("order").toString();
			// 条件查询公告标题
			String title = request.getParameter("title") == null ? "" : request.getParameter("title").toString();
			Map<String,String> condition = new HashMap<String, String>();
			condition.put("sort", sort);
			condition.put("order", order);
			
			//PaginationSupport ps = dealerService.selectDealerList(page, rows,project,condition);
			PaginationSupport ps = this.noticeManageService.getNotice(page, rows,sort,order,title);
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
	 * @throws Exception 
	 */
	@RequestMapping(value="addAdNotice")
	public ModelAndView addAd(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception{
		// 标题
		String title = FormUtil.getStringFiledValue(request, "add_title");
		// 公告标题
		String message = FormUtil.getStringFiledValue(request, "add_message");
		String path = WebUtils.getModuleProperty("upload.noticeDir");
		String fileAllName = MyFileUtil.oneFileUploadName(request, path);
		Boolean p;
		try {

			p = this.noticeManageService.addAdNotice(title, message);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "添加成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "添加失败.");
			}
		} catch (Exception e) {
			log.error("插入公告信息出错", e);
		}
		return new ModelAndView("redirect:/notice/toPage");
	}
	
	/**
	 * 添加公告信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="editAdNotice")
	public ModelAndView editAd(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		// 公告的主键
		long id = FormUtil.getLongFiledValue(request, "pk_id");
		// 标题
		String title = FormUtil.getStringFiledValue(request, "add_title");
		// 公告标题
		String message = FormUtil.getStringFiledValue(request, "add_message");
		Boolean p;
		try {

			p = this.noticeManageService.editAdNotice(id, title, message);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "修改成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "修改失败.");
			}
		} catch (Exception e) {
			log.error("修改公告信息出错", e);
		}
		return new ModelAndView("redirect:/notice/toPage");
	}
	/**
	 * 删除用户的操作
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("delNotice")
	public ModelAndView delNotice(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) {
		String id = request.getParameter("pk_id") == null ? "" : request.getParameter("pk_id").toString();
		// 将列表对应的id放入map中
		try {
			if (this.noticeManageService.delNotice(id)) {
				redirectAttributes.addFlashAttribute("message", "删除成功。");
			} else {
				redirectAttributes.addFlashAttribute("message", "删除失败。");
			}
		} catch (Exception e) {
			log.error("获取用户列表出错", e);
		}
		return new ModelAndView("redirect:/notice/toPage");
	}
	
	
	
	/**
	 * 删除用户的操作
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("getOneNotice")
	public void getOneNotice(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) {
		String id = request.getParameter("pk_id") == null ? "" : request.getParameter("pk_id").toString();
		// 将列表对应的id放入map中
		response.setContentType("text/json; charset=UTF-8"); // 注意设置为json
		response.setCharacterEncoding("UTF-8");// 传送中文时防止乱码
		
		String pk_id = request.getParameter("pk_id");
		try {
			if(pk_id != null && !"".equals(pk_id)){
				Map<String, Object> result = this.noticeManageService.queryNoticeType(pk_id);
				//返回参数
				Gson gson = new Gson();
				String jsonObject = gson.toJson(result);
				PrintWriter out = response.getWriter();
				out.print(jsonObject);
				out.flush();
				out.close();
			}
		} catch (Exception e) {
               log.error("根据pk_id查询文件类型数据出错",e);			
		}
	}

	
	
	/**
	 * 添加公告信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="addFlageNew")
	public ModelAndView addFlageNew(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		// 公告的主键
		long id = FormUtil.getLongFiledValue(request, "pk_id");
		Boolean p;
		try {

			p = this.noticeManageService.addFlageNew(id);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "设置new成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "设置new失败.");
			}
		} catch (Exception e) {
			log.error("设置new信息出错", e);
		}
		return new ModelAndView("redirect:/notice/toPage");
	}
	
	/**
	 * 添加公告信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="delFlageNew")
	public ModelAndView delFlageNew(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		// 公告的主键
		long id = FormUtil.getLongFiledValue(request, "pk_id");
		Boolean p;
		try {

			p = this.noticeManageService.delFlageNew(id);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "清除new图标成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "清除new图标失败.");
			}
		} catch (Exception e) {
			log.error("设置new信息出错", e);
		}
		return new ModelAndView("redirect:/notice/toPage");
	}
}

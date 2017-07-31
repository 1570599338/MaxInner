package com.lquan.web.controller.tourManager;

import java.io.PrintWriter;
import java.util.ArrayList;
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
import snt.common.dao.base.PrimaryKeyGenerator;
import snt.common.web.util.Constants;
import snt.common.web.util.WebUtils;

import com.lquan.business.tourManager.ITourManagerServer;
import com.lquan.util.CompressFileUseAnt;
import com.lquan.util.MyFileUtil;
import com.lquan.web.util.FormUtil;

/**
 * @Description 活动展示
 * @author liuquan
 *
 */
@Controller
@RequestMapping(value="tour")
public class TourController {
	Log log = LogFactory.getLog(TourController.class);
	
	@Resource(name="tourManagerServer")
	private ITourManagerServer tourServer;
	
	@RequestMapping(value="/toPage")
	public String 	topage(){
		
		return "tour/tour";
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
			String order = request.getParameter("order") == null ? "asc" : request.getParameter("order").toString();
			// 条件查询公告标题
			String title = request.getParameter("title") == null ? "" : request.getParameter("title").toString();
			Map<String,String> condition = new HashMap<String, String>();
			condition.put("sort", sort);
			condition.put("order", order);
			
			PaginationSupport ps = this.tourServer.getTour(page, rows,sort,order,title);
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
	@RequestMapping(value="addAdTour")
	public ModelAndView addAd(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		// 标题
		String title = FormUtil.getStringFiledValue(request, "add_title");
		// 公告标题
		String message = FormUtil.getStringFiledValue(request, "add_message");
		Boolean p;
		try {

			p = this.tourServer.addAdTour(title, message);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "添加成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "添加失败.");
			}
		} catch (Exception e) {
			log.error("插入公告信息出错", e);
		}
		return new ModelAndView("redirect:/tour/toPage");
	}
	
	/**
	 * 添加公告信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="editAdTour")
	public ModelAndView editAd(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		// 公告的主键
		long id = FormUtil.getLongFiledValue(request, "pk_id");
		// 标题
		String title = FormUtil.getStringFiledValue(request, "add_title");
		// 公告标题
		String message = FormUtil.getStringFiledValue(request, "add_message");
		Boolean p;
		try {

			p = this.tourServer.editAdTour(id, title, message);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "修改成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "修改失败.");
			}
		} catch (Exception e) {
			log.error("修改公告信息出错", e);
		}
		return new ModelAndView("redirect:/tour/toPage");
	}
	/**
	 * 删除用户的操作
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("delTour")
	public ModelAndView deleUsers(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) {
		String id = request.getParameter("pk_id") == null ? "" : request.getParameter("pk_id").toString();
		// 将列表对应的id放入map中
		try {
			if (this.tourServer.delTour(id)) {
				redirectAttributes.addFlashAttribute("message", "删除成功。");
			} else {
				redirectAttributes.addFlashAttribute("message", "删除失败。");
			}
		} catch (Exception e) {
			log.error("获取用户列表出错", e);
		}
		return new ModelAndView("redirect:/tour/toPage");
	}
	
	/**
	 * 上传分析报告
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="toupreport")
	public ModelAndView topReport(HttpServletRequest request,HttpServletResponse response,RedirectAttributes  redirect) throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		// 获取当前文件的存放的路径
		String path = WebUtils.getModuleProperty("upload.fileImg");
		// 经过转换后的文件的文件名称 -- > 实际文件名转换成数字类型的
		String fileName = MyFileUtil.oneFileUpload(request, path);
		//当前用户
		String userName =  "admin";
		long tourId = FormUtil.getLongFiledValue(request, "tourId");
		// 图片的描述信息
		String message = FormUtil.getStringFiledValue(request, "Img_message");
		
		if(fileName != null && !"".equals(fileName)){
			if(this.tourServer.updateImg(tourId, fileName, message, userName)){
				redirect.addFlashAttribute("message", "上传图片成功！");
				return  new ModelAndView("redirect:/tour/toPage");
			}else{
				redirect.addFlashAttribute("message", "上传图片失败！");
				return  new ModelAndView("redirect:/tour/toPage");
			}
				
			
		}	
		log.error("没有选中要上传的文件！");
		redirect.addFlashAttribute("message", "请选择你要上传的文件！");
		return  new ModelAndView("redirect:/tour/toPage");
	}
	
	
	
	/**
	 * 上传图片编辑
	 * @param request
	 * @param response
	 * @param redirect
	 * @throws Exception
	 */
	@RequestMapping(value="/seePhoto")
	public void seePhoto(HttpServletRequest request,HttpServletResponse response,RedirectAttributes  redirect) throws Exception{
		String activeID = request.getParameter("activeID") == null ? "0" : request.getParameter("activeID").toString();
		// 获取图片的文件的位置
		String baseImgPath = WebUtils.getModuleProperty("down.fileImg");
		//List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		
		
		List<Map<String, Object>> result  = tourServer.getActiveImg(activeID,baseImgPath);
		
		//json形式返回以树形表格展示
		JSONArray json=JSONArray.fromObject(result);
		PrintWriter out = response.getWriter();	
		//拼接的json数据多出一对“【】”，所以先去掉
		String resultStr = json.toString().substring(1,json.toString().length()-1);
		out.write(json.toString());			
		out.flush();			
		out.close();
			
	}
	
	
}










	
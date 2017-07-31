package com.lquan.web.controller.ruleManager;

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

import com.lquan.business.ruleManager.IRuleServer;
import com.lquan.util.MyFileUtil;
import com.lquan.web.controller.noticeManager.NoticeController;
import com.lquan.web.util.FormUtil;

/**
 * @Description主要是规章制度的模块
 * @author liuquan
 *
 */
@Controller
@RequestMapping(value="rule")
public class RuleController {
	Log log = LogFactory.getLog(NoticeController.class);
	@Resource(name="ruleManagerServer")
	private IRuleServer ruleServer;
	
	/**
	 * 跳转到最新公告的页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/toPage")
	public String toLogin(HttpServletRequest request){
		return "rule/rule";
	}
	
	
	/**
	 * 以json数据查询项目列表
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="selectRuleList")
	public void selectRuleList(HttpServletRequest request, HttpServletResponse response) {
		
		response.setContentType("text/json; charset=UTF-8"); // 注意设置为json
		response.setCharacterEncoding("UTF-8");// 传送中文时防止乱码
		//查询所有的经销商
		try {
			//当前页
			String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
			//每页显示的条数
			String rows = request.getParameter("rows") == null ? "20" : request.getParameter("rows").toString();
			//排序字段
			String sort = request.getParameter("sort") == null ? "createAt" : request.getParameter("sort").toString();
			//排序顺序
			String order = request.getParameter("order") == null ? "desc" : request.getParameter("order").toString();
			// 条件查询公告标题
			String title = request.getParameter("fileName") == null ? "" : request.getParameter("fileName").toString();
			Map<String,String> condition = new HashMap<String, String>();
			condition.put("sort", sort);
			condition.put("order", order);
			
			PaginationSupport ps = this.ruleServer.getRule(page, rows,sort,order,title);
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
			log.error("selectRuleList,以json数据查询经销商列表出错", e);
		}
	}
	
	
	/**
	 * 上传规章制度
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/uploadFile")
	public ModelAndView topReport(HttpServletRequest request,HttpServletResponse response,RedirectAttributes  redirect) throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		//当前用户
		String userName =  "admin";
		// 获取当前文件的存放的路径
		String path = WebUtils.getModuleProperty("upload.fileRule");
		// 文件类型
		int upfileType = FormUtil.getIntegerFiledValue(request, "upfileType");
		path = path + "//" +upfileType;
		// 经过转换后的文件的文件名称 -- > 实际文件名转换成数字类型的
		String fileAllName = MyFileUtil.oneFileUploadName(request, path);
		// IT未来发展之路.pdf
		String fileName = fileAllName.substring(0, fileAllName.lastIndexOf("."));
	
		
		if(fileName != null && !"".equals(fileName)){
			if(this.ruleServer.updateImg(upfileType, fileName, fileAllName,userName)){
				redirect.addFlashAttribute("message", "上传规章制度成功！");
				return  new ModelAndView("redirect:/rule/toPage");
			}else{
				redirect.addFlashAttribute("message", "上传规章制度失败！");
				return  new ModelAndView("redirect:/rule/toPage");
			}
				
			
		}	
		log.error("没有选中要上传的文件！");
		redirect.addFlashAttribute("message", "请选择你要上传的文件！");
		return  new ModelAndView("redirect:/rule/toPage");
	}
	
	/**
	 * 删除上传文件的记录
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("deleteFile")
	public ModelAndView deleteFile(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) {
		String id = request.getParameter("pk_id") == null ? "" : request.getParameter("pk_id").toString();
		// 将列表对应的id放入map中
		try {
			if (this.ruleServer.deleRule(id)) {
				redirectAttributes.addFlashAttribute("message", "删除成功。");
			} else {
				redirectAttributes.addFlashAttribute("message", "删除失败。");
			}
		} catch (Exception e) {
			log.error("删除上传的规章制度出错", e);
		}
		return  new ModelAndView("redirect:/rule/toPage");
	}
	
	/**
	 * 添加new图标
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("addFlageNew")
	public ModelAndView addFlageNew(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) {
		String id = request.getParameter("pk_id") == null ? "" : request.getParameter("pk_id").toString();
		// 将列表对应的id放入map中
		try {
			if (this.ruleServer.addFlageNew(id)) {
				redirectAttributes.addFlashAttribute("message", "添加new图标成功。");
			} else {
				redirectAttributes.addFlashAttribute("message", "添加new图标失败。");
			}
		} catch (Exception e) {
			log.error("删除上传的规章制度出错", e);
		}
		return  new ModelAndView("redirect:/rule/toPage");
	}

	/**
	 * 删除new图标
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("delFlageNew")
	public ModelAndView delFlageNew(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) {
		String id = request.getParameter("pk_id") == null ? "" : request.getParameter("pk_id").toString();
		// 将列表对应的id放入map中
		try {
			if (this.ruleServer.delFlageNew(id)) {
				redirectAttributes.addFlashAttribute("message", "删除new图标成功。");
			} else {
				redirectAttributes.addFlashAttribute("message", "删除new图标失败。");
			}
		} catch (Exception e) {
			log.error("删除上传的规章制度出错", e);
		}
		return  new ModelAndView("redirect:/rule/toPage");
	}
}

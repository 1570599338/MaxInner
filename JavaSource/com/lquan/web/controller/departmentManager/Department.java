package com.lquan.web.controller.departmentManager;

import java.io.PrintWriter;
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

import com.google.gson.Gson;
import com.lquan.business.departmentManager.IDepartmentService;
import com.lquan.web.util.FormUtil;
@Controller
@RequestMapping(value="department")
public class Department {
	Log log = LogFactory.getLog(Department.class);

	@Resource(name="departmentService")
	private IDepartmentService departmentService;
	
	/**
	 * 跳转到模板类型的页面
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/toDepartmentPage")
	public String toLogin(HttpServletRequest request) throws Exception{
		
		List<Map<String, Object>>  list = this.departmentService.getCompanyAndDepartmentList();
		request.setAttribute("list", list);
		
		return "department/department";
	}
	
	
	/**
	 * 新建公司
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="addCompany")
	public ModelAndView addCompany(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		// 获取公司的名称
		String company = FormUtil.getStringFiledValue(request, "company");
		String user = "admin";
		Boolean p;
		try {

			p = this.departmentService.addCompany(company, user);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "添加成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "添加失败.");
			}
		} catch (Exception e) {
			log.error("添加出错", e);
		}
		return new ModelAndView("redirect:/department/toDepartmentPage");
	}
	
	/**
	 * 修改公司信息
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="editCompany")
	public ModelAndView editCompany(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		// 公司名称
		String company = FormUtil.getStringFiledValue(request, "company");
		// 公司的id
		String companyId = FormUtil.getStringFiledValue(request, "companyId");
		Boolean p;
		try {

			p = this.departmentService.editCompany(companyId, company);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "修改成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "修改失败.");
			}
		} catch (Exception e) {
			log.error("修改公告信息出错", e);
		}
		return new ModelAndView("redirect:/department/toDepartmentPage");
	}
	
	
	/**
	 * 查询该公司下是否有部门
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryDepartment")
	public void queryDepartment(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/json; charset=UTF-8"); // 注意设置为json
		response.setCharacterEncoding("UTF-8");// 传送中文时防止乱码
		
		String pk_id = request.getParameter("pk_id");
		try {
			if(pk_id != null && !"".equals(pk_id)){
				List<Map<String, Object>> result = this.departmentService.queryDepartment(pk_id);
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
	 * 删除公司记录
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/delComany")
	public ModelAndView delComany(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		// 获取文件的主键
		String pk_id = FormUtil.getStringFiledValue(request, "pk_id");
		Boolean p;
		try {
			p = this.departmentService.delComany(pk_id);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "删除成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "删除失败.");
			}
		} catch (Exception e) {
			log.error("删除文件模板类型出错！", e);
		}
		return new ModelAndView("redirect:/department/toDepartmentPage");
	}
	
	/**
	 * 获取公司列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value ="queryCompanyList")
	public void  queryFileModelList(HttpServletRequest request,HttpServletResponse response){
		
		try {
			List<Map<String, Object>> modellist = this.departmentService.getCompanyList();			
			//json形式返回以树形展示
			JSONArray json=JSONArray.fromObject(modellist);
			
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();			
			out.write(json.toString());			
			out.flush();			
			out.close();
			
		} catch (NumberFormatException e) {
			log.error("queryFileTypeList,查询类型出错！", e);
		} catch (Exception e) {
			log.error("queryFileTypeList,查询模板信息出错！", e);
		}
	}
	
	/**
	 * 添加下载文件类型
	 */
	@RequestMapping(value="addDepartment")
	public ModelAndView addDepartment(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		// 文件模板
		String companyId = FormUtil.getStringFiledValue(request, "companyName");
		// 文件类型
		String departmentName = FormUtil.getStringFiledValue(request, "departMent");
		// 文件类型
		String code = FormUtil.getStringFiledValue(request, "departCode");
		// 用户
		String user  = "";
		
		Boolean p;
		try {
			p = this.departmentService.addDepartment(companyId, departmentName,code,user);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "添加成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "添加失败.");
			}
		} catch (Exception e) {
			log.error("插入公告信息出错", e);
			redirectAttributes.addFlashAttribute("message", "添加失败.");
		}
		return new ModelAndView("redirect:/department/toDepartmentPage");
	}
	
	/**
	 * 添加下载文件类型
	 */
	@RequestMapping(value="editDepartment")
	public ModelAndView editDepartment(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		// 文件模板
		String departmentId = FormUtil.getStringFiledValue(request, "departMentId");
		// 文件类型
		String departmentName = FormUtil.getStringFiledValue(request, "departMent");
		
		Boolean p;
		try {
			p = this.departmentService.editDepartment(departmentId, departmentName);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "修改成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "修改失败.");
			}
		} catch (Exception e) {
			log.error("修改信息出错", e);
		}
		return new ModelAndView("redirect:/department/toDepartmentPage");
	}
	
	/**
	 * 删除公司记录
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/delDepartment")
	public ModelAndView delDepartment(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		// 获取文件的主键
		String pk_id = FormUtil.getStringFiledValue(request, "departMentId");
		Boolean p;
		try {
			p = this.departmentService.delDepartment(pk_id);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "删除成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "删除失败.");
			}
		} catch (Exception e) {
			log.error("删除部门信息出错！", e);
		}
		return new ModelAndView("redirect:/department/toDepartmentPage");
	}
	
	
	
}

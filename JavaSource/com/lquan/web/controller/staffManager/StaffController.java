package com.lquan.web.controller.staffManager;

import java.io.FileInputStream;
import java.io.OutputStream;
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
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lquan.business.departmentManager.IDepartmentService;
import com.lquan.business.staffManager.IStaffService;
import com.lquan.common.Constants;
import com.lquan.parseExcel.StaffExcelWb;
import com.lquan.util.CompressFileUseAnt;
import com.lquan.util.MyFileUtil;
import com.lquan.web.util.FormUtil;

import snt.common.dao.base.PaginationSupport;
import snt.common.dao.base.PrimaryKeyGenerator;
import snt.common.web.util.WebUtils;

/**
 * @Decription 员工信息
 * @author lquan
 * @Date 2016年11月13日14:29:44
 */
@Controller
@RequestMapping(value="staff")
public class StaffController {
	private Log log = LogFactory.getLog(StaffController.class);
	
	@Resource(name="staffService")
	private IStaffService staffService;

	/**
	 * 跳转用户管理页面
	 * 
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "toStaffPage")
	public String toUserManage(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) {

		return "staff/staff";
	}
	
	/**
	 * 以json数据查询项目列表
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="getStaffPage")
	public void getStaffPage(HttpServletRequest request, HttpServletResponse response) {
		
		response.setContentType("text/json; charset=UTF-8"); // 注意设置为json
		response.setCharacterEncoding("UTF-8");// 传送中文时防止乱码
		//查询所有的经销商
		try {
			//当前页
			String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
			//每页显示的条数
			String rows = request.getParameter("rows") == null ? "20" : request.getParameter("rows").toString();
			//排序字段
			String sort = request.getParameter("sort") == null ? "c.company" : request.getParameter("sort").toString();
			//排序顺序
			String order = request.getParameter("order") == null ? "asc" : request.getParameter("order").toString();
			// 条件查询公告标题
			String title = request.getParameter("fileName") == null ? "" : request.getParameter("fileName").toString();
			String company = request.getParameter("log_name") == null ? "" : request.getParameter("log_name").toString();
			String depart = request.getParameter("user_name") == null ? "" : request.getParameter("user_name").toString();
			String isforbid = request.getParameter("isforbid") == null ? "" : request.getParameter("isforbid").toString();
			Map<String,String> condition = new HashMap<String, String>();
			condition.put("sort", sort);
			condition.put("order", order);
			condition.put("company", company);
			condition.put("depart", depart);
			condition.put("isforbid", isforbid);
			
			PaginationSupport ps = this.staffService.getStaffPage(page, rows,sort,order,title,condition);
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
	 * 新建公司
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="addStaff")
	public ModelAndView addCompany(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		// 公司
		String company = FormUtil.getStringFiledValue(request, "company");
		// 部门
		String department = FormUtil.getStringFiledValue(request, "department");
		// 职员名称
		String username = FormUtil.getStringFiledValue(request, "username");
		// 性别
		String gender = FormUtil.getStringFiledValue(request, "gender");
		// 座机
		String telphone = FormUtil.getStringFiledValue(request, "telphone");
		// 手机
		String cellphone = FormUtil.getStringFiledValue(request, "cellphone");
		// 邮件
		String email = FormUtil.getStringFiledValue(request, "email");
		String user = "admin";
		
		Map<String,Object> con = new HashMap<String,Object>();
		con.put("email",email);
		con.put("cellphone",cellphone);
		con.put("telphone",telphone);
		con.put("gender",gender );
		con.put("username",username);
		con.put("department",department);
		con.put("company", company);
		Boolean p;
		try {
			p = this.staffService.addStaff(con, user);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "添加成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "添加失败.");
			}
		} catch (Exception e) {
			log.error("添加出错", e);
		}
		return new ModelAndView("redirect:/staff/toStaffPage");
	}
	
	
	/**
	 * 新建公司
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="editStaff")
	public ModelAndView editStaff(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		//员工信息
		String staffId = FormUtil.getStringFiledValue(request, "staffId");
		// 公司
		String company = FormUtil.getStringFiledValue(request, "company");
		// 部门
		String department = FormUtil.getStringFiledValue(request, "department");
		// 职员名称
		String username = FormUtil.getStringFiledValue(request, "username");
		// 性别
		String gender = FormUtil.getStringFiledValue(request, "gender");
		// 座机
		String telphone = FormUtil.getStringFiledValue(request, "telphone");
		// 手机
		String cellphone = FormUtil.getStringFiledValue(request, "cellphone");
		// 邮件
		String email = FormUtil.getStringFiledValue(request, "email");
		String user = "admin";
		
		Map<String,Object> con = new HashMap<String,Object>();
		con.put("email",email);
		con.put("cellphone",cellphone);
		con.put("telphone",telphone);
		con.put("gender",gender );
		con.put("username",username);
		con.put("department",department);
		con.put("company", company);
		con.put("staffId", staffId);
		Boolean p;
		try {
			p = this.staffService.editStaff(con, user);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "修改成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "修改失败.");
			}
		} catch (Exception e) {
			log.error("修改出错", e);
		}
		return new ModelAndView("redirect:/staff/toStaffPage");
	}
	
	
	/**
	 * @Description:下载用户信息模板
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "downStaffTemp")
	public ModelAndView downLoadUserTemp(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String filePath = WebUtils.getModuleProperty("tempExcel.staffInfo");
			int index = filePath.lastIndexOf("/") + 1;
			// 文件全名带后缀
			String filerealname = filePath.substring(index);
			// 文件真实路径
			filePath = filePath.substring(0, index - 1);
			MyFileUtil.download(response, filePath, filerealname);
		} catch (Exception e) {
			log.error("downLoadUserTemp,下载用户信息模板", e);
		}
		return null;
	}
	
	
	
	/**
	 * 上传经销店excel数据,并验证数据的正确性存入数据库
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "uploadStaff")
	public String uploadStaff(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) {
		// 上传文件至临时目录，存放临时文件
		String tmpPath = WebUtils.getModuleProperty("upload.phoneDir.tempExcel");
		response.setContentType("text/json; charset=utf-8"); // 注意设置为json
		response.setCharacterEncoding("utf-8");// 传送中文时防止乱码
		String loginName = "admin";
		
		// 上传文件
		try {
			String[] filePath = MyFileUtil.uploadFile(request, tmpPath);
			// 验证excel 工作表和表头是否符合要求，有错误则返回错误信息，否则返回空串
			String exameExcelFormat = MyFileUtil.exameIfExcelFormatRight(new FileInputStream(filePath[0]), StaffExcelWb.strArray);
			if (!"".equals(exameExcelFormat)) {
				request.setAttribute("message", exameExcelFormat);
				return "staff/staff";
			}
			StaffExcelWb dealerWb = new StaffExcelWb(new FileInputStream(filePath[0]), 0);
			// 生成批次号
			long batchNum = PrimaryKeyGenerator.getLongKey();
			String tempTable = "t_temp_Staff" + batchNum;
			// 数据校验
			List<Map<String, Object>> list = this.staffService.checkUserData(batchNum, tempTable, dealerWb,loginName);
			// 如果有错误数据,向客户端写入一个包含错误提示的excel
			if (list != null && list.size() > 0) {
				HSSFWorkbook workbook = dealerWb.getWookBook();
				MyFileUtil.exportDataToExcel(workbook, list);
				OutputStream out = MyFileUtil.download(response, "StaffExcel_"+ batchNum + ".xls");
				workbook.write(out);
				out.flush();
				out.close();
				workbook = null;
			} else {
				// 插入数据,数据没有问题，将数据插入到正式表中
				int result = staffService.importData(tempTable, dealerWb);
				request.setAttribute("message", "成功导入 " + result + " 条数据！");
				return "staff/staff";
			}
		} catch (Exception e) {
			log.error("uploadStaff,上传员工excel数据出错。", e);
		}
		return null;
	}

	
	
	/**
	 * 上传分析报告
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="toUpdateHeadImg")
	public ModelAndView topReport(HttpServletRequest request,HttpServletResponse response,RedirectAttributes  redirect) throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		//User user = (User) request.getSession().getAttribute("user");
		String company = "";
		String departMent = "";
		boolean uploadSuccess = false;
		String path = WebUtils.getModuleProperty("upload.phoneDir.zip");
		String unZipPath = WebUtils.getModuleProperty("upload.phoneDir.unzip");
		String pathImg  = WebUtils.getModuleProperty("upload.phoneDir.Img");
		String fileNamefather = MyFileUtil.oneFileUpload(request, path);//压缩包文件名字	unZipPath 
		if(fileNamefather != null && !"".equals(fileNamefather)){
			String file_name = fileNamefather;
			//计算本次上传的条数
			int sum = 0;
			List<String> fileNameList = null;
			try {
				fileNameList = CompressFileUseAnt.unZip(path+ "\\"+file_name, unZipPath);
				List<Object[]> staffParamsList = new ArrayList<Object[]>();//插入经销商参数
				List<Object[]> reportName = new ArrayList<Object[]>();//数据库中报告名称
				Object[] staffParams= null; 
				if (fileNameList != null && !fileNameList.isEmpty() && fileNameList.size() > 0){
					//数据库中已上传过图片的用户
					reportName = this.staffService.getStaffHeadImg(company, departMent);
					for (String filename : fileNameList){
						if(this.staffService.valudateFileFormat(filename)){
							String userName = filename.substring(0,filename.lastIndexOf(".") );
							boolean staffNameMatch = this.staffService.checkStaffName(userName);
							if(reportName.contains(filename)){
								log.error("上传文件，向数据库插入数据出错，已有该文件！");
								redirect.addFlashAttribute("color","#FA3020");
								redirect.addFlashAttribute("message", "已有文件"+filename+"名称重复！");
								return new ModelAndView("redirect:/staff/toStaffPage");
							}	
							if(staffNameMatch){
								String tempname = MyFileUtil.CopyFile(unZipPath, filename, pathImg);
								staffParams = new Object[]{tempname,userName};
								staffParamsList.add(staffParams);
							}else{
								redirect.addFlashAttribute("color","#FA3020");
								redirect.addFlashAttribute("message", "不存在员工:"+userName+" ！");
								return new ModelAndView("redirect:/staff/toStaffPage");
							}		
						} else{
							redirect.addFlashAttribute("message", "文件格式错误,请上传后缀名为.JPG、.png、.jpeg的文件");
							return new ModelAndView("redirect:/staff/toStaffPage");
						}
					}
					int dealerSuccess[] = this.staffService.updateStaffHeadImg(staffParamsList);
					if(dealerSuccess == null || dealerSuccess.length == 0){
						log.error("保存文件，向数据库插入数据出错！");
						//redirect.addFlashAttribute("color","#FA3020");
						redirect.addFlashAttribute("message", "上传文件出错！");
						return new ModelAndView("redirect:/staff/toStaffPage");
					}else{
						for(int i : dealerSuccess){
							sum += i;
						}
					}
				}
				uploadSuccess = true;
			} catch (Exception e) {
				log.error("上传文件出错啦！！！", e);
				redirect.addFlashAttribute(Constants.ERRORKEY, e);
			}
			if (fileNameList == null || fileNameList.size() == 0) {
				//throw new BackendException("no file have been upload!!!");
				redirect.addFlashAttribute("color","#FA3020");
				redirect.addFlashAttribute("message", "上传文件的压缩包里面没有文件");
				return new ModelAndView("redirect:/staff/toStaffPage");
			}
			if(uploadSuccess ){
				redirect.addFlashAttribute("color","#20F12F");
				redirect.addFlashAttribute("message", "该次共成功上传" + sum + "条记录！");
				return new ModelAndView("redirect:/staff/toStaffPage");
			}
			log.error("保存文件，向数据库插入数据出错！");
			redirect.addFlashAttribute("color","#FA3020");
			redirect.addFlashAttribute("message", "上传文件出错！");
			return  new ModelAndView("redirect:/touploadreport/touploadreportpage");
		}else{
			log.error("没有选中要上传的文件！");
			redirect.addFlashAttribute("color","#FA3020");
			redirect.addFlashAttribute("message", "请选择你要上传的文件！");
			return  new ModelAndView("redirect:/touploadreport/touploadreportpage");
		}
		
	}
	
	
	/**
	 * 改变员工在职状态
	 * @param request
	 * @return
	 */
	@RequestMapping(value="forbidStaff")
	public ModelAndView forbidStaff(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		// 主键
		String pk_id = FormUtil.getStringFiledValue(request, "pk_id");
		// 状态
		Integer type = FormUtil.getIntegerFiledValue(request, "stat");
		Boolean p;
		try {

			p = this.staffService.forbidStaff(pk_id, type);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "修改成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "修改失败.");
			}
		} catch (Exception e) {
			log.error("修改员工信息出错", e);
		}
		return new ModelAndView("redirect:/staff/toStaffPage");
	}
	
	
	/**
	 * 改变员工在职状态
	 * @param request
	 * @return
	 */
	@RequestMapping(value="deleStaff")
	public ModelAndView deleStaff(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		// 主键
		String pk_id = FormUtil.getStringFiledValue(request, "pk_id");
		Boolean p;
		try {

			p = this.staffService.deleStaff(pk_id);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "删除成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "删除失败.");
			}
		} catch (Exception e) {
			log.error("删除员工信息出错", e);
		}
		return new ModelAndView("redirect:/staff/toStaffPage");
	}
	
	
	
	
	
	
	
	
	
	
	
	
}

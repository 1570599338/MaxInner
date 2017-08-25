package com.lquan.web.controller.holidayManager;

import java.io.FileInputStream;
import java.io.OutputStream;
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
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import snt.common.dao.base.PaginationSupport;
import snt.common.dao.base.PrimaryKeyGenerator;
import snt.common.web.util.WebUtils;

import com.lquan.business.holidayManager.IHolidayService;
import com.lquan.business.staffManager.IStaffService;
import com.lquan.parseExcel.StaffExcelWb;
import com.lquan.parseExcel.VacationExcelWeb;
import com.lquan.util.MyFileUtil;
import com.lquan.web.controller.vacationManager.VacationManager;
import com.lquan.web.util.FormUtil;

/**
 * 年假查询文件
 * @author liuquan
 */

@Controller
@RequestMapping(value="/holiday")
public class HolidayController {
	
	private Log log = LogFactory.getLog(VacationManager.class);
	
	@Resource(name="holidayService")
	private IHolidayService holidayService;
	
	@RequestMapping(value="/topage")
	public String toHolidayPage(){
		
		return "holiday/holiday";
	}
	
	/**
	 * @Decription  获取年假的信息
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="getVacationlist")
	public void getVacationlist(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/json; charset=UTF-8"); // 注意设置为json
		response.setCharacterEncoding("UTF-8");// 传送中文时防止乱码
		//当前页
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		//每页显示的条数
		String rows = request.getParameter("rows") == null ? "20" : request.getParameter("rows").toString();
		//排序字段
		String sort = request.getParameter("sort") == null ? "username" : request.getParameter("sort").toString();
		//排序顺序
		String order = request.getParameter("order") == null ? "asc" : request.getParameter("order").toString();
		
		String fileName = request.getParameter("username") == null ? "" : request.getParameter("fileName").toString();
		String type = request.getParameter("filetype") == null ? "" : request.getParameter("filetype").toString();
		
		PaginationSupport ps = this.holidayService.getRuleList(page, rows, sort,order, fileName,type);
		
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
	}
	
	/**
	 * 上传经销店excel数据,并验证数据的正确性存入数据库
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "uploadVacation")
	public String uploadStaff(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) {
		// 月份
		String mouth = FormUtil.getStringFiledValue(request, "mouth");
		// 上传文件至临时目录，存放临时文件
		String tmpPath = WebUtils.getModuleProperty("upload.vacation.tempExcel");
		response.setContentType("text/json; charset=utf-8"); // 注意设置为json
		response.setCharacterEncoding("utf-8");// 传送中文时防止乱码
		String loginName = "admin";
		
		// 上传文件
		try {
			String[] filePath = MyFileUtil.uploadFile(request, tmpPath);
			// 验证excel 工作表和表头是否符合要求，有错误则返回错误信息，否则返回空串
			String exameExcelFormat = MyFileUtil.exameIfExcelFormatRight(new FileInputStream(filePath[0]), VacationExcelWeb.strArray);
			if (!"".equals(exameExcelFormat)) {
				request.setAttribute("message", exameExcelFormat);
				return "holiday/holiday";
			}
			VacationExcelWeb dealerWb = new VacationExcelWeb(new FileInputStream(filePath[0]), 0);
			// 生成批次号
			long batchNum = PrimaryKeyGenerator.getLongKey();
			String tempTable = "t_temp_Holiday" + batchNum;
			// 数据校验
			List<Map<String, Object>> list = this.holidayService.checkUserData(batchNum, tempTable, dealerWb,loginName);
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
				int result = holidayService.importData(tempTable, dealerWb);
				request.setAttribute("message", "成功导入 " + result + " 条数据！");
				return "holiday/holiday";
			}
		} catch (Exception e) {
			log.error("uploadStaff,上传员工excel数据出错。", e);
		}
		return null;
	}

}

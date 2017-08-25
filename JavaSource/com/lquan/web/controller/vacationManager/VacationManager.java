package com.lquan.web.controller.vacationManager;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import snt.common.dao.base.PrimaryKeyGenerator;
import snt.common.web.util.WebUtils;

import com.lquan.business.staffManager.IStaffService;
import com.lquan.parseExcel.StaffExcelWb;
import com.lquan.util.MyFileUtil;
import com.lquan.web.controller.staffManager.StaffController;

/**
 * 
 * 年假管理部分
 * @author liuquan
 *
 */

@Controller
@RequestMapping(value="vacation")
public class VacationManager {
	
	private Log log = LogFactory.getLog(VacationManager.class);
	
	@Resource(name="staffService")
	private IStaffService staffService;
	
	/**
	 * 上传经销店excel数据,并验证数据的正确性存入数据库
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "uploadVacation")
	public String uploadStaff(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) {
		// 上传文件至临时目录，存放临时文件
		String tmpPath = WebUtils.getModuleProperty("upload.vacation.tempExcel");
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

}

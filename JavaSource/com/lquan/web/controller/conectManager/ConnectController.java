package com.lquan.web.controller.conectManager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lquan.business.connectManager.IConnectService;
import com.lquan.web.util.FormUtil;

@Controller
@RequestMapping(value="connnect")
public class ConnectController {
	Log log = LogFactory.getLog(ConnectController.class);

	@Resource(name="connectService")
	private IConnectService connectService;
	
	
	/**
	 * 跳转到通信页面
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "toConnectPage")
	public String toConnectManage(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) {
		
		String companyid = FormUtil.getStringFiledValue(request, "companyx");
		String departMentCode = FormUtil.getStringFiledValue(request, "departmentx");
		String gender = FormUtil.getStringFiledValue(request, "genderx");
		String useName = FormUtil.getStringFiledValue(request, "useNamex");
		String telphone = FormUtil.getStringFiledValue(request, "telphonex");
		String path = "\\upload"+"\\"+"phonefile"+"\\"+"Img"+"\\";
		List<Map<String, Object>> list = this.connectService.getStaffList(companyid, departMentCode, gender, useName, telphone, path);
		request.setAttribute("list", list);
		request.setAttribute("companyid", companyid);
		request.setAttribute("departMentCode", departMentCode);
		request.setAttribute("gender", gender);
		request.setAttribute("useName", useName);
		request.setAttribute("telphone", telphone);
		return "connection/connection";
	}

}

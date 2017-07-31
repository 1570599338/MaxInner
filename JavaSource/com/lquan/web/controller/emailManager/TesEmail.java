package com.lquan.web.controller.emailManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lquan.business.email.EmailSender;

@Controller
@RequestMapping(value="senderMail")
public class TesEmail {
	
	@Resource(name = "emailSender")
	public EmailSender emailSender;
	
	@RequestMapping(value="/test")
	public void toTestMail(HttpServletRequest request){
		String to ="quan.liu@maxinsight.cn";
		emailSender.send(to,null,null, "Forgot Password", "Hello,\r\n\r\n Your new password for .\r\n\r\nThank you!\r\n\r\n 美好的一天从现在开始！)");
		
	}

}

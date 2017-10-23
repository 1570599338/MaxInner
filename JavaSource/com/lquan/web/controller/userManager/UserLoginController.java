package com.lquan.web.controller.userManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;





import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import snt.common.web.util.WebUtils;

import com.lquan.ad.base.ldap.LDAPDAO;
import com.lquan.business.login.ILoginService;
import com.lquan.common.Constants;
import com.lquan.entity.User;
import com.lquan.util.Utils;

/**
 * 登陆操作
 * @author lquan
 * @version 1.0
 */
@Controller
@RequestMapping(value="user")
public class UserLoginController {
	Log log = LogFactory.getLog(UserLoginController.class);
	
	@Resource(name = "loginService")
	private ILoginService loginService;
	
	@Resource(name="lDAPDAO")
	private LDAPDAO lDAPDAO;
	
	/**
	 * 转向登陆界面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/toLogin")
	public String toLogin(HttpServletRequest request){
		//此处可以记录访问日志
		if (request.getSession().getAttribute(Constants.SESSION_USER) != null)
			request.getSession().removeAttribute(Constants.SESSION_USER);
		
		return "main/login";
	}
	
	
	/**
	 * 转向主界面
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/toMain")
	public ModelAndView toMain(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception{
		response.setContentType("text/xml; charset=UTF-8");
		String loginName = request.getParameter("uname");
		String password = request.getParameter("upass");
		Boolean loginFlage =false;
		User user =null;
		try {
			user = this.lDAPDAO.getDirContext(loginName, password);
			// 获取年假信息
			user = this.loginService.getUser(user);
			request.getSession().setAttribute(Constants.SESSION_USER,user);
			request.setAttribute("user", user);
			loginFlage =true;
		} catch (Exception e) {
			loginFlage =false;
			e.printStackTrace();
			log.error(loginName+"-登录失败-", e);
		}
		
		if(loginFlage){
			
			redirectAttributes.addFlashAttribute("message", "欢迎<font color=blue>"+loginName+"</font>登陆内部信息网！");
			return new ModelAndView("redirect:/user/main");
		}else{
			 user = loginService.selectUser(loginName);
			// 记录登陆时间、ip、mac地址
			String ip = Utils.getIpAddr(request);
			System.out.println("账户："+loginName+ " 密码："+password + "  IP:"+ip);
			
			if(user != null && null != user.getLog_name()&& !"".equals(user.getLog_name())){
				password = Utils.encryptPassword(password);
				if(password.equals(user.getPassword())){
					request.getSession().setAttribute(Constants.SESSION_USER,user);
					redirectAttributes.addFlashAttribute("message", "欢迎<font color=blue>"+user.getUser_name()+"</font>登陆内部信息网！");
					return new ModelAndView("redirect:/user/main");
				}else{
					redirectAttributes.addFlashAttribute("message", "对不起,您的密码有误！");
					return new ModelAndView("redirect:/user/toLogin");
				}
				
				
			}else{
				redirectAttributes.addFlashAttribute("message", "对不起,无此账户！");
			}
		}
		
		String ip = Utils.getIpAddr(request);
		System.out.println("*****"+ip+"*******");
		/*User user = loginService.selectUser(loginName);
		// 记录登陆时间、ip、mac地址
		String ip = Utils.getIpAddr(request);
		System.out.println("账户："+loginName+ " 密码："+password + "  IP:"+ip);
		
		if(user != null && null != user.getLog_name()&& !"".equals(user.getLog_name())){
			password = Utils.encryptPassword(password);
			if(password.equals(user.getPassword())){
				request.getSession().setAttribute(Constants.SESSION_USER,user);
				redirectAttributes.addFlashAttribute("message", "欢迎<font color=blue>"+user.getUser_name()+"</font>登陆内部信息网！");
				return new ModelAndView("redirect:/user/main");
			}else{
				redirectAttributes.addFlashAttribute("message", "对不起,您的密码有误！");
				return new ModelAndView("redirect:/user/toLogin");
			}
			
			
		}else{
			redirectAttributes.addFlashAttribute("message", "对不起,无此账户！");
		}*/
		return new ModelAndView("redirect:/user/toLogin");
	}
	
	/**
	 * 转向表格测试界面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/main")
	public String toDatagrid(HttpServletRequest request){
		String BJcompanyIP=WebUtils.getModuleProperty("BJcompanyIP1");
		String SHcompanyIP=WebUtils.getModuleProperty("SHcompanyIP1");
		String GZcompanyIP=WebUtils.getModuleProperty("GZcompanyIP1");
		
		String ip = Utils.getIpAddr(request);
		String[] ipPart = ip.split("\\.");
		String type ="";
		if(ipPart[2].equals(GZcompanyIP))// 20广州
			type="GZ";
		else if(ipPart[2].equals(SHcompanyIP)) //上海
			type="SH";
		else
			type="BJ";
		request.getSession().setAttribute(Constants.SESSION_TYPER,type);
		
		return "main/main";
	}
}

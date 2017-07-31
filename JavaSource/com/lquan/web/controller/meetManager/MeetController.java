package com.lquan.web.controller.meetManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import snt.common.dao.base.PaginationSupport;
import snt.common.web.util.WebUtils;

import com.lquan.business.meetManager.IMeetServer;
import com.lquan.business.userManager.IUserService;
import com.lquan.parseExcel.BookMeetExcelWB;
import com.lquan.util.MyFileUtil;
import com.lquan.web.util.FormUtil;

/**
 * 会议室管理系统
 * @author liuquan
 *
 */
@Controller  
@RequestMapping(value="/meet")
public class MeetController {
	Log log = LogFactory.getLog(MeetController.class);
	
	//
	@Resource(name="meetServer")
	private IMeetServer meetServer;

	
	@RequestMapping(value="topage")
	public String toPage(HttpServletRequest request){
		
		return "backmeet/managerMeet";
	}
	
	/**
	 * 前台页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="toHomepage")
	public String toHomepage(HttpServletRequest request){
		
		return "home/meet_base";
	}	
	
	/**
	 * 添加公告信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="bookMeet")
	public ModelAndView addAd(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		response.setContentType("text/json; charset=UTF-8"); // 注意设置为json
		response.setCharacterEncoding("UTF-8");
		String user = "admin";
		// 标题
		String meet = FormUtil.getStringFiledValue(request, "meet");
		// 公告标题
		String bookDate = FormUtil.getStringFiledValue(request, "bookDate");
		String startTime = FormUtil.getStringFiledValue(request, "startTime");
		String endTime = FormUtil.getStringFiledValue(request, "endTime");
		String[] bookassist = request.getParameterValues("bookassist"); 
		//String bookassist = FormUtil.getStringFiledValue(request, "bookassist");
		String booker = FormUtil.getStringFiledValue(request, "booker");
		String remark = FormUtil.getStringFiledValue(request, "remark");
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("1", "0");
		map.put("2", "0");
		map.put("3", "0");
		if(bookassist!=null){
			for(int j =0; j<bookassist.length;j++){
				map.put(bookassist[j], bookassist[j]);
			}
		}
		
		String bookassistx = map.get("1")+","+ map.get("2")+","+ map.get("3");
		
		
 	
		Map<String, Object> cont = new HashMap<String, Object>();	
		cont.put("meetId", meet);
		cont.put("bookDate", bookDate);
		cont.put("startTime", startTime);
		cont.put("endTime", endTime);
		cont.put("assist", bookassistx);
		cont.put("booker", booker);
		cont.put("remark", remark);
		
		Boolean p;
		try {
			p = this.meetServer.bookMeet(user, cont);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "预定成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "预定失败.");
			}
		} catch (Exception e) {
			log.error("预定会议室系统出错", e);
		}
		return new ModelAndView("redirect:/meet/topage");
	}
	
	
	/**
	 * 添加公告信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="editbookMeet")
	public ModelAndView editbookMeet(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		String user = "admin";
		String bookMeetId = FormUtil.getStringFiledValue(request, "bookMeetId");
		// 标题
		String meet = FormUtil.getStringFiledValue(request, "meet");
		// 公告标题
		String bookDate = FormUtil.getStringFiledValue(request, "bookDate");
		String startTime = FormUtil.getStringFiledValue(request, "startTime");
		String endTime = FormUtil.getStringFiledValue(request, "endTime");
		String[] bookassist = request.getParameterValues("bookassist"); 
		//String bookassist = FormUtil.getStringFiledValue(request, "bookassist");
		String booker = FormUtil.getStringFiledValue(request, "booker");
		String remark = FormUtil.getStringFiledValue(request, "remark");
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("1", "0");
		map.put("2", "0");
		map.put("3", "0");
		if(bookassist!=null){
			for(int j =0; j<bookassist.length;j++){
				map.put(bookassist[j], bookassist[j]);
			}
		}
		
		String bookassistx = map.get("1")+","+ map.get("2")+","+ map.get("3");
 	
		Map<String, Object> cont = new HashMap<String, Object>();
		cont.put("bookMeetId", bookMeetId);
		cont.put("meetId", meet);
		cont.put("bookDate", bookDate);
		cont.put("startTime", startTime);
		cont.put("endTime", endTime);
		cont.put("assist", bookassistx);
		cont.put("booker", booker);
		cont.put("remark", remark);
		
		Boolean p;
		try {
			p = this.meetServer.editBookMeet(user, cont);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "修改成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "修改失败.");
			}
		} catch (Exception e) {
			log.error("预定会议室系统出错", e);
		}
		return new ModelAndView("redirect:/meet/topage");
	}
	
	
	/**
	 * 显示
	 * @param request
	 * @param response
	 * @param redirect
	 * @throws Exception
	 */
	@RequestMapping(value="/getMeetInfo")
	public void seePhoto(HttpServletRequest request,HttpServletResponse response,RedirectAttributes  redirect) throws Exception{
		response.setContentType("text/json; charset=UTF-8"); // 注意设置为json
		response.setCharacterEncoding("UTF-8");// 传送中文时防止乱码
		String dateTime = FormUtil.getStringFiledValue(request, "dateTimex");
		//String companyId = FormUtil.getStringFiledValue(request, "companyId");
		if(dateTime==null ||"".equals(dateTime.trim())){
			SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			dateTime = dateFormater.format(date);
		}
		Map<String, Object> result  = this.meetServer.getbookMeet(null, null, dateTime,null);
		result.put("dateTime", dateTime);
		
		//json形式返回以树形表格展示
		JSONArray json=JSONArray.fromObject(result);
		PrintWriter out = response.getWriter();	
		//拼接的json数据多出一对“【】”，所以先去掉
		String resultStr = json.toString().substring(1,json.toString().length()-1);
		out.write(resultStr.toString());			
		out.flush();			
		out.close();
	}

	
	/**
	 * 显示
	 * @param request
	 * @param response
	 * @param redirect
	 * @throws Exception
	 */
	@RequestMapping(value="/checkMeet")
	public void checkMeet(HttpServletRequest request,HttpServletResponse response,RedirectAttributes  redirect) throws Exception{
		response.setContentType("text/json; charset=UTF-8"); // 注意设置为json
		response.setCharacterEncoding("UTF-8");// 传送中文时防止乱码
		String meet = FormUtil.getStringFiledValue(request, "meet");
		// 公告标题
		String bookDate = FormUtil.getStringFiledValue(request, "bookDate");
		String startTime = FormUtil.getStringFiledValue(request, "startTime");
		String endTime = FormUtil.getStringFiledValue(request, "endTime");
		
		List<Map<String, Object>> list = this.meetServer.checkMeet(meet, bookDate, startTime, endTime);
		Map<String, Object> map = new HashMap<String, Object>();
		if(list!=null&&list.size()>0)
			map.put("mess", list.size());
		else
			map.put("mess","0");
		//json形式返回以树形表格展示
		JSONArray json=JSONArray.fromObject(map);
		PrintWriter out = response.getWriter();	
		//拼接的json数据多出一对“【】”，所以先去掉
		String resultStr = json.toString().substring(1,json.toString().length()-1);
		out.write(resultStr);			
		out.flush();			
		out.close();
	}
	
	
	/**
	 * 获取会议室取消预订
	 * @param request
	 * @param response
	 * @param redirect
	 * @throws Exception
	 */
	@RequestMapping(value="/getCanelMeetlist")
	public void getCanelMeetlist(HttpServletRequest request,HttpServletResponse response,RedirectAttributes  redirect) throws Exception{
		response.setContentType("text/json; charset=UTF-8"); // 注意设置为json
		response.setCharacterEncoding("UTF-8");// 传送中文时防止乱码
		try {
			String bookDate = FormUtil.getStringFiledValue(request, "deletesearchData");
			String meetid = FormUtil.getStringFiledValue(request, "meetid");
			// 当前页
			String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
			// 每页显示的条数
			String rows = request.getParameter("rows") == null ? "10" : request.getParameter("rows").toString();
			//排序字段
			String sort = request.getParameter("sort") == null ? "bm.bookdate" : request.getParameter("sort").toString();
			//排序顺序
			String order = request.getParameter("order") == null ? "asc" : request.getParameter("order").toString();
			
			PaginationSupport ps = this.meetServer.getCanelMeet(page, rows,sort,order,meetid,bookDate);
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
		} catch (IOException e) {
			log.error("最新公告栏的页面数据出错了！",e);
		}
	}
	
	
	/**
	 * 取消会议室预定
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="deleteMeet")
	public ModelAndView deleteMeet(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		String user = "admin";
		// 标题
		String pk_id = FormUtil.getStringFiledValue(request, "pk_id");
		
		Boolean p;
		try {
			p = this.meetServer.deltebookMeet(pk_id);
			if (p) {
				redirectAttributes.addFlashAttribute("message", "取消预订成功.");
			} else {
				redirectAttributes.addFlashAttribute("message", "取消预订失败.");
			}
		} catch (Exception e) {
			log.error("取消预定会议室系统出错", e);
		}
		return new ModelAndView("redirect:/meet/topage");
	}
	
	
	
	/**
	 * 显示
	 * @param request
	 * @param response
	 * @param redirect
	 * @throws Exception
	 */
	@RequestMapping(value="/getOnebookMeetInfo")
	public void getOnebookMeetInfo(HttpServletRequest request,HttpServletResponse response,RedirectAttributes  redirect) throws Exception{
		response.setContentType("text/json; charset=UTF-8"); // 注意设置为json
		response.setCharacterEncoding("UTF-8");// 传送中文时防止乱码
		String bookMeetid = FormUtil.getStringFiledValue(request, "meetid");

		Map<String, Object> result  = this.meetServer.getOnebookMeetInfo(bookMeetid);

		
		//json形式返回以树形表格展示
		JSONArray json=JSONArray.fromObject(result);
		PrintWriter out = response.getWriter();	
		//拼接的json数据多出一对“【】”，所以先去掉
		String resultStr = json.toString().substring(1,json.toString().length()-1);
		out.write(resultStr.toString());			
		out.flush();			
		out.close();
	}
	
	/**
	 * 显示
	 * @param request
	 * @param response
	 * @param redirect
	 * @throws Exception
	 */
	@RequestMapping(value="/checkMeetUpdate")
	public void checkMeetUpdate(HttpServletRequest request,HttpServletResponse response,RedirectAttributes  redirect) throws Exception{
		response.setContentType("text/json; charset=UTF-8"); // 注意设置为json
		response.setCharacterEncoding("UTF-8");// 传送中文时防止乱码
		// 预定的会议室的记录的主键
		String bookMeetId = FormUtil.getStringFiledValue(request, "bookMeetId");
		String meet = FormUtil.getStringFiledValue(request, "meet");
		// 公告标题
		String bookDate = FormUtil.getStringFiledValue(request, "bookDate");
		//long startTime = FormUtil.getLongFiledValue(request, "startTime");
		String startTime = FormUtil.getStringFiledValue(request, "startTime");
		//long endTime = FormUtil.getLongFiledValue(request, "endTime");
		String endTime = FormUtil.getStringFiledValue(request, "endTime");
		
		List<Map<String, Object>> list = this.meetServer.checkMeetUpdate(bookMeetId,meet, bookDate, startTime, endTime);
		Map<String, Object> map = new HashMap<String, Object>();
		if(list!=null&&list.size()>0)
			map.put("mess", list.size());
		else
			map.put("mess","0");
		//json形式返回以树形表格展示
		JSONArray json=JSONArray.fromObject(map);
		PrintWriter out = response.getWriter();	
		//拼接的json数据多出一对“【】”，所以先去掉
		String resultStr = json.toString().substring(1,json.toString().length()-1);
		out.write(resultStr);			
		out.flush();			
		out.close();
	}
	
	
	/**
	 * 查询出数据写入excel
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/downloadDataExcel")
	public String downloadDataExcel(HttpServletRequest request,HttpServletResponse response)throws Exception{
		// 会议室预定的日期
		String bookTime = FormUtil.getStringFiledValue(request, "downBookDate");
		// 会议室的ID
		String meet = FormUtil.getStringFiledValue(request, "downbookMeetId");
		
		// 开始时间
		String startTime = FormUtil.getStringFiledValue(request, "downstartTime");
		// 结束时间
		String endTime = FormUtil.getStringFiledValue(request, "downendTime");
		// 预定人
		String bookName = FormUtil.getStringFiledValue(request, "downuserName");
		// 会议室的辅助设备
		long bookassist = FormUtil.getLongFiledValue(request, "downbookassist");
		
		// 获取到处文件的模板
		String realPathDir = WebUtils.getModuleProperty("tempExcel.downBookmeet");	
		
		List<Map<String,Object>> listData = this.meetServer.downLoadBookMeetList(bookName, meet, bookassist, bookTime, startTime, endTime);
			if(listData!=null){
				if (realPathDir != null && !"".equals(realPathDir)) {
					String  type = realPathDir.substring(realPathDir.lastIndexOf("."),realPathDir.length());
					if(realPathDir.contains(".xls")){//EXCEL格式文件
						if(".xls".equals(type)){//03					
							//数据报告工作薄AnswerExcelWb
							BookMeetExcelWB reportData = new BookMeetExcelWB(new FileInputStream(realPathDir),0);
							//写入数据
							reportData.bookMeetExcelImport(listData);
							//反馈出模板给页面下载
							HSSFWorkbook workbook = reportData.getWookBook();
							OutputStream out = MyFileUtil.download(response,"downloadPuGeData.xls");                 
							workbook.write(out);
							out.flush();
							out.close();	
						}else if(".xlsx".equals(type)){ //07预留接口
						}
					}
				}	
			}else{
				
			}
			
	    return null;
	}
}
	

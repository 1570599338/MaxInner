package com.lquan.web.controller.holidayManager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 年假查询文件
 * @author liuquan
 */

@Controller
@RequestMapping(value="/holiday")
public class HolidayController {
	
	@RequestMapping(value="/topage")
	public String toHolidayPage(){
		
		return "holiday/holiday";
	}
	
	
}

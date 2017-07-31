package com.lquan.business.connectManager;

import java.util.List;
import java.util.Map;

public interface IConnectService {
	/**
	 * 获取用户信息
	 * @return
	 */
	public List<Map<String, Object>>  getStaffList(String company,String departMent,String gender,String userName,String telPhone,String path);
}

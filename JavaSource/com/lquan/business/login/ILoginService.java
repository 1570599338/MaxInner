package com.lquan.business.login;

import java.util.List;
import java.util.Map;

import com.lquan.entity.User;



/**
 * 用户登录service接口
 * @author liuquan
 * 
 */
public interface ILoginService {
	/**
	 * 通过登录名查询用户
	 * @param loginName
	 */
	public User selectUser(String userName) throws Exception;

	/**
	 * 记录登录成功的详细信息
	 * @param loginInfo
	 * @return
	 */
	public boolean saveLoginInfo(Map<String, Object> loginInfo)throws Exception;

/*	*//**
	 * 判断密码是否正确，true:正确，False：不正确
	 * @param oldPassword
	 * @param pk_id
	 * @return
	 *//*
	public String checkPassWord(String oldPassword, long pk_id)throws Exception;

*/

	
	/**
	 * @Description: 修改密码，更新数据库中当前用户的密码
	 * @param pk_id
	 * @param npwd
	 * @return
	 */
	public boolean changePwd(long pk_id,String npwd);

}

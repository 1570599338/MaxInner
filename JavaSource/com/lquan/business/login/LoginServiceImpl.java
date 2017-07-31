package com.lquan.business.login;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lquan.entity.User;
import com.lquan.util.DateTimeUtil;

import snt.common.dao.base.CommonDAO;

/**
 * 用户登录接口实现类
 * @author liuquan
 */
@Service(value = "loginService")
public class LoginServiceImpl implements ILoginService {

	@Autowired
	@Qualifier("commonDAO")
	private CommonDAO commonDao;

	/**
	 * 根据登录名查询用户
	 */
	@Override
	public User selectUser(String loginName) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT pk_id,log_name,user_name,gender,tel,mobliephone,email,stat,password,role FROM t_use WHERE log_name='").append(loginName).append("'");
	
		List<User> list = commonDao.queryForPojoList(sql.toString(),new Object[] {}, User.class);
		
		if (list == null || list.isEmpty() || list.size() <= 0) {
			// 用户不存在的情况
			return null;
		} else {
			// 返回查询出的用户对象
			User user = list.get(0);
			return user;
		}

	}

	/**
	 * 记录登录成功的详细信息
	 * @param loginInfo
	 * @return
	 */
	@Override
	public boolean saveLoginInfo(Map<String, Object> loginInfo) {
		StringBuffer sb = new StringBuffer();
		sb.append(" INSERT INTO t_login_log(login_name,ip_address,net_mac,login_time) VALUES(?,?,?,?)");
		List<Object> paramList = new ArrayList<Object>();
		paramList.add((String) loginInfo.get("log_name"));
		paramList.add((String) loginInfo.get("ip"));
		paramList.add((String) loginInfo.get("net_mac"));
		paramList.add(DateTimeUtil.getDateW3CFormat(new Date()));

		int num = commonDao.update(sb.toString(), paramList.toArray());
		
		return num > 0 ? true : false;
	}

/*	*//**
	 * 判断密码是否正确，true:正确，False：不正确
	 *//*
	@Override
	public String checkPassWord(String oldPassword, long pk_id)throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT password FROM t_user WHERE pk_id = ").append(pk_id);

		List<User> list = commonDao.queryForPojoList(sb.toString(),new Object[] {}, User.class);
		
		if (list == null || list.isEmpty() || list.size() <= 0) {
			// 用户不存在的情况
			return "不存在该用户...";
		} else {
			// 返回查询出的用户对象
			String password = list.get(0).getPassword();
			if (!Utils.encryptPassword(oldPassword).equals(password)) {
				return "原密码输入不正确...";
			}
			return "";
		}
	}

	*//**
	 * 修改密码
	 *//*
	@Override
	public int uploadPassword(String newPassword, long pk_id) throws Exception {
		
		return commonDao.update("UPDATE t_user SET password = ? WHERE  pk_id = ? ",new Object[] { Utils.encryptPassword(newPassword), pk_id });

	}
	*/

	
	/**
	 * @Description: 修改密码，更新数据库中当前用户的密码
	 * @param pk_id
	 * @param npwd
	 * @return
	 */
	public boolean changePwd(long pk_id,String npwd) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE t_user SET password=?  WHERE pk_id = ?");
		int count = commonDao.update(sql.toString(),new Object[]{npwd,pk_id});
		return count > 0 ? true:false;
		
	}
	
}

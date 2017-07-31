package com.lquan.business.connectManager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import snt.common.dao.base.CommonDAO;

@Service(value="connectService")
public class ConnectServiceImpl implements IConnectService {
	
	//获取数据库的链接
	@Autowired
	@Qualifier("commonDAO")
	private CommonDAO commonDao;	
	
	/**
	 * 获取公司的
	 * @return
	 */
	@Override
	public List<Map<String, Object>>  getStaffList(String companyid,String departMentCode,String gender,String userName,String telPhone,String path){
		StringBuffer sql = new StringBuffer();
		sql.append(" select s.pk_id,s.useName, case s.gender when '1' then '男' else  '女' end gender ,s.telphone,s.celphone,s.email,s.departmentCode,case  when s.headImg is NULL then '\\upload\\demo.jpg' else  ");
		sql.append("'");
		sql.append(path);
		sql.append("' + s.headImg end as path  ");
		sql.append("from  staffInfo s ");
		sql.append(" join  department  d on s.departmentCode=d.Code ");
		sql.append(" join  company c on c.pk_id=d.companyId  where 1=1 and s.stat=1 ");
		// 公司
		if(companyid!=null && !"".equals(companyid)){
			sql.append(" AND c.pk_id=").append(companyid);
		}
		// 部门
		if(departMentCode!=null && !"".equals(departMentCode)){
			sql.append(" AND d.Code='").append(departMentCode).append("'");
		}
		// 性别
		if(gender!=null && !"".equals(gender)){
			sql.append(" AND s.gender=").append(gender);
		}
		// 员工名称
		if(userName!=null && !"".equals(userName)){
			sql.append(" AND s.useName like '%").append(userName).append("%'");
		}
		// 分机号
		if(telPhone!=null && !"".equals(telPhone)){
			sql.append(" AND s.telphone = '").append(telPhone).append("'");
		}
		
		List<Map<String, Object>> list = commonDao.queryForMapList(sql.toString());
		return list;
	}

}

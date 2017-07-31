package com.lquan.business.userManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import snt.common.dao.base.CommonDAO;

@Service(value="userService")
public class UserServiceImpl implements IUserService {
	
	@Autowired
    @Qualifier("commonDAO")
	private CommonDAO commonDao;

	@Override
	public void sayHello() {
		 int a = this.commonDao.getMaxRows();
		 System.out.println("************"+a);
	}
	
}

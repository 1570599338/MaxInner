package com.lquan.util;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;

import com.lquan.entity.User;

/**
 * 此功能主要是针对Ad域来对用户信息进行封装
 * 这个AD域的问题整了一个星期多了，第一次接触这个东东
 * 不过还是比较烦的，解析的东西特别多，需要一点点的去解析
 * @author liuquan
 *
 */
public class LDAPUtil {
		
	
	public static String getUser(SearchResult sr,String key){
		Attributes att = sr.getAttributes();
		// 此时获得值形式：mail: quan.liu@maxinsight.cn
        String valueTemp = att.get(key).toString();
        String value = valueTemp.split(":")[1];
		return value;
	}

}

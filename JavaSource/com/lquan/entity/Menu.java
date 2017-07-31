package com.lquan.entity;

import java.io.Serializable;

/** 
 * @ClassName: Funcregister 
 * @Description: 系统菜单节点实体类 
 * @author huaiqianci 
 * @date 2014-7-9 下午15:10  
 */
public class Menu {
	
	private static final long serialVersionUID = -4432738602661499644L;
	//菜单主键
	private Long pk_id;
	//菜单编号
	private String menuNum;
	//父菜单编号
	private String parentMenuNum;
	//菜单名称
	private String menuName;
	//菜单图标
	private String menuImge;
	//菜单url
	private String menuURL;
	//菜单功能描述
	private String describe;
	
	public Menu(){
		//
	}
	
	public Menu(long pk_id){
		this.pk_id = pk_id;
	}
	
	public Menu(long pk_id,String menuNum,String parentMenuNum,String menuName,String menuImge,String menuURL,String describe){
		this.pk_id = pk_id;
		this.menuNum = menuNum;
		this.parentMenuNum  = parentMenuNum;
		this.menuName = menuName;
		this.menuImge  = menuImge;
		this.menuURL = menuURL;
		this.describe = describe;
	} 
	
	public Long getPk_id() {
		return pk_id;
	}
	public void setPk_id(Long pk_id) {
		this.pk_id = pk_id;
	}
	public String getMenuNum() {
		return menuNum;
	}
	public void setMenuNum(String menuNum) {
		this.menuNum = menuNum;
	}
	public String getParentMenuNum() {
		return parentMenuNum;
	}
	public void setParentMenuNum(String parentMenuNum) {
		this.parentMenuNum = parentMenuNum;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuImge() {
		return menuImge;
	}
	public void setMenuImge(String menuImge) {
		this.menuImge = menuImge;
	}
	public String getMenuURL() {
		return menuURL;
	}
	public void setMenuURL(String menuURL) {
		this.menuURL = menuURL;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}

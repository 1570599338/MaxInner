package com.lquan.ad.base.ldap;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import com.lquan.entity.User;
import com.lquan.util.LDAPUtil;


/**
 * 验证Ad域的登陆Dao
 * @author liuquan
 *
 */
public class LDAPDAO {
	/*String userName = "liuquan";//AD域认证，用户的登录UserName
    String password = "M@x201611";//AD域认证，用户的登录PassWord
    
    String host = "192.168.1.230";//AD域IP，必须填写正确
    String domain = "@mi.local";//域名后缀，例.@noker.cn.com
    String port = "389"; //端口，一般默认389
    String url = new String("ldap://" + host + ":" + port);//固定写法
    String user = userName.indexOf(domain)>0?userName:userName + domain;//网上有别的方法，但是在我这儿都不好使，建议这么使用
*/	
	private DirContext ctx = null;
	// AD域的IP
	private static String host;
	// 域名后缀
	private String domain;
	// 端口
	private static String port="389"; //端口，一般默认389;
	private static Hashtable<Object, String> env = new Hashtable();//实例化一个Env
	static {
		env.put(Context.SECURITY_AUTHENTICATION, "simple");//LDAP访问安全级别(none,simple,strong),一种模式，这么写就行
		env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");// LDAP工厂类
	}
	
	/**
	 * 此处是获取连接AD域的 DirContext，注意调用此方法后不再使用后一定要关闭改连接
	 * @param userName 用户名
	 * @param password 密码
	 * @return
	 * @throws Exception 
	 */
	public User getDirContext(String userName,String password) throws Exception{
		String url = new String("ldap://" + host + ":" + port);
		String user = userName.indexOf(domain) > 0 ? userName : userName + domain;
        env.put(Context.SECURITY_PRINCIPAL, user); //用户名
        env.put(Context.SECURITY_CREDENTIALS, password);//密码
        env.put(Context.PROVIDER_URL, url);//Url
        User userBean = new User();
      /*  try {*/
        ctx = new InitialDirContext(env);// 初始化上下文
        System.out.println("身份验证成功!");
        SearchControls searchCtls = new SearchControls();  
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        // 设置过滤
        String searchFilter = "(&(objectClass=user)(name="+userName+"))";
        String searchBase = "DC=MI,DC=local";
        String[] returnedAtts = { "description","name","mail","employeeID","userpassword"};
        searchCtls.setReturningAttributes(returnedAtts);  
        NamingEnumeration<SearchResult> answer = ctx.search(searchBase, searchFilter,searchCtls); 
        while (answer.hasMoreElements()) {  
            SearchResult sr = (SearchResult) answer.next();
            // 获取属性
            String mail = LDAPUtil.getUser(sr, "mail");// 电子邮件
            String name = LDAPUtil.getUser(sr, "description"); // 姓名
            String loginName = LDAPUtil.getUser(sr, "name");// 登录名称
            userBean.setEmail(mail);
            userBean.setUser_name(name);
            userBean.setLog_name(loginName);
        }
        
        
        ctx.close();
        /*} catch (AuthenticationException e) {
            System.out.println("身份验证失败!");
            e.printStackTrace();
        } catch (javax.naming.CommunicationException e) {
        	 System.out.println("AD域连接失败!");
             e.printStackTrace();
        }  catch (Exception e) {
             //System.out.println("身份验证未知异常!");
             e.printStackTrace();
         }*/
		return userBean;
	}

	
	/**
	 * 此方法时关闭对应AD域的连接
	 */
	public void close(){
		try {
			ctx.close();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
            if(null!=ctx){
                try {
                    ctx.close();
                    ctx=null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
       }
	}
	
	

	public DirContext getCtx() {
		return ctx;
	}


	public void setCtx(DirContext ctx) {
		this.ctx = ctx;
	}


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public String getDomain() {
		return domain;
	}


	public void setDomain(String domain) {
		this.domain = domain;
	}


	public String getPort() {
		return port;
	}


	public void setPort(String port) {
		this.port = port;
	}

}

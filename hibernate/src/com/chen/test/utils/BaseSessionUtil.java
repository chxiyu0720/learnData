package com.chen.test.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * session工厂
 * 
 * @author
 *			chenxiaoyu
 * @QQ
 *			1524904743
 * @email
 *			1524904743@qq.com
 * @date
 *			2016年8月29日, 下午2:38:45
 */
public class BaseSessionUtil {
	
	private static SessionFactory factory;
	
	static {
		Configuration cfg = new Configuration().configure();
		factory = cfg.buildSessionFactory();
	}
	
	/**
	 * 获取session
	 * @return
	 */
	public static Session getSession() {
		return factory.openSession();
	}
	
	/**
	 * 关闭session
	 */
	public static void closeSession(Session session) {
		if (session != null) {
			if (session.isOpen()) {
				session.close();
			}
		}
	}
	
	/**
	 * 返回session工厂
	 */
	public static SessionFactory getSessionFactory() {
		return factory;
	}
}

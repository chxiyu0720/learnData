package com.chen.test.controller;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;

import com.chen.test.bean.UserInfo;
import com.chen.test.utils.BaseSessionUtil;

/**
 * hibernate 二级缓存测试
 * 依赖jar包：
 * @author
 *			chenxiaoyu
 * @QQ
 *			1524904743
 * @email
 *			1524904743@qq.com
 * @date
 *			2016年9月1日, 下午6:01:12
 */
public class CacheSecondTest {
	/**
	 * 开启二级缓存后，两个不同的session。查询同一个id的userinfo对象，只会在第一次查询时，
	 * 查询数据库，然后将查询到的对象，放入二级缓存中，之后再次查询时，会直接在二级缓存中获取
	 * 以下demo的总结：
	 * 	1、二级缓存的数据，在所有的session中可以共享，其生命周期可以配置。
	 * 	2、session的get和load查询方法，都可以使用二级缓存。
	 * 	3、当以get方法获取一个id=15的对象后，若是再次用load方法查询相同的id的对象，这时也不会
	 * 	 查询数据库，而是从二级缓存中读取。由此可见，二级缓存数据的存取，与操作方法无关
	 */
	@Test
	public void secondCache() {
		//get方法测试
		Session session = BaseSessionUtil.getSession();
		UserInfo info = session.get(UserInfo.class, 19);
		System.out.println(info.getUsername());
		BaseSessionUtil.closeSession(session);
		
		Session session2 = BaseSessionUtil.getSession();
		UserInfo info2 = session2.get(UserInfo.class, 19);
		System.out.println(info2.getUsername());
		BaseSessionUtil.closeSession(session2);
		
		//load方法测试
		Session session3 = BaseSessionUtil.getSession();
		UserInfo info3 = session3.load(UserInfo.class, 19);
		System.out.println(info3.getUsername());
		BaseSessionUtil.closeSession(session3);
		
		Session session4 = BaseSessionUtil.getSession();
		UserInfo info4 = session4.load(UserInfo.class, 19);
		System.out.println(info4.getUsername());
		BaseSessionUtil.closeSession(session4);
	}
	
	/**
	 * 2个不同的概念
	 * 查询缓存：
	 * 	配置：<property name="hibernate.cache.use_query_cache">true</property>
	 *  作用：用于支持query类型的查询
	 * 二级缓存：
	 *  配置：<property name="hibernate.cache.use_second_level_cache">true</property>
	 *  作用：存放公共缓存数据的空间
	 * 这两者结合使用才有意义，只有开启一种是没有意义的
	 * 
	 * demo总结：
	 * 	1、query的非主键查询，不仅需要开启查询缓存和二级缓存，而且还需要在查询语句后调用setCacheable(true)，才能使的二级缓存生效。
	 *  2、query的list方法，与get和load调用二级缓存的原理相似。
	 *  3、query的iterate方法，也支持二级缓存和查询缓存，但是在实际的使用中，它任会发出sql查询语句，但是不会查询数据库
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void selectForList() {
		
		// list查询集合
		Session session = BaseSessionUtil.getSession();
		Query query = session.createQuery("from UserInfo where username = ?").setString(0, "小三").setCacheable(true);
		List<UserInfo> list = query.list();
		for (UserInfo userInfo : list) {
			System.out.println(userInfo.getPassword());
		}
		BaseSessionUtil.closeSession(session);
		
		Session session2 = BaseSessionUtil.getSession();
		Query query2 = session2.createQuery("from UserInfo where username = ?").setString(0, "小三").setCacheable(true);
		List<UserInfo> list2 = query2.list();
		for (UserInfo userInfo : list2) {
			System.out.println(userInfo.getPassword());
		}
		BaseSessionUtil.closeSession(session2);
		
		// iterate查询集合
		Session session3 = BaseSessionUtil.getSession();
		Query query3 = session3.createQuery("from UserInfo where username = ?").setString(0, "小三").setCacheable(true);
		Iterator list3 = query3.iterate();
		while (list3.hasNext()) {
			UserInfo object = (UserInfo) list3.next();
			System.out.println(object.getPassword());
		}
		BaseSessionUtil.closeSession(session3);
		
		Session session4 = BaseSessionUtil.getSession();
		Query query4 = session4.createQuery("from UserInfo where username = ?").setString(0, "小三").setCacheable(true);
		Iterator list4 = query4.iterate();
		while (list4.hasNext()) {
			UserInfo object = (UserInfo) list4.next();
			System.out.println(object.getPassword());
		}
		BaseSessionUtil.closeSession(session4);
	}
	
	/**
	 * SessionFactory管理二级缓存
	 */
	@Test
	public void mageCache() {
		Session session = BaseSessionUtil.getSession();
		Transaction tran = session.beginTransaction();
		try {
			UserInfo info = session.get(UserInfo.class, 16);
			System.out.println(info.getUsername());
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tran.rollback();
		} finally {
			BaseSessionUtil.closeSession(session);
		}
		SessionFactory factory = BaseSessionUtil.getSessionFactory();
		System.out.println(factory.isClosed());
		Session session2 = BaseSessionUtil.getSession();
		Transaction tran2 = session2.beginTransaction();
		try {
			UserInfo info2 = session2.get(UserInfo.class, 16);
			System.out.println(info2.getUsername());
			tran2.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tran2.rollback();
		} finally {
			BaseSessionUtil.closeSession(session2);
		}
	}
}

package com.chen.test.controller;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import com.chen.test.bean.UserInfo;
import com.chen.test.utils.BaseSessionUtil;

/**
 * hibernater一级缓存测试
 * 
 * @author
 *			chenxiaoyu
 * @QQ
 *			1524904743
 * @email
 *			1524904743@qq.com
 * @date
 *			2016年8月31日, 下午2:35:22
 */
public class CacheFirstTest {
	
	/**
	 * 一级缓存，即session缓存，生命周期很短，和session一样长
	 * 	*当以get，load或iterate三种方法查询实体对象时，一级缓存会缓存查询结果，当第二次范围内的查询，就不会再查询数据库了，而是直接读缓存
	 * 但是若是用这三种方法查询具体的对象属性，则不会缓存
	 * 	*session之间是无法共享一级缓存的，当session关闭时，其对应的一级缓存也会销毁
	 */
	@Test
	public void testCache1() {
		Session session = BaseSessionUtil.getSession();
		int id = 16;
		UserInfo info = session.get(UserInfo.class, id);
		System.out.println(info.getUsername() + "=========");
		UserInfo Info2 = session.get(UserInfo.class, id);
		System.out.println(Info2.getUsername() + "----------");
		
		//迭代器查询
		Query query = session.createQuery("from UserInfo where id < ?").setInteger(0, 12);
		Iterator<?> iterator = query.iterate();
		while (iterator.hasNext()) {
			UserInfo object = (UserInfo) iterator.next();
			System.out.println(object.getUsername() + "******************");
		}
		System.out.println("=======================================");
		Query query1 = session.createQuery("from UserInfo where id < ?").setInteger(0, 10);
		Iterator<?> iterator1 = query1.iterate();
		while (iterator1.hasNext()) {
			UserInfo object = (UserInfo) iterator1.next();
			System.out.println(object.getUsername() + "******************");
		}
		
		//list查询
		List<UserInfo> list = query.list();
		for (UserInfo userInfo : list) {
			System.out.println(userInfo.getUsername() + "----------");
		}
		
		System.out.println("======================================");
		
		List<UserInfo> list2 = query.list();
		for (UserInfo userInfo : list2) {
			System.out.println(userInfo.getUsername() + "----------");
		}
	}
	
	/**
	 * iterate查询实体对象的属性，一级缓存不缓存
	 */
	@Test
	public void cacheTest() {
		Session session = BaseSessionUtil.getSession();
		Query query = session.createQuery("select username from UserInfo where id = 9");
		Iterator<?> iterable = query.iterate();
		while (iterable.hasNext()) {
			Object object = (Object) iterable.next();
			System.out.println(object.toString());
		}
		Iterator<?> iterable1 = query.iterate();
		while (iterable1.hasNext()) {
			Object object = (Object) iterable1.next();
			System.out.println(object.toString());
		}
//		UserInfo info = session.get
	}
}

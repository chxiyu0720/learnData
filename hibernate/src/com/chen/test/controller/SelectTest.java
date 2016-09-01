package com.chen.test.controller;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.chen.test.bean.UserInfo;
import com.chen.test.utils.BaseSessionUtil;

/**
 * 查询测试
 * 删除测试
 * 
 * @author
 *			chenxiaoyu
 * @QQ
 *			1524904743
 * @email
 *			1524904743@qq.com
 * @date
 *			2016年8月29日, 下午2:50:28
 */
public class SelectTest {
	
	/**
	 * 主键查询
	 */
	@Test
	public void selectId() {
		Session session = BaseSessionUtil.getSession();
		/**
		 * get方法查询：手动构造对象，若不存在返回null（推荐）
		 * 参数分别为目标对象以及所要查询数据的主键id值
		 */
		UserInfo info = session.get(UserInfo.class, 5);
		if (info != null) {
			System.out.println(info.getUsername() + "-----" + info.getPassword());
		}
		
		/**
		 * load方法查询：加载对象，若不存在会抛出空指针异常
		 * 参数分别为目标对象以及所要查询数据的主键id值
		 */
		UserInfo userInfo = session.load(UserInfo.class, 6);
		System.out.println(userInfo.getUsername() + "-----" + userInfo.getPassword());
		
		//关闭session
		BaseSessionUtil.closeSession(session);
	}
	
	/**
	 * 非主键查询测试
	 */
	@Test
	public void selectOther() {
		Session session = BaseSessionUtil.getSession();
		String name = "张三";
		
		// 注意这里的UserInfo是bean对象名称，而不是对应的表名称
		Query query = session.createQuery("from UserInfo where username = '"+name+"'");
		@SuppressWarnings("unchecked")
		List<UserInfo> list = query.list();
		for (UserInfo userInfo : list) {
			System.out.println(userInfo.getUsername() + "----" + userInfo.getCreate_time());
		}
	}
	
	/**
	 * 分页查询测试
	 */
	@Test
	public void selectPages() {
		Session session = BaseSessionUtil.getSession();
		Query query = session.createQuery("from UserInfo");
		query.setFirstResult(0);		//从数据表的第一项开始，下标为0
		query.setMaxResults(3);			//每页获取3个数据
		@SuppressWarnings("unchecked")
		List<UserInfo> infos = query.list();
		for (UserInfo userInfo : infos) {
			System.out.println(userInfo.getUsername() + "------" + userInfo.getCreate_time());
		}
	}
	
	/**
	 * 删除测试
	 */
	@Test
	public void deletedData() {
		Session session = BaseSessionUtil.getSession();
		//开启事务
		Transaction tran = session.beginTransaction();
		try {
			//第一种删除
			UserInfo info = session.get(UserInfo.class, 3);
			session.delete(info);
			
			//第二种删除
			UserInfo userInfo = new UserInfo();
			userInfo.setId(4);
			session.delete(userInfo);
			
			//提交事务
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
			//事务回滚
			tran.rollback();
		} finally {
			BaseSessionUtil.closeSession(session);
		}
	}
}

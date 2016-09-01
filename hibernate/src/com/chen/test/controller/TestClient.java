package com.chen.test.controller;

import java.util.Date;  

import org.hibernate.Session;  
import org.hibernate.SessionFactory;  
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;  
import org.junit.Test;

import com.chen.test.bean.UserInfo;
import com.chen.test.utils.BaseSessionUtil;
  
/**
 * 测试添加数据
 * 测试更新修改数据
 * 
 * @author
 *			chenxiaoyu
 * @QQ
 *			1524904743
 * @email
 *			1524904743@qq.com
 * @date
 *			2016年8月29日, 下午4:03:05
 */
public class TestClient {
	/**
	 * 传统源码模式添加
	 */
	@Test
    public void addData() {  
        //默认读取的是hibernate.cfg.xml 文件.  
        Configuration cfg = new Configuration().configure();  
        //建立SessionFactory.  
        SessionFactory factroy = cfg.buildSessionFactory();  
        //取得session.  
        Session session = null;  
        try{  
            //通过工厂取得session  
            session = factroy.openSession();  
            //开启事务.  
            session.beginTransaction();  
            //给对象赋值.  
            UserInfo user = new UserInfo();  
            user.setUsername("小婊砸");  
            user.setPassword("43423");  
            user.setCreate_time(new Date());  
            user.setAge(20);
            user.setDeleted(false);
            user.setSex("女");
              
            //保存user对象到数据库.  
            // 一个实体类对象对应一个数据库中的表.  
            session.save(user);  
            //先拿到前面事务的对象.再提交事务.  
            session.getTransaction().commit();  
        }catch( Exception e) {  
            e.printStackTrace();  
            //回滚事务.  
            session.getTransaction().rollback();  
        } finally { 
            if(session!=null){  
                if(session.isOpen()){  
                    //关闭session.  
                    //hibernate中已经将connection的的关闭封装了.  
                    //我们没有看到任何一条sql语句.  
                    session.close();  
                }  
            }  
        }  
    } 
	
	/**
	 * 采用封装的工具类BaseSessionUtil添加
	 */
	@Test
	public void addDataByUtil() {
		//获取session
		Session session = BaseSessionUtil.getSession();
		//开启事务
		Transaction tran = session.beginTransaction();
		try {
			//创建临时对象
			UserInfo info = new UserInfo();
			info.setUsername("小无");
			info.setAge(30);
			info.setPassword("fewg");
			info.setSex("女");
			info.setCreate_time(new Date());
			//数据持久化
			session.save(info);
			
			//提交事务，同步数据，数据脱管
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
			//事务回滚
			tran.rollback();
		} finally {
			//关闭session
			BaseSessionUtil.closeSession(session);
		}
	}
	
	/**
	 * 测试更新修改数据，先查询获取目标对象，在修改对应的值，最后在事务提交
	 */
	@Test
	public void updateUser() {
		//获取session
		Session session = BaseSessionUtil.getSession();
		//开启事务
		Transaction tran = session.beginTransaction();
		try {
			//get方法构造对象查询（推荐）
			int id = 70;
			UserInfo info = session.get(UserInfo.class, id);
			if (info != null) {
				info.setUsername("修改三");
				session.update(info);
			}
			
			//load方法加载对象查询
			int ids = 13;
			UserInfo userInfo = session.load(UserInfo.class, ids);
			userInfo.setUsername("修改四");
			session.update(userInfo);
			
			//事务提交
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tran.rollback();
		} finally {
			BaseSessionUtil.closeSession(session);
		}
	}
}  

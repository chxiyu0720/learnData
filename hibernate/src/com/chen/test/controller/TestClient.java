package com.chen.test.controller;

import java.util.Date;  

import javax.xml.registry.infomodel.User;

import org.hibernate.Session;  
import org.hibernate.SessionFactory;  
import org.hibernate.cfg.Configuration;  

import com.chen.test.bean.UserInfo;
  
/** 
 * 客户端向表中添加数据. 
 * @author summer 
 * 
 */  
public class TestClient {  
    public static void main(String[] args) {  
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
            user.setUsername("小三");  
            user.setPassword("123");  
            user.setCreate_time(new Date());  
            user.setAge(20);
            user.setDeleted(false);
            user.setSex("男");
              
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
}  

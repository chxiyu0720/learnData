package com.chen.test.controller;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.chen.test.utils.BaseSessionUtil;

public class TestMoreTables {
	public void addDatas() {
		Session session = BaseSessionUtil.getSession();
		Transaction tran = session.beginTransaction();
		try {
			
		} catch (Exception e) {
		}
	}
}

package com.chen.test.bean;

import java.util.Date;

public class QqGroup {
	private int id;
	private String qq_group;
	private String group_name;
	private boolean deleted;
	private Date create_time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQq_group() {
		return qq_group;
	}
	public void setQq_group(String qq_group) {
		this.qq_group = qq_group;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
}

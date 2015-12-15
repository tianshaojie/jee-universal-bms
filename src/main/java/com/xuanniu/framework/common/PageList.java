package com.xuanniu.framework.common;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class PageList<T> implements Serializable {

	private Page page;
	private List<T> list;
	private String msg;
	
	public PageList() {
	}
	public PageList(Page page) {
		this.page = page;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}

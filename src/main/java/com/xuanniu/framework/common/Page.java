package com.xuanniu.framework.common;

import java.io.Serializable;

/**
 * 数据分页处理辅助类，负责页码的计算
 * @author xwarrior
 *
 */
@SuppressWarnings("serial")
public class Page implements Serializable {
	public static int DEFAULT_PAGESIZE = 20;
	public static int DEFAULT_PAGEINDEX = 1;
	
	private int pageIndex = 0;
	private int pageSize = 0;
	private int totalCount = 0;
	private int pageCount = 0;
	private int start = 0;
	private int limit = 0;
	
	//排序字段名称
	private String orderField;
	//排序方向  asc,desc
	private String orderDirection;
	
	public Page(){}
	
	public Page(int totalCount){
		this.setPageIndex(DEFAULT_PAGEINDEX);
		this.setPageSize(DEFAULT_PAGESIZE);
		this.setTotalCount(totalCount);
		
		//初始化页码
		initPage();
	}
	
	public Page(int pageIndex,int pageSize,int totalCount){
		this.setPageIndex(pageIndex);
		this.setPageSize(pageSize);
		this.setTotalCount(totalCount);
		
		//初始化页码
		initPage();
	}
	
	public Page(int pageIndex,int pageSize,int totalCount,String orderField,String orderDirection){
		this.setPageIndex(pageIndex);
		this.setPageSize(pageSize);
		this.setTotalCount(totalCount);
		this.orderField = orderField;
		this.orderDirection = orderDirection;
		
		//初始化页码
		initPage();
	}
	
	public void initPage(int totalCount) {
		this.totalCount = totalCount;
		initPage();
	}

	private void initPage() {
		if(this.getTotalCount() <= 0)
			return;
		
		if(this.getPageSize() <= 0)
			return;
		
		if(this.getPageIndex() <= 0){
			this.setPageIndex(0);
		}
		
		//计算总页数
		this.setPageCount(this.getTotalCount() / this.getPageSize());
		this.setPageCount(this.getPageCount() + ((this.getTotalCount() % this.getPageSize()) > 0 ? 1:0));
		
		//如果要获取的页码超出，返回最后一页数据（在改变页面大小时会发生这种情况
		if(this.pageIndex > this.pageCount){
			this.setPageIndex(this.pageCount);
		}
		
		//计算偏移
		this.setStart(this.getPageIndex() > 1? this.getPageSize() * this.getPageIndex() - this.getPageSize() : 0);
		this.setLimit(this.getPageSize());
	}

	/**
	 * 获取页面总数
	 * @return
	 */
	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * 获取页面返回的数据开始位置
	 * @return
	 */
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * 获取查询返回的页面大小
	 * @return
	 */
	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * @return the orderField
	 */
	public String getOrderField() {
		return orderField;
	}

	/**
	 * @param orderField the orderField to set
	 */
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	/**
	 * @return the orderDirection
	 */
	public String getOrderDirection() {
		return orderDirection;
	}

	/**
	 * @param orderDirection the orderDirection to set
	 */
	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the pageIndex
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * @param pageIndex the pageIndex to set
	 */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex <= 0 ? 1: pageIndex;
	}
	
}

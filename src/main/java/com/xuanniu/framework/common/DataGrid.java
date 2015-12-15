package com.xuanniu.framework.common;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DataGrid<T> implements Serializable {

	private int total;
	private List<T> rows;
	private Object option;
	
	public DataGrid(PageList<T> pageList) {
		this.total = pageList.getPage().getTotalCount();
		this.rows = pageList.getList();
	}
	
	public DataGrid(PageList<T> pageList, Object option) {
		this.total = pageList.getPage().getTotalCount();
		this.rows = pageList.getList();
		this.option = option;
	}
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
	public Object getOption() {
		return option;
	}

	public void setOption(Object option) {
		this.option = option;
	}

	@Override
    public String toString() {
		String rowsstr = "null";
		if (rows != null)
			rowsstr = "" + rows.size();
		String optionstr = "null";
		if (optionstr != null)
			optionstr = option.toString();
        return "DataGrid{" +
                "total=" + total +
                ", rows=" + rowsstr +
                ", option=" + optionstr +
                "}";
    }
	
	
}

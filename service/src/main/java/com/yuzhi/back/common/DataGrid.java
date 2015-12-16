package com.yuzhi.back.common;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DataGrid<T> implements Serializable {

	private long total;
	private List<T> rows;
	private Object option;
	
    public DataGrid(PageInfo pageInfo) {
        this.total = pageInfo.getTotal();
        this.rows = pageInfo.getList();
    }

    public DataGrid(PageInfo pageInfo, Object option) {
        this.total = pageInfo.getTotal();
        this.rows = pageInfo.getList();
        this.option = option;
    }
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
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

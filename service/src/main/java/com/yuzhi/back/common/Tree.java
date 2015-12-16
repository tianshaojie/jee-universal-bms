package com.yuzhi.back.common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by tiansj on 15/6/25.
 */
public class Tree implements Serializable {

    private Long id;
    private Long pid;
    private String text;
    private String iconCls;
    private Map attributes;
    private List<Tree> children;
    private Long sort;
    private String url;
    private boolean checked;

    public Tree(){}

    public Tree(Long id, Long pid, String text) {
        this.id = id;
        this.pid = pid;
        this.text = text;
    }

    public Tree(Long id, Long pid, String text, String iconCls) {
        this.id = id;
        this.pid = pid;
        this.text = text;
        this.iconCls = iconCls;
    }

    public Tree(Long id, Long pid, String text, String iconCls, Long sort) {
        this.id = id;
        this.pid = pid;
        this.text = text;
        this.iconCls = iconCls;
        this.sort = sort;
    }

    public Tree(Long id, Long pid, String text, String iconCls, Long sort, String url) {
        this.id = id;
        this.pid = pid;
        this.text = text;
        this.iconCls = iconCls;
        this.sort = sort;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public Map getAttributes() {
        return attributes;
    }

    public void setAttributes(Map attributes) {
        this.attributes = attributes;
    }

    public List<Tree> getChildren() {
        return children;
    }

    public void setChildren(List<Tree> children) {
        this.children = children;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

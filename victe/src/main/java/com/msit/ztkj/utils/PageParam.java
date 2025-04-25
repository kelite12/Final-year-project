package com.msit.ztkj.utils;

/**
 * 2020/5/18
 *
 * @victe
 */
public class PageParam {
    private int pageNo;
    private int pageSize;
    private int start;//查询的起始位置

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}

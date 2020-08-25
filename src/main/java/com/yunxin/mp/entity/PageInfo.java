package com.yunxin.mp.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author: huangzuwang
 * @date: 2019-12-07 21:30
 * @description: 字段简化版分页返回实体
 */
public class PageInfo<T> implements Serializable {

    private static final long serialVersionUID = 8545996863226528798L;

    /**
     * 查询数据列表
     */
    private List<T> list = Collections.emptyList();
    /**
     * 总数，当 total 为 null 或者大于 0 分页插件不在查询总数
     */
    private long total = 0;
    /**
     * 每页显示条数，默认 10
     */
    private long pageSize = 10;
    /**
     * 当前页
     */
    private long pageNum = 1;

    /**
     * 是否有下一页
     */
    private boolean hasNextPage = false;

    public PageInfo() {

    }

    public PageInfo(IPage<T> iPage) {
        this.list = iPage.getRecords();
        this.pageNum = iPage.getCurrent();
        this.pageSize = iPage.getSize();
        this.total = iPage.getTotal();
        this.hasNextPage = iPage.getPages() > iPage.getCurrent();
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }

    public boolean getHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }
}

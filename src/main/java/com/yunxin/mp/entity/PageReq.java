package com.yunxin.mp.entity;

/**
 * @description: 分页入参
 * @author: huangzuwang
 * @date: 2019-12-06 17:17
 **/
public class PageReq {

	/**
     * 当前页
	 */
	private int pageNum = 1;

    /**
     * 每页数据条数
	 */
	private int pageSize = 10;

	
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}

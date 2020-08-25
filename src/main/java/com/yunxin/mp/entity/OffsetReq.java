package com.yunxin.mp.entity;

/**
 * @description: 分页入参
 * @author: huangzuwang
 * @date: 2019-12-07 20:17
 **/
public class OffsetReq {

	/**
     * 数据偏移量
	 */
	private int offset = 0;

    /**
     * 查询条数
	 */
	private int count = 10;


	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "OffsetReq{" +
				"offset=" + offset +
				", count=" + count +
				'}';
	}
}

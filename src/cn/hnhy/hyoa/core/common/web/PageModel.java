package cn.hnhy.hyoa.core.common.web;

import java.io.File;

import org.hibernate.validator.util.GetMethods;

/**
 * 分页实体
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月23日 下午5:24:40
 */
public class PageModel {
	/** 定义默认每页显示的记录的条数 */
	private static final int PAGE_SIZE = 20;
	/** 当前页码 */
	private int pageIndex;
	/** 每页显示的记录条数 */
	private int pageSize;
	/** 总记录条数 */
	private int recordCount;
	/** setter and getter method */
	public int getPageIndex() {
		/** 判断当前页码不能小于1 */
		pageIndex = pageIndex <= 1 ? 1 : pageIndex;
		/** 判断当前页码不能大于总页数 */
		int totalPage = ((this.recordCount - 1) / getPageSize()) + 1;
		return pageIndex > totalPage ? totalPage : pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize <= 0 ? PAGE_SIZE : pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	/** limit第一个问号的值 */
	public int getStartRow(){
		return (getPageIndex() - 1) * getPageSize();
	}
}
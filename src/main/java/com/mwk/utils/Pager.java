package com.mwk.utils;


import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * <b> 分页通用类 </b>
 *
 * @param <T>
 * @author
 */
public class Pager<T> implements Serializable {
	private static final long serialVersionUID = 4542617637761955078L;

	public Pager(int currentPage, int pageSize) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	public Pager(List<T> content, int currentPage, int pageSize) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.content = content;
		this.setRecordTotal(content.size());
	}

	public Pager() {
	}

	/**
	 * currentPage 当前页
	 */
	private int currentPage = 1;
	/**
	 * pageSize 每页大小
	 */
	private int pageSize = 10;
	/**
	 * pageTotal 总页数
	 */
	private int pageTotal;
	/**
	 * recordTotal 总条数
	 */
	private int recordTotal = 0;
	/**
	 * previousPage 前一页
	 */
	private int previousPage;
	/**
	 * nextPage 下一页
	 */
	private int nextPage;
	/**
	 * firstPage 第一页
	 */
	private int firstPage = 1;
	/**
	 * lastPage 最后一页
	 */
	private int lastPage;
	/**
	 * content 每页的内容
	 */
	private List<T> content;

	// 以下set方式是需要赋值的

	/**
	 * 设置当前页 <br>
	 *
	 * @param currentPage
	 * @author
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * 设置每页大小,也可以不用赋值,默认大小为10条 <br>
	 *
	 * @param pageSize
	 * @author
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 设置总条数,默认为0 <br>
	 *
	 * @param recordTotal
	 * @author
	 */
	public void setRecordTotal(int recordTotal) {
		this.recordTotal = recordTotal;
		this.otherAttr();
		this.setList();
	}

	/**
	 * 设置分页内容 <br>
	 *
	 * @param content
	 * @return
	 * @author
	 */
	public Pager<T> setContent(List<T> content) {
		this.content = content;
		return this;
	}

	/**
	 * 设置其他参数
	 *
	 * @author
	 */
	public void otherAttr() {
		// 总页数
		this.pageTotal = this.recordTotal % this.pageSize > 0 ? this.recordTotal / this.pageSize + 1 : this.recordTotal / this.pageSize;
		// 第一页
		this.firstPage = 1;
		// 最后一页
		this.lastPage = this.pageTotal;
		// 前一页
		if (this.currentPage > 1) {
			this.previousPage = this.currentPage - 1;
		} else {
			this.previousPage = this.firstPage;
		}
		// 下一页
		if (this.currentPage < this.lastPage) {
			this.nextPage = this.currentPage + 1;
		} else {
			this.nextPage = this.lastPage;
		}
	}

	/**
	 * 对集合进行分页
	 */
	public void setList() {
		this.recordTotal = this.content.size();
		// 总页数
		this.pageTotal = this.recordTotal % this.pageSize > 0 ? this.recordTotal / this.pageSize + 1 : this.recordTotal / this.pageSize;
		//起始页
		int start = this.pageSize * (this.currentPage - 1);
		//起始页大于总页数
		if (start > this.recordTotal) {
			this.content = Collections.emptyList();
			return;
		}

		//结束页
		int end;
		if (this.recordTotal - this.currentPage * this.pageSize >= 0) {
			end = this.currentPage * this.pageSize;
		} else {
			end = (this.currentPage - 1) * this.pageSize + (this.recordTotal % this.pageSize);
		}
		this.content = this.content.subList(start, end);

	}

	// 放开私有属性
	public int getCurrentPage() {
		return currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPageTotal() {
		return pageTotal;
	}

	public int getRecordTotal() {
		return recordTotal;
	}

	public int getPreviousPage() {
		return previousPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public List<T> getContent() {
		return content;
	}

	@Override
	public String toString() {
		return "Pager [currentPage=" + currentPage + ", pageSize=" + pageSize
				+ ", pageTotal=" + pageTotal + ", recordTotal=" + recordTotal
				+ ", previousPage=" + previousPage + ", nextPage=" + nextPage
				+ ", firstPage=" + firstPage + ", lastPage=" + lastPage
				+ ", content=" + content + "]";
	}


}
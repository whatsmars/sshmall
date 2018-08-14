package com.$2012.vo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/*
 * 分页（包装类）
 */
@Component("pageCtx")
@Scope("prototype")
public class PageContext<T> {
	/** 页码开始索引和结束索引 **/
	private PageList pageList;
	/** 页码数量 **/
	private int pageListSize = 10;
	/** 总页数 **/
	private long totalPages = 1;
	/** 每页显示记录数 **/
	private int maxResults = 12;
	/** 分页数据 **/
	private List<T> records;
	/** 总记录数 **/
	private long totalRecords;
	/** 当前页码 **/
	private int currentPage = 1;
	
	public PageContext() {}
	
	public PageContext(int maxResults, int currentPage) {
		this.maxResults = maxResults;
		this.currentPage = currentPage;
	}
	
	public PageContext(int pageListSize, int maxResults, int currentPage) {
		this(maxResults, currentPage);
		this.pageListSize = pageListSize;
	}
		
	
	/** 要获取记录的开始索引 **/
	public int getFirstResult() {
		return (this.currentPage-1)*this.maxResults;
	}
	
	public int getPageListSize() {
		return pageListSize;
	}

	public void setPageListSize(int pageListSize) {
		this.pageListSize = pageListSize;
	}

	public void setQueryResult(QueryResult<T> qr){
		setTotalRecords(qr.getTotalRecords());
		setRecords(qr.getResultList());
	}
	
	public long getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
		setTotalPages(this.totalRecords%this.maxResults==0? this.totalRecords/this.maxResults : this.totalRecords/this.maxResults+1);
	}
	public List<T> getRecords() {
		return records;
	}
	public void setRecords(List<T> records) {
		this.records = records;
	}
	public PageList getPageList() {
		return pageList;
	}
	@Resource
	public void setPageList(PageList pageList) {
		this.pageList = PageList.getPageList(pageListSize, currentPage, totalPages);
	}
	public long getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
		this.setPageList(pageList);
	}
	public int getMaxResults() {
		return maxResults;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPrePage() {
		if (currentPage == 1) return 0;
		return currentPage - 1;
	}

	public int getNextPage() {
		if (currentPage >= totalPages) return 0;
		return currentPage + 1;
	}
}

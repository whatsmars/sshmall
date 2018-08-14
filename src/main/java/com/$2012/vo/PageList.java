package com.$2012.vo;

import org.springframework.stereotype.Component;

/*
 * 当前网页页码列表
 */
@Component
public class PageList {
	/*开始页码*/
	private long startPage;
	/*结束页码*/
	private long endPage;
	
	public PageList() {}

	public PageList(long startPage, long endPage) {
		this.startPage = startPage;
		this.endPage = endPage;
	}

	public long getStartPage() {
		return startPage;
	}

	public void setStartPage(long startPage) {
		this.startPage = startPage;
	}

	public long getEndPage() {
		return endPage;
	}

	public void setEndPage(long endPage) {
		this.endPage = endPage;
	}
	
	/*
	 * 仿优酷评论构造页码列表，即始终将当前页码置于页码列表中间
	 * 当当前页码列表长度为偶数时，中间位置居左
	 */
	public static PageList getPageList(long pageListSize, int currentPage,
			long totalPages) {
		long startPage = currentPage
				- (pageListSize % 2 == 0 ? pageListSize / 2 - 1
						: pageListSize / 2);
		long endPage = currentPage + pageListSize / 2;
		if (startPage < 1) {
			startPage = 1;
			if (totalPages >= pageListSize)
				endPage = pageListSize;
			else
				endPage = totalPages;
		}
		if (endPage > totalPages) {
			endPage = totalPages;
			if ((endPage - pageListSize) > 0)
				startPage = endPage - pageListSize + 1;
			else
				startPage = 1;
		}
		return new PageList(startPage, endPage);
	}
}

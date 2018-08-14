package com.$2012.vo;

import java.util.List;

/*
 * 查询结果         <对实体对象进行包装处理得到的类或不宜放其他包里的类，放vo包里>
 */
public class QueryResult<T> {
	/*结果集*/
	private List<T> resultList;
	/*结果集总记录数*/
	private long totalRecords;
	public QueryResult() {}
	public QueryResult(List<T> resultList) {
		this.resultList = resultList;
	}
	public List<T> getResultList() {
		return resultList;
	}
	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
	public long getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}
}

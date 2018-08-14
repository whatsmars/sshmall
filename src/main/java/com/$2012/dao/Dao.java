package com.$2012.dao;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import com.$2012.vo.QueryResult;
/*
 * 利用泛型和反射封装常用数据库操作
 * ps:命名时Spring建议Dao而不是DAO
 */
public interface Dao<T> {
	/*增*/
	void save(T entity);
	/*删*/
	void delete(T entity);
	void delete(Serializable... entityIds);
	/*改*/
	void update(T entity);
	/*查 entityId是主键*/
	T find(Serializable entityId);
	/*获取记录总数*/
	long getCount();
	/*清空一级缓存（session缓存）-将对象由托管状态变为游离状态*/
	//托管状态：session.clear()..->Detached
	//游离状态：hibernate.clear()
	void clear();
	
	/*分页查询*/
	public QueryResult<T> getScrollData(int firstIndex, int maxResult, String whereql, List<Object> queryParams, LinkedHashMap<String, String> orderby);
	
	public QueryResult<T> getScrollData(int firstIndex, int maxResult, String whereql, List<Object> queryParams);
	
	public QueryResult<T> getScrollData(int firstIndex, int maxResult, LinkedHashMap<String, String> orderby);
	
	public QueryResult<T> getScrollData(int firstIndex, int maxResult);
	
	public QueryResult<T> getScrollData();
	
}

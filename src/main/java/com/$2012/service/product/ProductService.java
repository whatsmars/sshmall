package com.$2012.service.product;

import java.util.List;

import com.$2012.dao.Dao;
import com.$2012.entity.product.Brand;
import com.$2012.entity.product.Product;
import com.$2012.vo.QueryResult;

public interface ProductService extends Dao<Product> {
	/*后台--上架与下架(status-true|false)*/
	void setVisibleStatus(Integer[] productIds, boolean status);
	/*后台--推荐与不推荐(status-true|false)*/
	void setCommendStatus(Integer[] productIds, boolean status);
	/*前台--获得畅销产品 maxResults 指定畅销产品个数*/
	List<Product> getTopSale(Integer typeId, Integer maxResults);
	/*前台--获得浏览历史 maxResults 指定浏览历史的产品个数*/
	List<Product> findScanHistory(Integer[] productIds, Integer maxResults);
	/*更新人气指数*/
	void updateClickCount(Integer productId);
	/*人气指数归0*/
	void clearClickCount(Integer[] productIds);
	/*按关键词搜索产品，并分页*/
	/*QueryResult<Product> search(String keyword, int firstResult, int maxResults);*/
	/*查找某类别下所有商品的品牌*/
	List<Brand> findBrandsByTypeId(Integer[] typeIds);
}

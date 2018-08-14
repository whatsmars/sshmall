package com.$2012.service.product;

import java.util.List;

import com.$2012.dao.Dao;
import com.$2012.entity.product.ProductType;

public interface ProductTypeService extends Dao<ProductType> {
	/*获取类别typeIds下的所有子孙类别，将类别id存入allIds*/
	List<Integer> getAllSubTypeIds(List<Integer> allIds, Integer[] typeIds);
}

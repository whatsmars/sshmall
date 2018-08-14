package com.$2012.service.product.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.$2012.dao.DaoSupport;
import com.$2012.entity.product.ProductType;
import com.$2012.service.product.ProductTypeService;

@Component("productTypeService")
@Transactional //事实上，采用继承，service会继承dao的@Transactional，这里加上也无所谓，但更明白
public class ProductTypeServiceImpl extends DaoSupport<ProductType> implements ProductTypeService {

	@SuppressWarnings("unchecked")
	private List<Integer> getSubTypeIds(Integer[] parentIds) {
		if (parentIds != null && parentIds.length > 0) {
			int len = parentIds.length;
			StringBuilder ql = new StringBuilder();
			for (int i = 0; i < len; i++) {
				ql.append(parentIds[i]).append(',');
			}
			ql.deleteCharAt(ql.length() - 1);
			return super.hibernateTemplate.find("select o.typeId from ProductType o where o.parent.typeId in(" + ql.toString() + ")");
		}
		return null;
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<Integer> getAllSubTypeIds(List<Integer> allIds, Integer[] parentIds) {
		List<Integer> subIds = this.getSubTypeIds(parentIds);
		if (subIds != null && subIds.size() > 0) {
			allIds.addAll(subIds);
			this.getAllSubTypeIds(allIds, subIds.toArray(new Integer[]{}));
		}
		return allIds;
	}

}

package com.$2012.service.product.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.$2012.dao.DaoSupport;
import com.$2012.entity.product.ProductStyle;
import com.$2012.service.product.ProductStyleService;

@Component("productStyleService")
@Transactional
public class ProductStyleServiceImpl extends DaoSupport<ProductStyle> implements ProductStyleService {

	public void setVisible(Integer[] styleIds, Boolean status) {
		if (styleIds != null && styleIds.length > 0) {
			StringBuilder ql = new StringBuilder();
			int len = styleIds.length;
			for (int i = 0; i < len; i++) {
				ql.append("?,");
			}
			ql.deleteCharAt(2 * len - 1);
			Object[] params = new Object[len+1];
			params[0] = status;
			for (int i = 0; i < len; i++) {
				params[i+1] = styleIds[i];
			}
			this.hibernateTemplate.bulkUpdate("update ProductStyle o set o.visible = ? where o.styleId in(" + ql.toString() + ")", params);
		}
	}

	public ProductStyle findByName(String name) {
		return (ProductStyle) this.hibernateTemplate.find("select o from ProductStyle o where o.name=?", name).iterator().next();
	}

}

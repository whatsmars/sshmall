package com.$2012.service.product;

import com.$2012.dao.Dao;
import com.$2012.entity.product.ProductStyle;

public interface ProductStyleService extends Dao<ProductStyle> {
	/*设置样式是否可见*/
	void setVisible(Integer[] styleIds, Boolean status);
	/*按名称查找样式*/
	ProductStyle findByName(String name);
}

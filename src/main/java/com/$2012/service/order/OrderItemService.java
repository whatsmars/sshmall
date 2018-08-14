package com.$2012.service.order;

import com.$2012.dao.Dao;
import com.$2012.entity.order.OrderItem;

public interface OrderItemService extends Dao<OrderItem>  {
	/*修改指定订单项产品数量*/
	void updateProductAmount(Integer oitemId, int amount);
}

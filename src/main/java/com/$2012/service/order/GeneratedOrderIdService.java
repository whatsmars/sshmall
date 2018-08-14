package com.$2012.service.order;

import com.$2012.dao.Dao;
import com.$2012.entity.order.GeneratedOrderId;

public interface GeneratedOrderIdService extends Dao<GeneratedOrderId>  {
	/*初始化*/
	void init();
	/*生成订单流水号*/
	int buildOrderId();
}

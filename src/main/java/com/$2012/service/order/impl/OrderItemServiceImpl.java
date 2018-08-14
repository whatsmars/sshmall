package com.$2012.service.order.impl;


import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.$2012.dao.DaoSupport;
import com.$2012.entity.order.Order;
import com.$2012.entity.order.OrderItem;
import com.$2012.service.order.OrderItemService;

@Component("orderItemService")
public class OrderItemServiceImpl extends DaoSupport<OrderItem> implements OrderItemService {

	public void updateProductAmount(Integer oitemId, int amount) {
		OrderItem oitem = this.find(oitemId);
		oitem.setAmount(amount);
		Order order = oitem.getOrder();
		float result = 0f;
		for (OrderItem item : order.getItems()) {
			result += item.getProductPrice() * item.getAmount();
		}
		order.setProductTotalPrice(result);
		order.setTotalPrice(order.getProductTotalPrice() + order.getDeliverFee());
		order.setPayableFee(order.getTotalPrice());
	}

	@Override
	public void delete(Serializable... entityIds) {
		for (Serializable itemId : entityIds) {
			OrderItem oitem = this.find(itemId);
			Order order = oitem.getOrder();
			order.getItems().remove(oitem);
			float result = 0f;
			for (OrderItem item : order.getItems()) {
				result += item.getProductPrice() * item.getAmount();
			} 
			order.setProductTotalPrice(result);
			order.setTotalPrice(order.getProductTotalPrice() + order.getDeliverFee());
			order.setPayableFee(order.getTotalPrice());
			this.delete(oitem);
		}
	}

	

}

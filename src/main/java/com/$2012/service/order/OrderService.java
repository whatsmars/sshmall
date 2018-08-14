package com.$2012.service.order;

import com.$2012.dao.Dao;
import com.$2012.entity.order.DeliverWay;
import com.$2012.entity.order.Order;
import com.$2012.entity.order.PaymentWay;
import com.$2012.vo.Cart;

public interface OrderService extends Dao<Order>  {
	/*生成订单*/
	Order createOrder(Cart cart, String username);
	/*修改支付方式*/
	void updatePaymentWay(String orderId, PaymentWay paymentWay);
	/*修改配送方式*/
	void updateDeliverWay(String orderId, DeliverWay deliverWay);
	/*修改配送费*/
	void updateDeliverFee(String orderId, float deliverFee);
	/*转订单状态为“已取消”*/
	void cancelOrder(String orderId);
	/*转订单状态为“通过审核”*/
	void confirmOrder(String orderId);
	/*转订单状态为“已支付”*/
	void confirmPayment(String orderId);
	/*转订单状态为“等待发货”*/
	void turnWaitDeliver(String orderId);
	/*转订单状态为“已发货”*/
	void turnDelivered(String orderId);
	/*转订单状态为“已收货”*/
	void turnReceived(String orderId);
	/*为订单加锁*/
	Order addLock(String orderId, String username);
	/*为订单解锁*/
	void unlockOrder(String... orderIds);
}

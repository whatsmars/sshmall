package com.$2012.service.order.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.$2012.dao.DaoSupport;
import com.$2012.entity.order.Acceptor;
import com.$2012.entity.order.DeliverWay;
import com.$2012.entity.order.Order;
import com.$2012.entity.order.OrderItem;
import com.$2012.entity.order.OrderState;
import com.$2012.entity.order.PaymentWay;
import com.$2012.entity.product.Product;
import com.$2012.entity.product.ProductStyle;
import com.$2012.entity.user.ContactInfo;
import com.$2012.entity.user.User;
import com.$2012.service.order.GeneratedOrderIdService;
import com.$2012.service.order.OrderService;
import com.$2012.service.user.UserService;
import com.$2012.vo.BuyItem;
import com.$2012.vo.Cart;

@Component("orderService")
@Transactional
public class OrderServiceImpl extends DaoSupport<Order> implements OrderService {
	private GeneratedOrderIdService generatedOrderIdService;
	private UserService userService;
	
	@PostConstruct //init-method
	public void init() {
		//虽然init()在"orderService"里不受事务管理（@PostConstruct，实例化bean后会立即执行，未开事务）
		//但generatedOrderIdService实际是一个代理对象，代理对象执行目标对象的init方法前会打开事务，之后会提交事务
		this.generatedOrderIdService.init();
	}

	/*
	 * 注意解决多用户并发下订单问题
	 * 可给该方法加synchronized关键字，但不能解决多服务器问题
	 * 本项目中采用增加一个订单号实体，用来以自增长方式生成订单流水号(1,2,3...)
	 * 对流水号进行处理，形成最终的符合规范的订单号(如：12090100000001)
	 */
	public Order createOrder(Cart cart, String username) {
		Order order = new Order();
		User user = userService.find(username);
		order.setUser(user);
		order.setDeliverFee(cart.getDeliverFee());
		order.setNote(cart.getNote());
		if ("true".equals(cart.getBuyerIsAcceptor())) {//画蛇添足是为保万无一失
			cart.getBuyer().setBuyerName(cart.getAcceptor().getAcceptorName());
			cart.getBuyer().setGender(cart.getAcceptor().getGender());
			cart.getBuyer().setAddress(cart.getAcceptor().getAddress());
			cart.getBuyer().setEmail(cart.getAcceptor().getEmail());
			cart.getBuyer().setPostalcode(cart.getAcceptor().getPostalcode());
			if (cart.getAcceptor().getPhone() != null)
				cart.getBuyer().setPhone(cart.getAcceptor().getPhone());
			if (cart.getAcceptor().getMobile() != null) 
				cart.getBuyer().setMobile(cart.getAcceptor().getMobile());
		}
		order.setBuyer(cart.getBuyer());
		order.setAcceptor(cart.getAcceptor());
		order.setState(OrderState.WAITCONFIRM);
		order.setPaymentWay(cart.getPaymentWay());
		order.setProductTotalPrice(cart.getTotalSalePrice());
		order.setTotalPrice(cart.getOrderTotalPrice());
		order.setPayableFee(cart.getOrderTotalPrice());
		for (BuyItem item : cart.getItems()) {
			Product product = item.getProduct();
			ProductStyle style = product.getStyles().iterator().next();
			OrderItem oitem = new OrderItem(product.getName(), product.getProductId(),
					product.getSalePrice(), item.getAmount(), style.getName(), style.getStyleId());
			order.addOrderItem(oitem);
		}
		//如果用户的联系信息为空，则将收货人的相关信息赋予用户
		if (user.getContactInfo() == null) {
			Acceptor acceptor = cart.getAcceptor();
			ContactInfo contactInfo = new ContactInfo();
			contactInfo.setAddress(acceptor.getAddress());
			contactInfo.setPhone(acceptor.getPhone());
			contactInfo.setMobile(acceptor.getMobile());
			contactInfo.setPostalcode(acceptor.getPostalcode());
			if (user.getRealname() == null) user.setRealname(acceptor.getAcceptorName());
			if (user.getGender() == null) user.setGender(acceptor.getGender());
			contactInfo.setUser(user);
			user.setContactInfo(contactInfo);
			userService.update(user);
		}
		order.setOrderId(this.buildOrderId2(order.getCreateDate()));//涉及到事务排他锁定，故放最后
		super.save(order);
		return order;
	}
	
	/*
	 * 生成订单号--yyMMdd[6位]+(当天订单总数+1)[8位，不足则前面补0]
	 */
	@SuppressWarnings("unused")
	private String buildOrderId(Date date) {
		StringBuilder id = new StringBuilder(new SimpleDateFormat("yyMMdd").format(date));
		SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			final Date zeroTime = dateFmt.parse(dateFmt.format(date));
			long count = (Long) this.hibernateTemplate.find("select count(o) from Order o where o.createDate>?", zeroTime).get(0);
			String strCount = fillZero(8, String.valueOf(count + 1));
			id.append(strCount);
		} catch (ParseException e) {
			throw new RuntimeException("生成订单号失败");
		}
		return id.toString();
	}
	/*
	 * 生成订单号--yyMMdd[6位]+流水号[8位，不足则前面补0]
	 */
	private String buildOrderId2(Date date) {
		StringBuilder id = new StringBuilder(new SimpleDateFormat("yyMMdd").format(date));
		id.append(this.fillZero(8, String.valueOf(this.generatedOrderIdService.buildOrderId())));
		return id.toString();
	}
	/*
	 * 补0
	 */
	private String fillZero(int length, String source) {
		StringBuilder result = new StringBuilder(source);
		for (int i = result.length(); i < length; i++) {
			result.insert(0, '0');
		}
		return result.toString();
	}

	public GeneratedOrderIdService getGeneratedOrderIdService() {
		return generatedOrderIdService;
	}
	@Resource
	public void setGeneratedOrderIdService(
			GeneratedOrderIdService generatedOrderIdService) {
		this.generatedOrderIdService = generatedOrderIdService;
	}

	public UserService getUserService() {
		return userService;
	}
	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void updatePaymentWay(String orderId, PaymentWay paymentWay) {
		this.hibernateTemplate.bulkUpdate("update Order o set o.paymentWay=? where o.orderId=?", new Object[]{paymentWay, orderId});
	}

	public void updateDeliverWay(final String orderId, final DeliverWay deliverWay) {
		this.hibernateTemplate.bulkUpdate("update Acceptor o set o.deliverWay=? where o.order.orderId=?", new Object[]{deliverWay, orderId});
	}

	public void updateDeliverFee(String orderId, float deliverFee) {
		Order order = this.find(orderId);
		order.setDeliverFee(deliverFee);
		order.setTotalPrice(order.getProductTotalPrice() + order.getDeliverFee());
		order.setPayableFee(order.getTotalPrice());
	}

	public void cancelOrder(String orderId) {
		Order order = this.find(orderId);
		if (!OrderState.RECEIVED.equals(order.getState())) 
			order.setState(OrderState.CANCEL);
	}

	public void confirmOrder(String orderId) {
		Order order = this.find(orderId);
		if (OrderState.WAITCONFIRM.equals(order.getState())) {
			if (!PaymentWay.COD.equals(order.getPaymentWay()) && !order.getPaymentState())
				order.setState(OrderState.WAITPAYMENT);//定时器设置等待7天未付款则取消订单，当然也可人工取消
			else order.setState(OrderState.ADMEASUREPRODUCT);
		}
	}

	public void confirmPayment(String orderId) {
		Order order = this.find(orderId);
		order.setPaymentState(true);
		if (OrderState.WAITPAYMENT.equals(order.getState())) 
			order.setState(OrderState.ADMEASUREPRODUCT);
		else if (OrderState.DELIVERED.equals(order.getState()) && PaymentWay.COD.equals(order.getPaymentWay()))
			order.setState(OrderState.RECEIVED);
	}

	public void turnWaitDeliver(String orderId) {
		Order order = this.find(orderId);
		if (OrderState.ADMEASUREPRODUCT.equals(order.getState())) 
			order.setState(OrderState.WAITDELIVER);
	}

	public void turnDelivered(String orderId) {
		Order order = this.find(orderId);
		if (OrderState.WAITDELIVER.equals(order.getState())) 
			order.setState(OrderState.DELIVERED);
	}

	public void turnReceived(String orderId) {
		Order order = this.find(orderId);
		if (OrderState.DELIVERED.equals(order.getState())) 
			order.setState(OrderState.RECEIVED);
	}

	public Order addLock(final String orderId, final String username) {
		this.hibernateTemplate.bulkUpdate("update Order o set o.lockUser=? where o.orderId=? and o.lockUser is null and o.state not in(?,?)", new Object[]{username, orderId, OrderState.RECEIVED, OrderState.CANCEL});
		return this.find(orderId);
	}

	public void unlockOrder(final String... orderIds) {
		if (orderIds != null && orderIds.length > 0) {
			StringBuilder n = new StringBuilder();
			for (int i = 0; i < orderIds.length; i++) {
				n.append("?,");
			}
			n.deleteCharAt(n.length() - 1);
			Object[] params = new Object[orderIds.length+1];
			for (int i = 0; i < orderIds.length; i++) {
				params[i+1] = orderIds[i];
			}
			this.hibernateTemplate.bulkUpdate("update Order o set o.lockUser=? where o.orderId in(" + n.toString() + ")", params);
		}
	}

}

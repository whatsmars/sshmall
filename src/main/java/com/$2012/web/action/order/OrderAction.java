package com.$2012.web.action.order;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.$2012.entity.order.Acceptor;
import com.$2012.entity.order.Buyer;
import com.$2012.entity.order.DeliverWay;
import com.$2012.entity.order.Message;
import com.$2012.entity.order.Order;
import com.$2012.entity.order.OrderState;
import com.$2012.entity.order.PaymentWay;
import com.$2012.entity.privilege.Employee;
import com.$2012.service.order.AcceptorService;
import com.$2012.service.order.BuyerService;
import com.$2012.service.order.MessageService;
import com.$2012.service.order.OrderItemService;
import com.$2012.service.order.OrderService;
import com.$2012.utils.WebUtils;
import com.$2012.vo.Gender;
import com.$2012.vo.PageContext;
import com.$2012.vo.QueryResult;
import com.opensymphony.xwork2.ActionSupport;

import org.apache.struts2.ServletActionContext;

/*
 * （后台）订单管理
 */
@Component
@Scope("prototype") //每次访问都会新建Action，可能不宜将过多的属性和方法放在一个Action里
public class OrderAction extends ActionSupport {
	private static final long serialVersionUID = 6027368743294375344L;
	private Order order;
	private List<Order> orders = new ArrayList<Order>();
	private OrderService orderService;
	private PageContext<Order> pageCtx;
	
	private String query;
	
	private Acceptor acceptor;
	private Buyer buyer;
	private String acceptorGender;
	private String buyerGender;
	
	private BuyerService buyerService;
	private AcceptorService acceptorService;
	
	private String paymentWay;
	private String deliverWay;
	
	private Integer oitemId;
	private int amount;
	private OrderItemService orderItemService;
	
	private Employee employee;
	private String[] orderIds;
	
	private boolean showButton = false; 
	
	private Message msg;
	private MessageService messageService;
	
	/*
	 * 订单列表
	 */
	public String list() {
		pageCtx = new PageContext<Order>(12, pageCtx.getCurrentPage());
		StringBuilder whereql = new StringBuilder("o.state=?");
		List<Object> params = new ArrayList<Object>();
		params.add(order.getState() != null ? order.getState() : OrderState.WAITCONFIRM);
		if ("true".equals(query)) {
			if (order.getOrderId() != null && !"".equals(order.getOrderId().trim())) {
				whereql.append(" and o.orderId like ?");
				params.add("%" + order.getOrderId() + "%");
			}
			if (order.getState() != null) {
				whereql.append(" and o.state=?");
				params.add(order.getState());
			}
			if (order.getUser().getName() != null && !"".equals(order.getUser().getName().trim())) {
				whereql.append(" and o.user.name like ?");
				params.add("%" + order.getUser().getName().trim() + "%");
			}
			if (acceptor.getAcceptorName() != null && !"".equals(acceptor.getAcceptorName().trim())) {
				whereql.append(" and o.acceptor.acceptorName like ?");
				params.add("%" + acceptor.getAcceptorName().trim() + "%");
			}
			if (buyer.getBuyerName() != null && !"".equals(buyer.getBuyerName().trim())) {
				whereql.append(" and o.buyer.buyerName like ?");
				params.add("%" + buyer.getBuyerName().trim() + "%");
			}
		}
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "asc");//类似于订餐，应将先订的放上面
		QueryResult<Order> qr = orderService.getScrollData(pageCtx.getFirstResult(), pageCtx.getMaxResults(), whereql.toString(), params, orderby);
		orders = qr.getResultList();
		pageCtx.setQueryResult(qr);
		
		this.employee = WebUtils.getEmp(ServletActionContext.getRequest());
		
		for (Order order : orders) {
			if (order.getLockUser() != null) showButton = true;
		}
		return SUCCESS;
	}
	
	/*
	 * 订单详细信息查看(载入订单)
	 */
	public String view() {
		order = orderService.find(order.getOrderId());
		acceptor = order.getAcceptor();
		buyer = order.getBuyer();
		
		String empName = WebUtils.getEmp(ServletActionContext.getRequest()).getName();
		order = this.orderService.addLock(order.getOrderId(), empName);
		return SUCCESS;
	}
	
	/*
	 * 订单解锁
	 */
	public String unlockOrder() {
		this.orderService.unlockOrder(order.getOrderId());
		return SUCCESS;
	}
	
	/*
	 * 批量解锁
	 */
	public String unlockAll() {
		this.orderService.unlockOrder(orderIds);
		return SUCCESS;
	}
	
	/*
	 * 已锁订单列表
	 */
	public String listLocked() {
		pageCtx = new PageContext<Order>(12, pageCtx.getCurrentPage());
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "asc");
		QueryResult<Order> qr = orderService.getScrollData(pageCtx.getFirstResult(), pageCtx.getMaxResults(), "o.lockUser is not null", null, orderby);
		orders = qr.getResultList();
		pageCtx.setQueryResult(qr);
		if (orders.size() > 0) this.showButton = true;
		return SUCCESS;
	}
	
	/*
	 * 修改购买人信息
	 */
	public String updateBuyer() {
		Buyer buyer = this.buyerService.find(this.buyer.getBuyerId());
		buyer.setBuyerName(this.buyer.getBuyerName().trim());
		buyer.setGender(Gender.valueOf(buyerGender));
		buyer.setAddress(this.buyer.getAddress().trim());
		buyer.setPostalcode(this.buyer.getPostalcode().trim());
		buyer.setPhone(this.buyer.getPhone().trim());
		buyer.setMobile(this.buyer.getMobile().trim());
		this.buyerService.update(buyer);
		return SUCCESS;
	}
	
	/*
	 * 修改收货人人信息
	 */
	public String updateAcceptor() {
		Acceptor acceptor = this.acceptorService.find(this.acceptor.getAcceptorId());
		acceptor.setAcceptorName(this.acceptor.getAcceptorName().trim());
		acceptor.setGender(Gender.valueOf(acceptorGender));
		acceptor.setAddress(this.acceptor.getAddress().trim());
		acceptor.setPostalcode(this.acceptor.getPostalcode().trim());
		acceptor.setPhone(this.acceptor.getPhone().trim());
		acceptor.setMobile(this.acceptor.getMobile().trim());
		this.acceptorService.update(acceptor);
		return SUCCESS;
	}
	
	/*
	 * 修改支付方式
	 */
	public String updatePaymentWay() {
		this.orderService.updatePaymentWay(order.getOrderId(), PaymentWay.valueOf(paymentWay));
		return SUCCESS;
	}
	
	/*
	 * 修改配送方式
	 */
	public String updateDeliverWay() {
		this.orderService.updateDeliverWay(order.getOrderId(), DeliverWay.valueOf(deliverWay));
		return SUCCESS;
	}
	
	/*
	 * 修改所购产品数量
	 */
	public String updateProductAmount() {
		this.orderItemService.updateProductAmount(oitemId, amount);
		return SUCCESS;
	}
	
	/*
	 * 删除订单项
	 */
	public String deleteOrderItem() {
		this.orderItemService.delete(oitemId);
		return SUCCESS;
	}
	
	/*
	 * 修改配送费
	 */
	public String updateDeliverFee() {
		this.orderService.updateDeliverFee(order.getOrderId(), order.getDeliverFee());
		return SUCCESS;
	}
	
	/*
	 * 转订单状态为“已取消”
	 */
	public String cancelOrder() {
		this.orderService.cancelOrder(order.getOrderId());
		this.orderService.unlockOrder(order.getOrderId());
		return SUCCESS;
	}
	
	/*
	 * 转订单状态为“通过审核”
	 */
	public String confirmOrder() {
		this.orderService.confirmOrder(order.getOrderId());
		this.orderService.unlockOrder(order.getOrderId());
		return SUCCESS;
	}
	
	/*
	 * 转订单状态为“已支付”
	 */
	public String confirmPayment() {
		this.orderService.confirmPayment(order.getOrderId());
		this.orderService.unlockOrder(order.getOrderId());
		return SUCCESS;
	}
	
	/*
	 * 转订单状态为“等待发货”
	 */
	public String turnWaitDeliver() {
		this.orderService.turnWaitDeliver(order.getOrderId());
		this.orderService.unlockOrder(order.getOrderId());
		return SUCCESS;
	}
	
	/*
	 * 转订单状态为“已发货”
	 */
	public String turnDelivered() {
		this.orderService.turnDelivered(order.getOrderId());
		this.orderService.unlockOrder(order.getOrderId());
		return SUCCESS;
	}
	
	/*
	 * 转订单状态为“已收货”
	 */
	public String turnReceived() {
		this.orderService.turnReceived(order.getOrderId());
		this.orderService.unlockOrder(order.getOrderId());
		return SUCCESS;
	}
	
	/*
	 * 显示增加留言界面
	 */
	public String showAddMessageUI() {
		return SUCCESS;
	}
	
	/*
	 * 增加留言
	 */
	public String addMsg() {
		order = this.orderService.find(this.order.getOrderId());
		String empName = WebUtils.getEmp(ServletActionContext.getRequest()).getName();
		Message message = new Message(msg.getContent(), empName, order);
		this.messageService.save(message);
		return SUCCESS;
	}

	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	public OrderService getOrderService() {
		return orderService;
	}
	@Resource
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	public PageContext<Order> getPageCtx() {
		return pageCtx;
	}
	@Resource
	public void setPageCtx(PageContext<Order> pageCtx) {
		this.pageCtx = pageCtx;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public Acceptor getAcceptor() {
		return acceptor;
	}

	public void setAcceptor(Acceptor acceptor) {
		this.acceptor = acceptor;
	}

	public String getBuyerGender() {
		return buyerGender;
	}

	public void setBuyerGender(String buyerGender) {
		this.buyerGender = buyerGender;
	}

	public String getAcceptorGender() {
		return acceptorGender;
	}

	public void setAcceptorGender(String acceptorGender) {
		this.acceptorGender = acceptorGender;
	}

	public BuyerService getBuyerService() {
		return buyerService;
	}
	@Resource
	public void setBuyerService(BuyerService buyerService) {
		this.buyerService = buyerService;
	}

	public AcceptorService getAcceptorService() {
		return acceptorService;
	}
	@Resource
	public void setAcceptorService(AcceptorService acceptorService) {
		this.acceptorService = acceptorService;
	}

	public String getPaymentWay() {
		return paymentWay;
	}

	public void setPaymentWay(String paymentWay) {
		this.paymentWay = paymentWay;
	}

	public String getDeliverWay() {
		return deliverWay;
	}

	public void setDeliverWay(String deliverWay) {
		this.deliverWay = deliverWay;
	}

	public Integer getOitemId() {
		return oitemId;
	}

	public void setOitemId(Integer oitemId) {
		this.oitemId = oitemId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public OrderItemService getOrderItemService() {
		return orderItemService;
	}
	@Resource
	public void setOrderItemService(OrderItemService orderItemService) {
		this.orderItemService = orderItemService;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String[] getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String[] orderIds) {
		this.orderIds = orderIds;
	}

	public boolean isShowButton() {
		return showButton;
	}

	public void setShowButton(boolean showButton) {
		this.showButton = showButton;
	}
	
	public MessageService getMessageService() {
		return messageService;
	}
	@Resource
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public Message getMsg() {
		return msg;
	}

	public void setMsg(Message msg) {
		this.msg = msg;
	}

}

package com.$2012.entity.order;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.$2012.entity.user.User;

/*
 * 订单
 */
@Entity @Table(name="t_order")  //使用二级缓存情况：经常被访问，改动不大，数量有限 <load,iterate..默认使用二级缓存，即先查二级缓存，没有再查一级session缓存>
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Order {
	/* 订单号 */
	private String orderId;
	/* 所属用户 */
	private User user;
	/* 订单创建时间 */
	private Date createDate = new Date();
	/* 订单状态 */
	private OrderState state;
	/* 商品总金额 */
	private Float productTotalPrice = 0f;
	/* 配送费 */
	private Float deliverFee = 0f;
	/* 订单总金额 */
	private Float totalPrice= 0f;
	/* 应付款(实际需要支付的费用) */
	private Float payableFee = 0f;
	/* 顾客附言 */
	private String note;
	/* 支付方式 */
	private PaymentWay paymentWay;
	/* 支付状态 */
    private Boolean paymentState = false;
    /* 订单配送信息 */
	private Acceptor acceptor;
	/* 订单购买者联系信息 */
	private Buyer buyer;
	/* 订单项 */
	private Set<OrderItem> items = new HashSet<OrderItem>();
	/* 对订单进行加锁的用户,如果值为null,代表订单未被加锁,否则,订单被加锁 */
	private String lockUser;
	/* 客服留言 */
	private Set<Message> msgs = new HashSet<Message>();
	
	public Order(){}
	
	public Order(String orderId) {
		this.orderId = orderId;
	}
	@OneToMany(mappedBy="order",cascade=CascadeType.REMOVE)
	public Set<Message> getMsgs() {
		return msgs;
	}
	public void setMsgs(Set<Message> msgs) {
		this.msgs = msgs;
	}
	public void addMsg(Message message) {
		this.msgs.add(message);
	}
	@Column(length=20)
	public String getLockUser() {
		return lockUser;
	}
	public void setLockUser(String lockUser) {
		this.lockUser = lockUser;
	}
	@Id @Column(length=14)
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	@ManyToOne(cascade=CascadeType.REFRESH, optional=false)
	@JoinColumn(name="username")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Temporal(TemporalType.TIMESTAMP) @Column(nullable=false)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Enumerated(EnumType.STRING) @Column(length=16, nullable=false)
	public OrderState getState() {
		return state;
	}
	public void setState(OrderState state) {
		this.state = state;
	}
	@Column(nullable=false)
	public Float getProductTotalPrice() {
		return productTotalPrice;
	}
	public void setProductTotalPrice(Float productTotalPrice) {
		this.productTotalPrice = productTotalPrice;
	}
	@Column(nullable=false)
	public Float getDeliverFee() {
		return deliverFee;
	}
	public void setDeliverFee(Float deliverFee) {
		this.deliverFee = deliverFee;
	}
	@Column(nullable=false)
	public Float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}
	@Column(nullable=false)
	public Float getPayableFee() {
		return payableFee;
	}
	public void setPayableFee(Float payableFee) {
		this.payableFee = payableFee;
	}
	@Column(length=100)
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	@Enumerated(EnumType.STRING) @Column(length=20,nullable=false)
	public PaymentWay getPaymentWay() {
		return paymentWay;
	}
	public void setPaymentWay(PaymentWay paymentWay) {
		this.paymentWay = paymentWay;
	}
	@Column(nullable=false)
	public Boolean getPaymentState() {
		return paymentState;
	}
	public void setPaymentState(Boolean paymentState) {
		this.paymentState = paymentState;
	}
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="acceptorId")
	public Acceptor getAcceptor() {
		return acceptor;
	}
	public void setAcceptor(Acceptor acceptor) {
		this.acceptor = acceptor;
	}
	@OneToOne(cascade=CascadeType.ALL, optional=false)
	@JoinColumn(name="buyerId")
	public Buyer getBuyer() {
		return buyer;
	}
	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}
	@OneToMany(mappedBy="order",cascade=CascadeType.ALL)
	//双向关系，被维护端设mappedBy
	public Set<OrderItem> getItems() {
		return items;
	}
	public void setItems(Set<OrderItem> items) {
		this.items = items;
	}
	/*
	 * 添加订单项
	 */
	public void addOrderItem(OrderItem item){
		this.items.add(item);
		item.setOrder(this);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Order other = (Order) obj;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		return true;
	}
	
}

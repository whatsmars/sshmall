package com.$2012.entity.order;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
/*
 * 订单项
 *     由于订单项的与产品有关的部分信息在订单提交后是不允许更改的
 *     所以此处不采用关联Product
 *     如：昨天下的点单，今天准备付款，今天产品降价了，若关联的话，就易产生纠纷
 */
@Entity
public class OrderItem {
	private Integer itemId;
	/* 产品名称 */
	private String productName;
	/* 产品id */
	private Integer productId;
	/* 产品销售价 */
	private Float productPrice = 0f;
	/* 购买数量 */
	private Integer amount = 1;
	/* 产品样式 */
	private String styleName;	
	/* 产品样式ID */
	private Integer styleId;
	/* 所属订单 */
	private Order order;
	
	public OrderItem(){}
	
	/*
	 * 该构造器并未设itemId，因此为了区分不同的OrderItem，要改写hashCode()
	 */
	public OrderItem(String productName, Integer productid, Float productPrice,
			Integer amount, String styleName, Integer styleid) {
		this.productName = productName;
		this.productId = productid;
		this.productPrice = productPrice;
		this.amount = amount;
		this.styleName = styleName;
		this.styleId = styleid;
	}
	@Id @GeneratedValue
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	@Column(length=50,nullable=false)
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	@Column(nullable=false)
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	@Column(nullable=false)
	public Float getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(Float productPrice) {
		this.productPrice = productPrice;
	}
	@Column(nullable=false)
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	@Column(length=30,nullable=false)
	public String getStyleName() {
		return styleName;
	}
	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}
	@Column(nullable=false)
	public Integer getStyleid() {
		return styleId;
	}
	public void setStyleid(Integer styleid) {
		this.styleId = styleid;
	}
	@ManyToOne(cascade={CascadeType.MERGE, CascadeType.REFRESH}, optional=false)
	@JoinColumn(name="order_id")
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		//由于itemId由主键自增长生成，所以itemId均为null，故改写hashCode算法（0->super.hashCode()）
		result = prime * result + ((itemId == null) ? super.hashCode() : itemId.hashCode());
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
		final OrderItem other = (OrderItem) obj;
		if (itemId == null) {
			if (other.itemId != null)
				return false;
		} else if (!itemId.equals(other.itemId))
			return false;
		return true;
	}
	
}

package com.$2012.vo;

import java.util.ArrayList;
import java.util.List;

import com.$2012.entity.order.Acceptor;
import com.$2012.entity.order.Buyer;
import com.$2012.entity.order.PaymentWay;

/*
 * 购物车
 */
public class Cart {
	/*购买项*/
	private List<BuyItem> items = new ArrayList<BuyItem>();
	
	/*收货人*/
	private Acceptor acceptor;
	/*购买人*/
	private Buyer buyer; 
	/*购买人是否也是收货人*/
	private String buyerIsAcceptor;
	/*支付方式*/
	private PaymentWay paymentWay;
	/*配送费*/
	private float deliverFee = 10f;
	/*订单附言*/
	private String note;
	
	/*
	 * 添加购物项
	 */
	public void addItem(BuyItem item) {
		if (!items.contains(item)) {
			items.add(item);
		} else {
			for (BuyItem bi : items) {
				if (bi.equals(item)) {
					bi.setAmount(bi.getAmount() + 1);
					break;
				}
			}
		}
	}
	
	/*
	 * 清空购物车
	 */
	public void clear() {
		items.clear();
	}
	
	/*
	 * 删除购物项
	 */
	public void deleteItem(BuyItem item) {
		if (items.contains(item)) items.remove(item);
	}
	

	public List<BuyItem> getItems() {
		return items;
	}

	public void setItems(List<BuyItem> items) {
		this.items = items;
	}

	/*
	 * 计算商品的总金额
	 */
	public float getTotalSalePrice(){
		float result = 0f;
		for(BuyItem item : items){
			result += item.getProduct().getSalePrice() * item.getAmount();
		}
		return result;
	}
	
	/*
	 * 计算商品的总节省金额
	 */
	public float getTotalSavedPrice(){
		float result = 0f;
		for(BuyItem item : items){
			result += item.getProduct().getMarketPrice() * item.getAmount();
		}
		return result - getTotalSalePrice();
	}
	
	/*
	 * 计算订单总金额
	 */
	public float getOrderTotalPrice() {
		return this.getTotalSalePrice() + this.getDeliverFee();
	}

	public String getBuyerIsAcceptor() {
		return buyerIsAcceptor;
	}

	public void setBuyerIsAcceptor(String buyerIsAcceptor) {
		this.buyerIsAcceptor = buyerIsAcceptor;
	}

	public Acceptor getAcceptor() {
		return acceptor;
	}

	public void setAcceptor(Acceptor acceptor) {
		this.acceptor = acceptor;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public PaymentWay getPaymentWay() {
		return paymentWay;
	}

	public void setPaymentWay(PaymentWay paymentWay) {
		this.paymentWay = paymentWay;
	}

	public float getDeliverFee() {
		return deliverFee;
	}

	public void setDeliverFee(float deliverFee) {
		this.deliverFee = deliverFee;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}

package com.$2012.vo;

import com.$2012.entity.product.Product;
import com.$2012.entity.product.ProductStyle;

/*
 * 购买项（是否为同一购买项，由产品id和产品样式id共同决定）
 */
public class BuyItem {
	/*购买的商品*/
	private Product product;
	/*购买的数量*/
	private int amount;
	
	public BuyItem(Product product) {
		this.product = product;
	}
	public BuyItem(Product product, int amount) {
		this.product = product;
		this.amount = amount;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	@Override
	public int hashCode() {
		String itemId = product.hashCode() + "-";
		if (product.getStyles().size() > 0)
			itemId += product.getStyles().iterator().next().getStyleId();
		return itemId.hashCode();
	}
	/*
	 * 是否为同一购买项，由产品id和产品样式id共同决定
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final BuyItem other = (BuyItem) obj;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		
		if (product.getStyles().size() != other.product.getStyles().size())
			return false;
		if (product.getStyles().size() > 0) {
			ProductStyle style = product.getStyles().iterator().next();
			ProductStyle otherStyle = other.product.getStyles().iterator().next();
			if (!style.equals(otherStyle))
				return false;
		}
		return true;
	}
}

package com.$2012.web.action.shopping;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.$2012.entity.product.Product;
import com.$2012.entity.product.ProductStyle;
import com.$2012.service.product.ProductService;
import com.$2012.utils.WebUtils;
import com.$2012.vo.BuyItem;
import com.$2012.vo.Cart;
import com.$2012.web.listener.SiteSessionListener;
import com.opensymphony.xwork2.ActionSupport;

@Component
@Scope("prototype")
public class CartAction extends ActionSupport {
	private static final long serialVersionUID = -2034553708522363350L;
	private Cart cart;
	private Integer productId;
	private Integer styleId;
	private ProductService productService;
	
	private String directUrl;
	private String note;
	
	/*
	 * 显示购物车
	 */
	public String list() {
		findCart();
		if (cart == null) return "noCart";
		return SUCCESS;
	}
	
	/*
	 * 找车
	 */
	private void findCart() {
		HttpServletRequest request = ServletActionContext.getRequest();
		cart = WebUtils.getCart(request);
		if (cart == null) {//1.session存在但找不到
			String sid = WebUtils.getCookieByName(request, "cartId");
			if (sid != null) {
				HttpSession session = SiteSessionListener.getSession(sid);
				if (session != null) {
					cart = (Cart) session.getAttribute("cart");
					if (cart != null) {
						SiteSessionListener.removeSession(sid);
						request.getSession().setAttribute("cart", cart);
						WebUtils.addCookie(ServletActionContext.getResponse(), "cartId", request.getSession().getId(), request.getSession().getMaxInactiveInterval());
					}
				}
			}
		}
	}
	
	/*
	 * 购买，加入购物车
	 */
	public String buy() {
		findCart();
		if (cart == null) {//2.session不存在
			HttpServletRequest request = ServletActionContext.getRequest();
			cart = new Cart();
			request.getSession().setAttribute("cart", cart);
			//将sessionid存入cookie中，以便浏览器关掉重启后仍能找到session
			WebUtils.addCookie(ServletActionContext.getResponse(), "cartId", request.getSession().getId(), request.getSession().getMaxInactiveInterval());
		}
		
		if (productId != null && productId > 0) {
			Product product = this.productService.find(productId);
			if (product != null) {
				ProductStyle currentStyle = null;
				if (styleId == null) currentStyle = product.getStyles().iterator().next();
				else {
					for (ProductStyle style : product.getStyles()) {
						if (style.getVisible() && style.getStyleId().equals(styleId)) {
							currentStyle = style;
						}
					}
				}
				product.getStyles().clear();
				if (currentStyle != null) product.addStyle(currentStyle);
				
				cart.addItem(new BuyItem(product, 1));
				
				this.productService.updateClickCount(productId);
			}
		}
		
		return SUCCESS;
	}
	
	/*
	 * 删除购物项
	 */
	public String delete() {
		HttpServletRequest request = ServletActionContext.getRequest();
		cart = WebUtils.getCart(request);
		if (cart != null) {
			Product product = new Product();
			product.setProductId(productId);
			ProductStyle style = new ProductStyle();
			style.setStyleId(styleId);
			product.addStyle(style);
			cart.deleteItem(new BuyItem(product));
		}
		return SUCCESS;
	}
	
	/*
	 * 清空购物车
	 */
	public String clear() {
		HttpServletRequest request = ServletActionContext.getRequest();
		WebUtils.deleteCart(request);//删除购物车
		/*cart = WebUtils.getCart(request);
		if (cart != null) cart.clear();*/
		return SUCCESS;
	}
	
	/*
	 * 更新购买数量
	 */
	public String updateAmount() {
		setAmount();
		return SUCCESS;
	}
	
	/*
	 * 结算
	 */
	public String settleAccounts() {
		setAmount();//结算前更新数量
		//没做到订单确提交确认模块时下面的语句不必看；
		if (directUrl != null && !"".equals(directUrl)) {
			HttpServletRequest request = ServletActionContext.getRequest();
			cart = WebUtils.getCart(request);
			if (cart != null) cart.setNote(note);
			return "toOrderConfirmUI";
		} 
		return SUCCESS;
	}
	
	public void setAmount() {
		HttpServletRequest request = ServletActionContext.getRequest();
		cart = WebUtils.getCart(request);
		if (cart != null) {
			for (BuyItem item : cart.getItems()) {
				StringBuilder key = new StringBuilder("amount_");
				Product product = item.getProduct();
				key.append(product.getProductId()).append("_");
				if (product.getStyles().size() > 0) {
					key.append(product.getStyles().iterator().next().getStyleId());
				}
				String amountStr = request.getParameter(key.toString());
				if (amountStr != null && !"".equals(amountStr)) {
					try {
						int amount = Integer.parseInt(amountStr);
						if (amount > 0) item.setAmount(amount);
					} catch (RuntimeException e) {}
				}
			}
		}
	}
	
	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getStyleId() {
		return styleId;
	}

	public void setStyleId(Integer styleId) {
		this.styleId = styleId;
	}

	public ProductService getProductService() {
		return productService;
	}
	@Resource
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public String getDirectUrl() {
		return directUrl;
	}

	public void setDirectUrl(String directUrl) {
		this.directUrl = directUrl;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}

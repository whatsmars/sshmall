package com.$2012.web.action.shopping;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.$2012.entity.order.*;
import com.$2012.entity.user.ContactInfo;
import com.$2012.entity.user.User;
import com.$2012.service.order.AcceptorService;
import com.$2012.service.order.BuyerService;
import com.$2012.service.order.OrderService;
import com.$2012.utils.*;
import com.$2012.vo.Cart;
import com.$2012.vo.Gender;
import com.opensymphony.xwork2.ActionSupport;



import java.util.Arrays;

/*
 * 这里的业务有点复杂，记住一点：
 * 购物车里有数据时，从购物车拿，否则从数据库拿
 */
@Component
@Scope("prototype")
public class ShoppingManageAction extends ActionSupport {
	private static final long serialVersionUID = -264920969703201462L;
	
	private Cart cart;
	private User user;
	
	private AcceptorService acceptorService;
	private BuyerService buyerService;
	private Acceptor acceptor;
	private Buyer buyer;
	private String acceptorGender;
	private String buyerGender;
	private String buyerIsAcceptor;
	
	private String deliverWay;
	private String paymentWay;
	private String requirement;
	private String deliverNote;
	
	private String directUrl;
	
	private String note;
	private OrderService orderService;
	private Order order;
	
	/*
	 * 显示配送信息界面
	 */
	public String showDeliverInfoUI() {
		HttpServletRequest request = ServletActionContext.getRequest();
		user = WebUtils.getUser(request);
		cart = WebUtils.getCart(request);
		
		ContactInfo contactInfo = user.getContactInfo();
		
		if (cart == null) return "noCart";
		else {
			//信息回显
			if(cart.getAcceptor() != null) {
				this.acceptor = cart.getAcceptor();
				if(cart.getAcceptor().getGender() != null) {
					this.acceptorGender = cart.getAcceptor().getGender().toString();
				}
				cart.setNote(note);
			} else {
				if (this.acceptorService.exists(user.getRealname())) {
					this.acceptor = this.acceptorService.findByName(user.getRealname());
				} else if (contactInfo != null) {
					this.acceptor = new Acceptor();
					this.acceptor.setAcceptorName(user.getRealname());
					this.acceptor.setAddress(contactInfo.getAddress());
					this.acceptor.setGender(user.getGender());
					if (contactInfo.getMobile() != null) this.acceptor.setMobile(contactInfo.getMobile());
					if (contactInfo.getPhone() != null) this.acceptor.setPhone(contactInfo.getPhone());
					this.acceptor.setPostalcode(contactInfo.getPostalcode());
				}
			}
			if(cart.getBuyerIsAcceptor() != null) {
				this.buyerIsAcceptor = cart.getBuyerIsAcceptor();
				if(cart.getBuyer() != null) {
					this.buyer = cart.getBuyer();
					if(cart.getBuyer().getGender() != null) {
						this.buyerGender = cart.getBuyer().getGender().toString();
					}
				}
			} 
		}
		return SUCCESS;
	}
	
	/*
	 * 保存配送信息
	 */
	public String saveDeliverInfo() {
		HttpServletRequest request = ServletActionContext.getRequest();
		cart = WebUtils.getCart(request);
		
		if (cart == null) return "noCart";
		else {
			//order-acceptor-deliverWay 依赖关系
			//解决在点单确认界面返回修改配送信息时的deliverWay丢失问题
			if (cart.getAcceptor() != null) {
				if (cart.getAcceptor().getDeliverWay() != null) {
					acceptor.setDeliverWay(cart.getAcceptor().getDeliverWay());
					if (cart.getAcceptor().getRequirement() != null) 
						acceptor.setRequirement(cart.getAcceptor().getRequirement());
				}
			}
			
			acceptor.setGender(Gender.valueOf(acceptorGender));
			cart.setAcceptor(acceptor);
			cart.setBuyerIsAcceptor(buyerIsAcceptor);
			if (buyerIsAcceptor != null) {
				if ("false".equals(buyerIsAcceptor)) {
					buyer.setGender(Gender.valueOf(buyerGender));
					buyer.setEmail(WebUtils.getUser(request).getEmail());
				} 
				if ("true".equals(buyerIsAcceptor)) {
					buyer.setAddress(acceptor.getAddress());
					buyer.setBuyerName(acceptor.getAcceptorName());
					buyer.setEmail(acceptor.getEmail());
					buyer.setGender(acceptor.getGender());
					if (null != acceptor.getMobile()) buyer.setMobile(acceptor.getMobile());
					if (null != acceptor.getPhone()) buyer.setPhone(acceptor.getPhone());
					buyer.setPostalcode(acceptor.getPostalcode());
				}
				cart.setBuyer(buyer);
			} 
		}
		
		if (directUrl != null && !"".equals(directUrl)) return "toOrderConfirmUI";
		
		return SUCCESS;
	}
	
	/*
	 * 显示支付方式界面
	 */
	public String showPaymentWayUI() {
		HttpServletRequest request = ServletActionContext.getRequest();
		cart = WebUtils.getCart(request);
		
		if (cart == null) return "noCart";
		else {
			if (cart.getAcceptor() == null) return "toDeliverInfoUI";
			
			//信息回显
			if (cart.getAcceptor().getDeliverWay() != null) {
				this.deliverWay = cart.getAcceptor().getDeliverWay().toString();
				System.out.println(cart.getAcceptor().getRequirement());
				if (cart.getAcceptor().getRequirement() != null) {
					List<String> contents = Arrays
							.asList("工作日、双休日与假日均可送货", "只双休日、假日送货", "只工作日送货(双休日、假日不用送)",
									"学校地址/地址白天没人，请尽量安排其他时间送货");
					if (contents.contains(cart.getAcceptor().getRequirement())) {
						this.requirement = cart.getAcceptor().getRequirement();
					}else {
						this.requirement = "other";
						this.deliverNote = cart.getAcceptor().getRequirement();
					}
				}
			} 
			if(cart.getPaymentWay() != null) {
				this.paymentWay = cart.getPaymentWay().toString();
				cart.setNote(note);
			} 
		}
		
		return SUCCESS;
	}
	
	/*
	 * 保存支付方式，显示订单确认界面
	 */
	public String savePaymentWay() {
		HttpServletRequest request = ServletActionContext.getRequest();
		cart = WebUtils.getCart(request);
		
		if (cart == null) return "noCart";
		else {
			acceptor = cart.getAcceptor();
			acceptor.setDeliverWay(DeliverWay.valueOf(deliverWay));
			if (DeliverWay.EXPRESSDELIVERY.equals(DeliverWay.valueOf(deliverWay)) || DeliverWay.EXIGENCEEXPRESSDELIVERY.equals(DeliverWay.valueOf(deliverWay))) {
				if ("other".equals(requirement)) {
					acceptor.setRequirement(deliverNote);
				} else {
					acceptor.setRequirement(requirement);
				}
			} else {
				acceptor.setRequirement(null);
			}
			cart.setAcceptor(acceptor);
			cart.setPaymentWay(PaymentWay.valueOf(paymentWay));
		}
		
		String url = "/customer/shopping/manage/savePaymentWay";
		directUrl = new String(Base64.encodeBase64(url.getBytes()));//编码加密
		
		return SUCCESS;
	}
	
	/*
	 * 提交订单
	 */
	public String saveOrder() {
		HttpServletRequest request = ServletActionContext.getRequest();
		cart = WebUtils.getCart(request);
		
		if (cart == null) return "noCart";
		else {
			if (note != null) cart.setNote(note);
			order = orderService.createOrder(cart, WebUtils.getUser(request).getName());
			WebUtils.deleteCart(request);
			
			if (PaymentWay.NET.equals(order.getPaymentWay())) return "net";
			if (PaymentWay.COD.equals(order.getPaymentWay())) return "cod";
			if (PaymentWay.BANKREMITTANCE.equals(order.getPaymentWay())) return "bankRemittance";
			if (PaymentWay.POSTOFFICEREMITTANCE.equals(order.getPaymentWay())) return "postoFficeRemittance";
		}
		
		return "postoFficeRemittance";
	}
	
	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
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

	public String getAcceptorGender() {
		return acceptorGender;
	}

	public void setAcceptorGender(String acceptorGender) {
		this.acceptorGender = acceptorGender;
	}

	public String getBuyerGender() {
		return buyerGender;
	}

	public void setBuyerGender(String buyerGender) {
		this.buyerGender = buyerGender;
	}

	public String getBuyerIsAcceptor() {
		return buyerIsAcceptor;
	}

	public void setBuyerIsAcceptor(String buyerIsAcceptor) {
		this.buyerIsAcceptor = buyerIsAcceptor;
	}

	public String getDeliverWay() {
		return deliverWay;
	}

	public void setDeliverWay(String deliverWay) {
		this.deliverWay = deliverWay;
	}

	public String getPaymentWay() {
		return paymentWay;
	}

	public void setPaymentWay(String paymentWay) {
		this.paymentWay = paymentWay;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public String getDeliverNote() {
		return deliverNote;
	}

	public void setDeliverNote(String deliverNote) {
		this.deliverNote = deliverNote;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public OrderService getOrderService() {
		return orderService;
	}
	@Resource
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public AcceptorService getAcceptorService() {
		return acceptorService;
	}
	@Resource
	public void setAcceptorService(AcceptorService acceptorService) {
		this.acceptorService = acceptorService;
	}

	public BuyerService getBuyerService() {
		return buyerService;
	}
	@Resource
	public void setBuyerService(BuyerService buyerService) {
		this.buyerService = buyerService;
	}

}

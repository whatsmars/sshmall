package com.$2012.entity.order;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.$2012.vo.Gender;

/**
 * 收货人
 *    User - Acceptor - Buyer 可能是三个不同的人，也可能是同一个人（即User）
 *    还可能是两个人（User和Acceptor[收货人和购买者为同一人]）
 *    当User的ContactInfo为null时，将Acceptor的信息赋给ContactInfo
 */
@Entity
public class Acceptor {
	private Integer acceptorId;
	/* 收货人姓名 */
	private String acceptorName;
	/* 配送地址 */
	private String address;
	/* 电子邮箱 */
	private String email;
	/* 邮编 */
	private String postalcode;
	/* 座机 */
	private String phone;
	/* 手机 */
	private String mobile;
	/* 性别 */
	private Gender gender = Gender.MAN;
	/* 送货方式 */
	private DeliverWay deliverWay;
	/* 送货时间要求 */
	private String requirement;
	/*所属订单*/
	private Order order;
	
	@Enumerated(EnumType.STRING) @Column(length=23,nullable=false)
	public DeliverWay getDeliverWay() {
		return deliverWay;
	}
	public void setDeliverWay(DeliverWay deliverWay) {
		this.deliverWay = deliverWay;
	}
	@Column(length=30)
	public String getRequirement() {
		return requirement;
	}
	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}
	@Column(length=8,nullable=false)
	public String getAcceptorName() {
		return acceptorName;
	}
	public void setAcceptorName(String acceptorName) {
		this.acceptorName = acceptorName;
	}
	@Column(length=40,nullable=false)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(length=40)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(length=6)
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	@Column(length=18)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(length=11)
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Enumerated(EnumType.STRING) @Column(length=5,nullable=false)
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	@Id @GeneratedValue
	public Integer getAcceptorId() {
		return acceptorId;
	}
	public void setAcceptorId(Integer acceptorId) {
		this.acceptorId = acceptorId;
	}
	@OneToOne(//mappedBy="acceptor", //配置成一对一的对象不维护关联关系
			cascade=CascadeType.REFRESH)
	@JoinColumn(name="orderId")
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
}

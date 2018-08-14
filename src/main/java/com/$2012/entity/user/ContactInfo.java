package com.$2012.entity.user;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ContactInfo implements Serializable{
	private static final long serialVersionUID = -4336182674133849896L;
	private Integer contactId;
	/** 地址 **/
	private String address;
	/** 邮编 **/
	private String postalcode;
	/** 座机 **/
	private String phone;
	/** 手机 **/
	private String mobile;
	/** 所属用户 **/
	private User user;
	@Id @GeneratedValue
	public Integer getContactId() {
		return contactId;
	}
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	@Column(length=100,nullable=false)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(length=6)
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postcode) {
		this.postalcode = postcode;
	}
	@Column(length=20)
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
	@OneToOne(mappedBy="contactInfo", cascade=CascadeType.REFRESH)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((contactId == null) ? super.hashCode() : contactId.hashCode());
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
		final ContactInfo other = (ContactInfo) obj;
		if (contactId == null) {
			if (other.contactId != null)
				return false;
		} else if (!contactId.equals(other.contactId))
			return false;
		return true;
	}
	
}

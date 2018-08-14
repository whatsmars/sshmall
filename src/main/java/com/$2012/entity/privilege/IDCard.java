package com.$2012.entity.privilege;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/*
 * 身份证
 */
@Entity
public class IDCard {
	private Integer cardId;//实体标识,自增长
	/* 18 ,不能为null */
	private String cardNo;
	/* 50 不能为null */
	private String address;
	/* 出生日期 */
	private Date birthday;//采用只含有日期部分的类型表示
	/* 所属的员工 */
	private Employee employee;
	
	public IDCard(){}
	
	public IDCard(String cardNo, String address, Date birthday) {
		this.cardNo = cardNo;
		this.address = address;
		this.birthday = birthday;
	}
	@Id @GeneratedValue
	public Integer getCardId() {
		return cardId;
	}
	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}
	@Column(length=18,nullable=false)
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	@Column(length=40,nullable=false)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(nullable=false) @Temporal(TemporalType.DATE)
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	@OneToOne(mappedBy="idCard", cascade=CascadeType.REFRESH)
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cardId == null) ? 0 : cardId.hashCode());
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
		final IDCard other = (IDCard) obj;
		if (cardId == null) {
			if (other.cardId != null)
				return false;
		} else if (!cardId.equals(other.cardId))
			return false;
		return true;
	}
	
}

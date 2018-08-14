package com.$2012.entity.privilege;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.$2012.vo.Gender;

/*
 * 员工
 */
@Entity
public class Employee {
	/* 主键,20位*/
	private String name;
	/* 36位, 不能为null */
	private String password;
	/* 姓名 10位 不能为null */
	private String realname;
	/* 性别 5位 不能为null */
	private Gender gender;
	/* 学历 10位 */
	private String degree;
	/* 身份证 必须提供 */
	private IDCard idCard ;//一对一,员工作为关系维护端
	/* 毕业院校 20位 */
	private String school;
	/* 联系电话 20 */
	private String phone;
	/* 电子邮件 40 */
	private String email;
	/* 照片 41 */ 
	private String empImagePath; 
	/* 员工在职状态 true为在职,false为离职 */
	private Boolean visible = true;
	/* 员工所在部门 */
	private Department department;//双向一对多,多对一
	/* 员工具有的权限 */
	private Set<PrivilegeGroup> groups = new HashSet<PrivilegeGroup>();
	
	@ManyToMany(cascade=CascadeType.REFRESH, fetch=FetchType.EAGER)
	@JoinTable(name="emp_group",inverseJoinColumns=@JoinColumn(name="groupId"),
			joinColumns=@JoinColumn(name="name"))
	public Set<PrivilegeGroup> getGroups() {
		return groups;
	}

	public void setGroups(Set<PrivilegeGroup> groups) {
		this.groups = groups;
	}
	
	public void addGroups(PrivilegeGroup group) {
		this.groups.add(group);
	}
	
	public void clearGroups() {
		this.groups.clear();
	}

	@Id @Column(length=20)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length=30, nullable=false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(length=8,nullable=false)
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	@Enumerated(EnumType.STRING) @Column(length=5,nullable=false)
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	@Column(length=10)
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	
	@OneToOne(cascade=CascadeType.ALL, optional=false)
	@JoinColumn(name="cardId")
	public IDCard getIdCard() {
		return idCard;
	}
	public void setIdCard(IDCard idCard) {
		this.idCard = idCard;
	}
	@Column(length=20)
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	@Column(length=18)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(length=40)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(length=100)
	public String getEmpImagePath() {
		return empImagePath;
	}
	public void setEmpImagePath(String empImagePath) {
		this.empImagePath = empImagePath;
	}
	/*获得员工原尺寸图片路径，图片均由上传而来*/
	@Transient
	public String getEmpPrototypeImagePath() {
		int i = this.empImagePath.lastIndexOf("/");
		StringBuilder sb = new StringBuilder(this.empImagePath);
		sb.insert(i, "/prototype");
		return sb.toString();
	}
	@Column(nullable=false)
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="departmentId")
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((name == null) ? 0 : name.hashCode());
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
		final Employee other = (Employee) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}

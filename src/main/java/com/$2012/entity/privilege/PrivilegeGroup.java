package com.$2012.entity.privilege;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
/*
 * 权限组
 */
@Entity
public class PrivilegeGroup {
	private String groupId;
	private String name;
	private Set<SystemPrivilege> privileges = new HashSet<SystemPrivilege>();
	private Set<Employee> employees = new HashSet<Employee>();
	
	public PrivilegeGroup(){}
	
	public PrivilegeGroup(String name) {
		this.name = name;
	}
	@ManyToMany(mappedBy="groups", cascade=CascadeType.REFRESH)
	public Set<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}
	@Id @Column(length=36)
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	@Column(length=20,nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@ManyToMany(cascade=CascadeType.REFRESH,fetch=FetchType.EAGER)
	@JoinTable(name="group_privilege", inverseJoinColumns={@JoinColumn(name="module", referencedColumnName="module"),
											  @JoinColumn(name="privilege", referencedColumnName="privilege")}
						 ,joinColumns=@JoinColumn(name="groupId"))
	public Set<SystemPrivilege> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(Set<SystemPrivilege> privileges) {
		this.privileges = privileges;
	}
	public void addPrivilege(SystemPrivilege privilege) {
		this.privileges.add(privilege);
	}
	public void clear() {
		this.privileges.clear();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
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
		final PrivilegeGroup other = (PrivilegeGroup) obj;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		return true;
	}
	
}

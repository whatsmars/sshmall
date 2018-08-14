package com.$2012.entity.privilege;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/*
 * 联合主键
 */
@Embeddable
public class SystemPrivilegePK implements Serializable{
	private static final long serialVersionUID = 313537697211620906L;
	/* 模块 */
	private String module;
	/* 权限值 */
	private String privilege;
	
	public SystemPrivilegePK(){}

	public SystemPrivilegePK(String module, String privilege) {
		this.module = module;
		this.privilege = privilege;
	}
	@Column(length=20, name="module")
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	@Column(length=20, name="privilege")
	public String getPrivilege() {
		return privilege;
	}
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((module == null) ? 0 : module.hashCode());
		result = prime * result
				+ ((privilege == null) ? 0 : privilege.hashCode());
		return result;
	}
	/*既然是联合主键，自然要比较所以属性*/
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SystemPrivilegePK other = (SystemPrivilegePK) obj;
		if (module == null) {
			if (other.module != null)
				return false;
		} else if (!module.equals(other.module))
			return false;
		if (privilege == null) {
			if (other.privilege != null)
				return false;
		} else if (!privilege.equals(other.privilege))
			return false;
		return true;
	}
	
}

package com.$2012.web.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.$2012.entity.privilege.Employee;
import com.$2012.entity.privilege.PrivilegeGroup;
import com.$2012.entity.privilege.SystemPrivilege;
import com.$2012.entity.privilege.SystemPrivilegePK;
import com.$2012.utils.WebUtils;

/*
 * 权限校验标签  （也可定义权限注解，然后用AOP检测方法上是否有权限注解，进而分析拦截）
 */
public class PermissionTag extends TagSupport {
	private static final long serialVersionUID = -2239087804642296923L;
	
	/*模块*/
	private String module;
	/*权限*/
	private String privilege;
	
	@Override
	public int doStartTag() throws JspException {
		boolean result = false;
		Employee emp = WebUtils.getEmp((HttpServletRequest) pageContext.getRequest());
		SystemPrivilege privilege = new SystemPrivilege(new SystemPrivilegePK(this.module, this.privilege));
		for(PrivilegeGroup group : emp.getGroups()){
			if(group.getPrivileges().contains(privilege)){
				result = true;
				break;
			}
		}
		return result ? EVAL_BODY_INCLUDE : SKIP_BODY;
	}
	public String getPrivilege() {
		return privilege;
	}
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
}

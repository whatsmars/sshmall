package com.$2012.web.action.privilege;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;
import org.springframework.stereotype.Component;

import com.$2012.entity.privilege.Employee;
import com.$2012.entity.privilege.PrivilegeGroup;
import com.$2012.entity.privilege.SystemPrivilege;
import com.$2012.entity.privilege.SystemPrivilegePK;
import com.$2012.utils.WebUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
/*
 * 权限拦截器
 */
@Component
public class PermissionInterceptor implements Interceptor {
	private static final long serialVersionUID = 5451842413580775186L;

	public void destroy() {
	}

	public void init() {
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		//获得所访问方法
		Method method = invocation.getAction().getClass().getMethod(invocation.getProxy().getConfig().getMethodName());
		HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
		if(method!=null && method.isAnnotationPresent(Permission.class)){
			//得到方法上的注解
			Permission permission = method.getAnnotation(Permission.class);
			//得到执行方法需要的权限
			SystemPrivilege methodPrivilege = new SystemPrivilege(new SystemPrivilegePK(permission.module(), permission.privilege()));
			Employee emp = WebUtils.getEmp(request);
			for(PrivilegeGroup group : emp.getGroups()){
				if(group.getPrivileges().contains(methodPrivilege)) return invocation.invoke();
			}
		}
		request.setAttribute("message", "您没有执行该动作的权限");
		//request.setAttribute("urladdress", request.getRequestURL());
		return "noPermission";
	}

}

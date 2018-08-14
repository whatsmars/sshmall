package com.$2012.web.action.privilege;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.$2012.entity.privilege.Employee;
import com.$2012.service.privilege.EmployeeService;
import com.$2012.utils.WebUtils;
import com.opensymphony.xwork2.ActionSupport;

@Component
@Scope("prototype")
public class EmployeeLoginAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4756267994050047566L;
	private String username;
	private String password;
	private Employee employee;
	private EmployeeService employeeService;
	private Map<String, Object> session;
	
	public String login() {
	    if (employeeService.validate(username.trim(), password.trim())) {
			session.put("emp", employeeService.find(username));//将登录用户存入session中
			return SUCCESS;
		} else {
			this.addFieldError("login", "用户名或密码错误");
			return "nameorpass";
		} 
	}
	
	public String logout() {
		WebUtils.deleteEmp(ServletActionContext.getRequest());
		return SUCCESS;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public EmployeeService getEmployeeService() {
		return employeeService;
	}
	@Resource
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	} 
}

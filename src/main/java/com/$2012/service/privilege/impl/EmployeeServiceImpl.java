package com.$2012.service.privilege.impl;

import java.io.Serializable;
import org.springframework.stereotype.Component;

import com.$2012.dao.DaoSupport;
import com.$2012.entity.privilege.Employee;
import com.$2012.service.privilege.EmployeeService;

@Component("employeeService")
public class EmployeeServiceImpl extends DaoSupport<Employee> implements EmployeeService {

	public boolean exists(final String name) {
		return (Long) this.hibernateTemplate.find("select count(o) from Employee o where o.name=?", name).get(0) > 0;
	}

	/*
	 * 标志为离职
	 */
	@Override
	public void delete(Serializable... entityIds) {
		setVisible(false, entityIds);
	}
	
	private void setVisible(final boolean visible, final Serializable... entityIds) {
		if (entityIds != null && entityIds.length > 0) {
			StringBuilder n = new StringBuilder();
			for (int i = 0; i < entityIds.length; i++) {
				n.append("?,");
			}
			n.deleteCharAt(n.length() - 1);
			Object[] params = new Object[entityIds.length+1];
			params[0] = visible;
			for (int i = 0; i < entityIds.length; i++) {
				params[i+1] = entityIds[i];
			}
			this.hibernateTemplate.bulkUpdate("update Employee o set o.visible=? where o.name in(" + n.toString() + ")", params);
		}
	}

	public boolean validate(final String username, final String password) {
		return (Long) this.hibernateTemplate.find("select count(o) from Employee o where o.name=? and o.password=? and o.visible=?", new Object[]{username, password, true}).get(0) > 0;
	}

}

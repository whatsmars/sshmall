package com.$2012.service.privilege;

import com.$2012.dao.Dao;
import com.$2012.entity.privilege.Employee;

public interface EmployeeService extends Dao<Employee> {
	/*判断用户名是否存在*/
	public boolean exists(String username);
	/*判断用户名及密码是否正确*/
	public boolean validate(String username, String password);
}

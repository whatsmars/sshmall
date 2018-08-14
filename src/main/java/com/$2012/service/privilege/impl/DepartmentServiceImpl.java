package com.$2012.service.privilege.impl;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.$2012.dao.DaoSupport;
import com.$2012.entity.privilege.Department;
import com.$2012.entity.privilege.Employee;
import com.$2012.service.privilege.DepartmentService;

@Component("departmentService")
public class DepartmentServiceImpl extends DaoSupport<Department> implements DepartmentService {

	@Override
	public void save(Department entity) {
		entity.setDepartmentId(UUID.randomUUID().toString());
		super.save(entity);
	}

	@Override
	public void delete(Serializable... entityIds) {
		for (Serializable id : entityIds) {
			Department department = this.find(id);
			for (Employee employee : department.getEmployees()) {
				employee.setDepartment(null);
				employee.setVisible(false);
			}
			this.delete(department);
		}
	}

}

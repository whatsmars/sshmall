package com.$2012.service.privilege.impl;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.$2012.dao.DaoSupport;
import com.$2012.entity.privilege.Employee;
import com.$2012.entity.privilege.PrivilegeGroup;
import com.$2012.service.privilege.PrivilegeGroupService;

@Component("privilegeGroupService")
public class PrivilegeGroupServiceImpl extends DaoSupport<PrivilegeGroup> implements PrivilegeGroupService {

	@Override
	public void save(PrivilegeGroup group) {
		group.setGroupId(UUID.randomUUID().toString());
		super.save(group);
	}

	@Override
	public void delete(Serializable... entityIds) {
		for (Serializable id : entityIds) {
			PrivilegeGroup group = this.find(id);
			for (Employee emp : group.getEmployees()) {
				emp.getGroups().remove(group);
			}
			super.delete(group);
		}
	}

}

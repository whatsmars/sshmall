package com.$2012.service.privilege;

import java.util.List;

import com.$2012.dao.Dao;
import com.$2012.entity.privilege.SystemPrivilege;

public interface SystemPrivilegeService extends Dao<SystemPrivilege> {
	/*批量保存系统权限*/
	public void batchSave(List<SystemPrivilege> privileges);
}

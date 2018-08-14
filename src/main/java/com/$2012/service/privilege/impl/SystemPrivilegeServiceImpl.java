package com.$2012.service.privilege.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.$2012.dao.DaoSupport;
import com.$2012.entity.privilege.SystemPrivilege;
import com.$2012.service.privilege.SystemPrivilegeService;
import com.$2012.vo.QueryResult;

@Component("systemPrivilegeService")
public class SystemPrivilegeServiceImpl extends DaoSupport<SystemPrivilege> implements SystemPrivilegeService {

	public void batchSave(List<SystemPrivilege> privileges) {
		for (SystemPrivilege privilege : privileges) {
			this.save(privilege);
		}
	}
	
	/*不知为什么，父类提供的一些方法测试时会出错*/
	@Override
	public long getCount() {
		return (Integer) this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createQuery("select o from SystemPrivilege o").list().size();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<SystemPrivilege> getScrollData() {
		return new QueryResult<SystemPrivilege>(this.hibernateTemplate.find("from SystemPrivilege"));
	}

}

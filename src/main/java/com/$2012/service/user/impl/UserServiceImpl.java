package com.$2012.service.user.impl;

import java.io.Serializable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.$2012.dao.DaoSupport;
import com.$2012.entity.user.User;
import com.$2012.service.user.UserService;
import com.$2012.utils.MD5;


@Component("userService")
@Transactional
public class UserServiceImpl extends DaoSupport<User> implements UserService {

	public boolean exists(String name) {//因为sql注入，一般推荐使用占位符
		return (Long) this.hibernateTemplate.find("select count(o) from User o where o.name=?", name).get(0) > 0;
	}
	
	/*保存密码时，先将用户输入的密码编码加密*/
	@Override
	public void save(User u) {
		u.setPassword(MD5.MD5Encode(u.getPassword()));
		super.save(u);
	}

	public boolean validate(String name, String password) {
		return (Long) this.hibernateTemplate.find("select count(o) from User o where o.name=? and o.password=? and o.visible=?", new Object[]{name, MD5.MD5Encode(password), true}).get(0) > 0;
	}
	
	/*
	 * 禁用用户
	 */
	@Override
	public void delete(Serializable... entityIds) {
		setVisible(false, entityIds);
	}
	
	/*
	 * 启用用户
	 */
	public void enable(Serializable... entityIds) {
		setVisible(true, entityIds);
	}
	
	private void setVisible(boolean visible, Serializable... entityIds) {
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
			this.hibernateTemplate.bulkUpdate("update User o set o.visible=? where o.name in(" + n.toString() + ")", params);
		}
	}

	public void updatePassword(final String username, final String password) {
		this.hibernateTemplate.bulkUpdate("update User o set o.password=? where o.name=?", new Object[]{MD5.MD5Encode(password), username});
	}
	
}

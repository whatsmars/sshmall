package com.$2012.service.user;

import java.io.Serializable;

import com.$2012.dao.Dao;
import com.$2012.entity.user.User;

public interface UserService extends Dao<User> {
	/*判断用户是否已存在*/
	boolean exists(String name);
	/*登录验证*/
	boolean validate(String name, String password);
	/*启用用户*/
	void enable(Serializable... entityIds);
	/*修改密码*/
	void updatePassword(String username, String password);
}

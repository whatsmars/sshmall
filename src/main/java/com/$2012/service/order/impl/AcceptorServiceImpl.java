package com.$2012.service.order.impl;


import org.springframework.stereotype.Component;

import com.$2012.dao.DaoSupport;
import com.$2012.entity.order.Acceptor;
import com.$2012.service.order.AcceptorService;

@Component("acceptorService")
public class AcceptorServiceImpl extends DaoSupport<Acceptor> implements AcceptorService {
	public boolean exists(final String name) {//因为sql注入，一般推荐使用占位符
		return (Long) this.hibernateTemplate.find("select count(o) from Acceptor o where o.acceptorName=?", name).get(0) > 0;
	}

	public Acceptor findByName(final String name) {
		return (Acceptor) this.hibernateTemplate.find("select o from Acceptor o where o.acceptorName=?", name).get(0);
	}
}

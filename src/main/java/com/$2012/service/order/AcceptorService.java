package com.$2012.service.order;

import com.$2012.dao.Dao;
import com.$2012.entity.order.Acceptor;

public interface AcceptorService extends Dao<Acceptor>  {
	boolean exists(String name);
	
	Acceptor findByName(String name);
}

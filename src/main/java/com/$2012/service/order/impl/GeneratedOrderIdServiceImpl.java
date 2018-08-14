package com.$2012.service.order.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.$2012.dao.DaoSupport;
import com.$2012.entity.order.GeneratedOrderId;
import com.$2012.service.order.GeneratedOrderIdService;

@Component("generatedOrderIdService")
@Transactional
public class GeneratedOrderIdServiceImpl extends DaoSupport<GeneratedOrderId> implements GeneratedOrderIdService {
	public void init() {
		if (this.getCount() == 0) {
			GeneratedOrderId orderId = new GeneratedOrderId();
			orderId.setId("order");
			this.save(orderId);
		}
	}
	
	public int buildOrderId() {
		//update,find处在一个事务中，事务未结束时，update操作被加上了排他锁，即只允许当前一个用户执行该操作
		this.hibernateTemplate.bulkUpdate("update GeneratedOrderId o set o.orderId=o.orderId+1 where o.id=?", "order");
		//事务提交前，该事务范围内的各条sql语句会被加入到批处理对象(JDBC-Batch)中，事务提交时一次性发送
		this.hibernateTemplate.flush();//将sql语句立刻发送到数据库里执行
		return super.find("order").getOrderId();//find
	}
}

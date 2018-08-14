package com.$2012.service.order.impl;


import org.springframework.stereotype.Component;

import com.$2012.dao.DaoSupport;
import com.$2012.entity.order.Buyer;
import com.$2012.service.order.BuyerService;

@Component("buyerService")
public class BuyerServiceImpl extends DaoSupport<Buyer> implements BuyerService {
}

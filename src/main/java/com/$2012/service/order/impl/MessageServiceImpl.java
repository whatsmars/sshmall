package com.$2012.service.order.impl;

import org.springframework.stereotype.Component;

import com.$2012.dao.DaoSupport;
import com.$2012.entity.order.Message;
import com.$2012.service.order.MessageService;
@Component("messageService")
public class MessageServiceImpl extends DaoSupport<Message> implements MessageService {

}

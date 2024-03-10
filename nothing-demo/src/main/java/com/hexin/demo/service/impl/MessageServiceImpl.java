package com.hexin.demo.service.impl;

import com.hexin.demo.entity.Message;
import com.hexin.demo.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author hex1n
 */
@Slf4j
@Service
public class MessageServiceImpl implements MessageService {


    @Override
    public void sendMessage(Message message) {
        System.out.println(message);
//        String exchange = "FANOUT.ORACLE.CHANGE." + message.getTable();
//        log.info("【RabbitMQ】Send message to '" + exchange);
//        rabbitTemplate.convertAndSend(exchange, null, JSON.toJSONString(message));
    }
}